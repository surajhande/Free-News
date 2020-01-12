package com.srjhnd.opennews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: HeadlineAdapter, binding: FragmentMainBinding?) {
        viewModel.headlines.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }
}