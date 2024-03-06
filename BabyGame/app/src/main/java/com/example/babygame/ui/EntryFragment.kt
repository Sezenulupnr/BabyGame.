package com.example.babygame.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.babygame.R
import com.example.babygame.databinding.FragmentEntryBinding
import com.example.babygame.common.viewBinding

class EntryFragment : Fragment(R.layout.fragment_entry) {

    private val binding by viewBinding(FragmentEntryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gifImg = pl.droidsonroids.gif.GifDrawable(resources, R.drawable.baby)

        with(binding){

            ivBaby.setImageDrawable(gifImg)

            btnStart.setOnClickListener{
                findNavController().navigate(R.id.entryToGame)

            }
        }
    }

}