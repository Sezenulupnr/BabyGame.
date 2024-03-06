package com.example.babygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.babygame.common.viewBinding
import com.example.babygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}