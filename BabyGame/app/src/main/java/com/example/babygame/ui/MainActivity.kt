package com.example.babygame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.babygame.databinding.ActivityMainBinding
import com.example.babygame.common.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding){

        }

        setContentView(binding.root)
    }
}