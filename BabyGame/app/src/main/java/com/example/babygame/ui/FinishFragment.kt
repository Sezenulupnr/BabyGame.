package com.example.babygame.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.babygame.R
import com.example.babygame.databinding.FragmentFinishBinding
import com.example.babygame.common.viewBinding

class FinishFragment : Fragment(R.layout.fragment_finish) {

    private val binding by viewBinding(FragmentFinishBinding::bind)
    private val args by navArgs<FinishFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Oyunun bitiş skorunu al
        val score = args.score

        // Skoru EditText'e yerleştir
        binding.etFinishScore.setText(score.toString())

        // Skor 20 veya daha büyükse kazandınız mesajını göster, aksi takdirde kaybettiniz mesajını göster
        if (score >= 15) {
            binding.tvFinish.text = "KAZANDINIZ"
        } else {
            binding.tvFinish.text = "KAYBETTİNİZ"
        }

        // Tekrar oyuna başlamak için düğmeye tıklandığında GameFragment'a geç
        binding.btnGame.setOnClickListener {
            findNavController().navigate(R.id.finishToGame)
        }
    }
}
