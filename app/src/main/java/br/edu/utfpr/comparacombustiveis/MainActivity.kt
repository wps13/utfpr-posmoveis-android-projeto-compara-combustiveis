package br.edu.utfpr.comparacombustiveis

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.comparacombustiveis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var opcaoAtual: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.opcao1Bt.setOnClickListener {
            opcaoAtual = 1
            onOpcaoClick()
        }

        binding.opcao2Bt.setOnClickListener {
            opcaoAtual = 2
            onOpcaoClick()
        }
    }

    private fun onOpcaoClick() {
        val intent = Intent(this, ListaCombustiveis::class.java)

        getOpcao.launch(intent)
    }

    private val getOpcao =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val opcao = it.data?.getStringExtra("opcaoNome")
                val consumo = it.data?.getDoubleExtra("opcaoConsumo", 0.0)

                val formattedConsumo = consumo
                    .toString()
                    .replace(".", ",")
                    .plus(" km/l")

                if (opcaoAtual == 1) {
                    binding.valueOpcao1.text = opcao
                    binding.opcao1Consumo.text =
                        getString(R.string.opcao_1_consumo, formattedConsumo)
                }
                if (opcaoAtual == 2) {
                    binding.valueOpcao2.text = opcao
                    binding.opcao2Consumo.text =
                        getString(R.string.opcao_2_consumo, formattedConsumo)
                }

                opcaoAtual = 0
            }
        }

}