package com.example.app.ui.main

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.inSpans
import androidx.core.text.scale
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.FragmentMainBinding
import com.example.app.utility.typeface

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val typeface =
            ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)!!
        val text = SpannableStringBuilder("START 3-DAY FREE TRIAL")
            .scale(0.75f) { typeface(typeface) { append("\nthen ${"11,99 PLN"}/month") } }
        binding.startFreeTrialButton.text = text
    }

    companion object {

        fun newInstance() = MainFragment()
    }
}
