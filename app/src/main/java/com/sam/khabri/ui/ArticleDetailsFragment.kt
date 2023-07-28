package com.sam.khabri.ui

import android.os.Build
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sam.khabari.R
import com.sam.khabari.databinding.FragmentArticleDetailsBinding
import com.sam.khabri.base.BaseFragment
import com.sam.khabri.data.model.response.Article
import com.sam.khabri.utils.ARTICLE
import com.sam.khabri.utils.dismissLoadingDialog
import com.sam.khabri.utils.showLoadingDialog


class ArticleDetailsFragment : BaseFragment<FragmentArticleDetailsBinding, HomeViewModel>() {


    override fun setUpViews() {
        super.setUpViews()

        showLoadingDialog()
        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getParcelable(ARTICLE, Article::class.java)
        else
            arguments?.getParcelable(ARTICLE)

        (requireActivity() as MainActivity).supportActionBar?.show()
        (requireActivity() as MainActivity).supportActionBar?.title = article?.title

        val webView = viewBinding?.webview

        webView?.settings?.loadsImagesAutomatically = true
        webView?.settings?.javaScriptEnabled = true
        webView?.settings?.domStorageEnabled = true
        webView?.settings?.setSupportZoom(true)
        webView?.settings?.builtInZoomControls = true
        webView?.settings?.displayZoomControls = false
        webView?.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        article?.url?.let { viewBinding?.webview?.loadUrl(it) }


        webView?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, urlPage: String) {
                dismissLoadingDialog()
            }
        }

    }


    override var useSharedViewModel: Boolean = true
    override fun viewModelClass() = HomeViewModel::class.java
    override val layoutId: Int = R.layout.fragment_article_details
}