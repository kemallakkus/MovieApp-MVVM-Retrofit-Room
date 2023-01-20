package com.kemalakkus.movieapp_mvvm_retrofit_room.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.kemalakkus.movieapp_mvvm_retrofit_room.R
import com.kemalakkus.movieapp_mvvm_retrofit_room.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        val nav = Navigation.findNavController(binding.root.findViewById(R.id.movieNavHostFragment))
        binding.bottomNav.setupWithNavController(nav)
        nav.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.favoriteFragment -> binding.bottomNav.visibility = View.VISIBLE
                else -> binding.bottomNav.visibility = View.GONE
            }
        }

        return binding.root

    }
}



