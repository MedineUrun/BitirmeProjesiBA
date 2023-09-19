package com.example.bitirmeprojesi.ui.PhotoDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import coil.load
import com.example.bitirmeprojesi.data.model.Photo
import com.example.bitirmeprojesi.databinding.FragmentPhotoBottomBinding
import com.example.bitirmeprojesi.databinding.FragmentPhotoDetailBinding
import com.example.bitirmeprojesi.percentage
import com.example.bitirmeprojesi.windowHeight
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PhotoBottomFragment(private val photo: Photo) : BottomSheetDialogFragment() {

    lateinit var binding: FragmentPhotoBottomBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoBottomBinding.inflate(layoutInflater,container,false)
        binding.root.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, requireActivity().windowHeight().percentage(99))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivPhoto.load(photo.src.portrait)
        binding.tvPhotographer.text = photo.photographer


    }

}