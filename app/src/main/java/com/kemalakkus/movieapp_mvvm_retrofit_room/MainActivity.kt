package com.kemalakkus.movieapp_mvvm_retrofit_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kemalakkus.movieapp_mvvm_retrofit_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}