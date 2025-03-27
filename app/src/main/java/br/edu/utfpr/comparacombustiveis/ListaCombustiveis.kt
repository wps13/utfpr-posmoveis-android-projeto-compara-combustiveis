package br.edu.utfpr.comparacombustiveis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.comparacombustiveis.databinding.ActivityListaCombustiveisBinding

class ListaCombustiveis : AppCompatActivity() {
    private lateinit var binding: ActivityListaCombustiveisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaCombustiveisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listaCombustiveis.setOnItemClickListener { _, _, position, _ ->
            val consumo = when (position) {
                0 -> 12.9
                1 -> 9.1
                else -> 0.0
            }

            val nome = when (position) {
                0 -> "Gasolina"
                else -> "Álcool"
            }
            intent.putExtra("opcaoNome", nome)
            intent.putExtra("opcaoConsumo", consumo)

            setResult(RESULT_OK, intent)

            finish()
        }

    }
}