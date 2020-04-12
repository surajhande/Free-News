package com.srjhnd.freenews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.srjhnd.freenews.databinding.FragmentHeadlineBinding
import com.srjhnd.freenews.viewmodel.FeedViewModelFactory
import com.srjhnd.freenews.viewmodel.HeadlineViewModel
import com.srjhnd.freenews.viewmodel.HeadlineViewModelFactory
import kotlinx.android.synthetic.main.fragment_headline.*
import javax.inject.Inject

class HeadlineFragment : Fragment() {

    private lateinit var binding: FragmentHeadlineBinding
    private val args: HeadlineFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: HeadlineViewModelFactory

    private val headlineViewModel: HeadlineViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as FreeNewsApplication).applicationGraph.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_headline, container, false)
        headlineViewModel.getHeadlineByTitle(args.articleTitle)
            .observe(viewLifecycleOwner) { headline ->
                binding.headline = headline
            }
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        binding.clickListener = View.OnClickListener { view ->
            val direction = HeadlineFragmentDirections.actionHeadlineFragmentToDetailFragment(
                binding.headline?.url ?: ""
            )
            view.findNavController().navigate(direction)
        }
        var bToolbarShown = false
        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val shouldShowToolbar = scrollY > binding.toolbar.height
            if (bToolbarShown != shouldShowToolbar) {
                bToolbarShown = shouldShowToolbar
                binding.appBarLayout.isActivated = shouldShowToolbar
                binding.collapsableToolBar.isTitleEnabled = shouldShowToolbar
            }
        })
        return binding.root
    }

}
