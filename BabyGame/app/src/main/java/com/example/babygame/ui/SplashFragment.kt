package com.example.babygame.ui

import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.babygame.R
import com.example.babygame.databinding.FragmentSplashBinding
import com.example.babygame.common.viewBinding
import kotlinx.coroutines.CoroutineScope

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gifImg = pl.droidsonroids.gif.GifDrawable(resources, R.drawable.baby)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)

            // SplashScreen süresi sona erdiğinde, ana fragment'a geçiş yap
            findNavController().navigate(R.id.splashToEntry)
        }

        with(binding){

            ivBaby2.setImageDrawable(gifImg)
            gifImg.start()
            }
        }
}