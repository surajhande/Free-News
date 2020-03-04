package com.srjhnd.freenews

import InjectionUtils
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.srjhnd.freenews.adapters.HeadlineAdapter
import com.srjhnd.freenews.databinding.FragmentMainBinding
import com.srjhnd.freenews.utils.NetworkUtils
import com.srjhnd.freenews.viewmodel.HeadlineViewModel


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private var showGoToTop = false

    private val viewModel: HeadlineViewModel by viewModels {
        InjectionUtils.provideHeadlineViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* Get the country of operation from locale */
        val locale = context!!.resources.configuration.locale.country
        NetworkUtils.country = locale ?: "in"

        val adapter = HeadlineAdapter()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        /* setting up UI elements */
        with(binding) {
            recyclerView.adapter = adapter
            swipeRefreshView.setOnRefreshListener {
                viewModel.fetchTopHeadlines()
            }
        }

        viewModel.fetchTopHeadlines()
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        subscribeUi(adapter, binding)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_action -> {
                viewModel.fetchTopHeadlines()
            }
            R.id.about_action -> {
                val count = viewModel.getCount()
                Toast.makeText(this.context, "count is $count", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribeUi(adapter: HeadlineAdapter, binding: FragmentMainBinding) {
        viewModel.headlines.observe(viewLifecycleOwner) { result ->
            binding.noNews.visibility = if (result.isEmpty()) View.VISIBLE else View.GONE
            if (adapter.itemCount < result.count() && adapter.itemCount != 0)
                showGoToTop = true
            adapter.submitList(result)
        }
        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            with(binding) {
                if (swipeRefreshView.isRefreshing && it) {
                    swipeRefreshView.isRefreshing = false
                }
            }
        }
    }

}