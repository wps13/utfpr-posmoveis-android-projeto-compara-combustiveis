package br.edu.utfpr.comparacombustiveis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.comparacombustiveis.databinding.ActivityInfoBinding
import br.edu.utfpr.comparacombustiveis.databinding.ActivityMainBinding

class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.infoIcon.setOnClickListener {
            finish()
        }
    }
}