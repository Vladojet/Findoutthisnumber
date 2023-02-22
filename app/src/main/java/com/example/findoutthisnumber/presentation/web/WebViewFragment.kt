package com.example.findoutthisnumber.presentation.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.example.findoutthisnumber.R
import com.example.findoutthisnumber.databinding.FragmentWebViewBinding
import com.example.findoutthisnumber.presentation.utils.isOnline

class WebViewFragment : Fragment(), IOnBackPressed {


    private lateinit var binding: FragmentWebViewBinding

    private var linkParam: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { linkParam = it.getString(ARG_LINK) }
    }

    companion object {
        private const val ARG_LINK = "link param"
        fun newInstance(linkParam: String) = WebViewFragment().apply {
            arguments = Bundle().apply { putString(ARG_LINK, linkParam) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentWebViewBinding.inflate(layoutInflater).apply { binding = this }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            binding.webView.restoreState(savedInstanceState)
        } else {
            showPage(linkParam!!)
            initWebView()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState)
    }

    private fun showPage(link: String) {
        if (requireContext().isOnline()) binding.webView.loadUrl(link)
        else showInternetErrorMessage()
    }

    private fun showInternetErrorMessage() {
        binding.webView.visibility = View.GONE
        binding.networkErrorAlert.visibility = View.VISIBLE
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
        val webSettings = binding.webView.settings
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.databaseEnabled = true
        webSettings.setSupportZoom(false)
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else return
    }
}