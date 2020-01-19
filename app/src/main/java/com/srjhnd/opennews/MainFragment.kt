package com.srjhnd.opennews

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.srjhnd.opennews.adapters.HeadlineAdapter
import com.srjhnd.opennews.databinding.FragmentMainBinding
import com.srjhnd.opennews.viewmodel.HeadlineViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: HeadlineViewModel by viewModels {
        InjectionUtils.provideHeadlineViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = HeadlineAdapter()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.recyclerView.adapter = adapter
        binding.swipeRefreshView.setOnRefreshListener {
            viewModel.fetchTopHeadlines()
            binding.swipeRefreshView.isRefreshing = false
        }
        subscribeUi(adapter, binding)
        setHasOptionsMenu(true)
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

    private fun subscribeUi(adapter: HeadlineAdapter, binding: FragmentMainBinding?) {
        viewModel.headlines.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }
}