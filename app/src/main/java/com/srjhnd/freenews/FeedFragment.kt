package com.srjhnd.freenews

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.srjhnd.freenews.adapters.HeadlineAdapter
import com.srjhnd.freenews.data.State
import com.srjhnd.freenews.databinding.FragmentFeedBinding
import com.srjhnd.freenews.utils.NetworkUtils
import com.srjhnd.freenews.viewmodel.FeedViewModel
import com.srjhnd.freenews.viewmodel.FeedViewModelFactory
import kotlinx.android.synthetic.main.fragment_feed.*
import timber.log.Timber
import javax.inject.Inject


class FeedFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: FeedViewModelFactory

    private lateinit var binding: FragmentFeedBinding
    private lateinit var connectionSnackbar: Snackbar
    private val viewModel: FeedViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as FreeNewsApplication).applicationGraph.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* Get the country of operation from locale */
        val locale = context!!.resources.configuration.locale.country
        NetworkUtils.country = locale ?: "in"

        val adapter = HeadlineAdapter()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)

        setupUi(adapter, binding)
        subscribeUi(adapter, binding)

        if (viewModel.initialUpdateRequired) {
            viewModel.initialUpdateRequired = false
            viewModel.fetchTopHeadlines()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_action -> {
                viewModel.fetchTopHeadlines()
            }
            R.id.about_action -> {
                val count = viewModel.getCount()
                Toast.makeText(this.context, "number of articles: $count", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUi(adapter: HeadlineAdapter, binding: FragmentFeedBinding) {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        connectionSnackbar = Snackbar.make(binding.parentView, R.string.network_error, Snackbar.LENGTH_INDEFINITE)
        connectionSnackbar.setBackgroundTint(resources.getColor(R.color.red))
        connectionSnackbar.setActionTextColor(resources.getColor(R.color.white))
        connectionSnackbar.setAction(R.string.try_again) {
            viewModel.fetchTopHeadlines()
        }

        with(binding) {
            recyclerView.adapter = adapter
            swipeRefreshView.setOnRefreshListener {
                viewModel.fetchTopHeadlines()
            }
        }

        setHasOptionsMenu(true)
    }

    private fun subscribeUi(adapter: HeadlineAdapter, binding: FragmentFeedBinding) {
        viewModel.headlines.observe(viewLifecycleOwner) { result ->
            Timber.d("headlines update arrived.")
            binding.noNews.visibility = if (result.isEmpty()) View.VISIBLE else View.GONE
            adapter.submitList(result)
        }

        viewModel.requestState.observe(viewLifecycleOwner) { state ->
            Timber.d("state updated to $state")
            when (state) {
                State.IDLE -> {
                    if (swipeRefreshView.isRefreshing)
                        swipeRefreshView.isRefreshing = false
                }
                State.LOADING -> {
                    binding.swipeRefreshView.isRefreshing = true
                    if (connectionSnackbar.isShown)
                        connectionSnackbar.dismiss()
                }
                State.FAILURE -> {
                    connectionSnackbar.show()
                    if (swipeRefreshView.isRefreshing)
                        swipeRefreshView.isRefreshing = false
                }
            }
        }
    }

}