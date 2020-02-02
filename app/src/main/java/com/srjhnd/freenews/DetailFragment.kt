package com.srjhnd.freenews

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.srjhnd.freenews.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.newsView.webViewClient = ArticleWebViewClient(binding)
        binding.newsView.loadUrl(args.articleUrl)
        binding.swipeRefreshNews.isRefreshing = true
        binding.swipeRefreshNews.setOnRefreshListener {
            binding.newsView.loadUrl(args.articleUrl)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.open_in_browser -> {
                val openIntent =
                    Intent(Intent.ACTION_VIEW).also { it.setData(Uri.parse(args.articleUrl)) }
                startActivity(openIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private class ArticleWebViewClient(val binding: FragmentDetailBinding) : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            binding.swipeRefreshNews.isRefreshing = false
            super.onPageFinished(view, url)
        }
    }
}