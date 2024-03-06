package com.example.babygame.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.babygame.R
import com.example.babygame.common.viewBinding
import com.example.babygame.databinding.FragmentGameBinding

class GameFragment : Fragment(R.layout.fragment_game), View.OnTouchListener {

    private val binding by viewBinding(FragmentGameBinding::bind)

    private var playerX = 0f
    private var objectY = 0f
    private var score = 0

    private val maxX = 640f // Sağ sınırı belirleyin
    private val minX = 0f   // Sol sınırı belirleyin

    private val objectSpeed = 30 // Nesnelerin düşüş hızı

    private var countdownTimer: CountDownTimer? = null
    private var timeLeftInSeconds: Long = 30 // Oyun süresi

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener(this)
        startCountdownTimer()
        startGameLoop()
    }

    private fun startGameLoop() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                moveObjects()
                updateUI()
                handler.postDelayed(this, 20) // 20 milisaniyede bir güncelle
            }
        }
        handler.post(runnable)
    }

    private fun moveObjects() {
        // Nesneleri düşür
        objectY += objectSpeed

        // Eğer nesne alt kenara ulaştıysa yeni bir nesne oluştur
        if (objectY > screenHeight()) {
            objectY = 0f
            generateNewObject()
        }
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                playerX = event.x
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val currentX = event.x
                val deltaX = currentX - playerX

                // Güncelleme: Oyuncunun sağa sola hareket etmesi
                var newX = binding.player.x + deltaX

                // Sağ/Sol sınır kontrolü
                newX = newX.coerceIn(minX, maxX)

                // Güncelleme: Oyuncunun sağa sola hareket etmesi
                binding.player.x = newX

                // Oyuncu ve nesne çarpışma kontrolü
                if (isCollision()) {
                    // Nesneyi topla
                    score++
                    objectY = -500f // Resmi ekrandan dışarı çıkar
                    generateNewObject()
                }

                playerX = currentX
                return true
            }
        }
        return false
    }

    private fun isCollision(): Boolean {
        // Oyuncu ve nesne merkezlerinin koordinatlarını hesapla
        val playerCenterX = binding.player.x + binding.player.width / 2
        val playerCenterY = binding.player.y + binding.player.height / 2

        val objectCenterX = binding.ivMilk.x + binding.ivMilk.width / 2
        val objectCenterY = binding.ivMilk.y + binding.ivMilk.height / 2

        // Oyuncu ve nesne arasındaki mesafeyi hesapla
        val distanceX = Math.abs(playerCenterX - objectCenterX)
        val distanceY = Math.abs(playerCenterY - objectCenterY)

        // Oyuncu ve nesne arasındaki mesafeyi kontrol et
        val collision = (distanceX < binding.player.width / 2 + binding.ivMilk.width / 2) &&
                (distanceY < binding.player.height / 2 + binding.ivMilk.height / 2)

        return collision
    }

    private fun updateUI() {
        // Oyuncuyu ve nesneyi güncelle
        binding.ivMilk.y = objectY

        // Skoru güncelle
        binding.etScore.text = "$score"
    }

    private fun generateNewObject() {
        // Yeni bir nesne oluştur ve başlangıç pozisyonuna yerleştir
        val randomX = (2..(screenWidth() - binding.ivMilk.width)).random().toFloat()
        binding.ivMilk.x = randomX
    }

    private fun screenWidth(): Int {
        return resources.displayMetrics.widthPixels
    }

    private fun screenHeight(): Int {
        return resources.displayMetrics.heightPixels
    }

    private fun stopGame() {
        // Oyunu durdurma işlemleri
        handler.removeCallbacks(runnable)
    }

    private fun startCountdownTimer() {
        countdownTimer = object : CountDownTimer(timeLeftInSeconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Her saniyede bir çağrılır
                timeLeftInSeconds--
                binding.etTime.text = "$timeLeftInSeconds"
            }

            override fun onFinish() {
                // Süre bitince yapılacaklar
                stopGame()

                // Skorun finish fragmentına gönderilmesi
                val action = GameFragmentDirections.gameToFinish(score)
                findNavController().navigate(action)
            }
        }
        countdownTimer?.start()
    }

    override fun onDestroyView() {
        // Oyun döngüsünü durdur
        handler.removeCallbacks(runnable)
        countdownTimer?.cancel()
        super.onDestroyView()
    }
}