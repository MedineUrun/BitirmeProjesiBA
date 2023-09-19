package com.example.bitirmeprojesi.ui.PhotoDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import coil.load
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.Photo
import com.example.bitirmeprojesi.databinding.FragmentPhotoDetailBinding

class PhotoDetailFragment : Fragment(R.layout.fragment_photo_detail) {

    private lateinit var binding:FragmentPhotoDetailBinding
    private lateinit var webView: WebView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding=FragmentPhotoDetailBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val args=arguments
        val photoDetail=args?.getParcelable<Photo>("photoDetail")
        if (photoDetail!=null){
            binding.imgPhotoDetail.load(photoDetail.src.portrait)
            binding.btnPhotographer.text=photoDetail.photographer
        }

        binding.fabShare.setOnClickListener {
            val photoDetail = arguments?.getParcelable<Photo>("photoDetail")
            val imageUri = Uri.parse(photoDetail?.src?.portrait)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"

            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
            startActivity(Intent.createChooser(shareIntent, "Resmi Paylaş"))
        }

        webView = binding.webView
        webView.settings.javaScriptEnabled = true

        binding.btnPhotographer.setOnClickListener {
            val url = photoDetail?.photographer  + "_https://www.pexels.com/api/"
            webView.visibility = View.VISIBLE
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)
        }

    }
}