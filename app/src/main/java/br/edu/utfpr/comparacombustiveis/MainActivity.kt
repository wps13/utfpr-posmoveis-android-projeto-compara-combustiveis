package br.edu.utfpr.comparacombustiveis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Visibility
import br.edu.utfpr.comparacombustiveis.databinding.ActivityMainBinding
import kotlin.text.*

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

        binding.resultadoBt.setOnClickListener {
            onResultadoClick()
        }

        binding.imageView.setOnClickListener {
            onInfoClick()
        }
    }

    private fun onInfoClick() {
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("DefaultLocale")
    private fun onResultadoClick() {
        val opcao1Value = binding.valueOpcao1.text
        val opcao2Value = binding.valueOpcao2.text

        if (checaCamposVazios(opcao1Value, opcao2Value)) return

        val opcao1Preco = binding.precoCombustivel1.text
        val opcao2Preco = binding.precoCombustivel2.text

        if (checaPrecosVazios(opcao1Preco, opcao2Preco)) return

        val gasolinaOption = if (opcao1Value.toString().lowercase() == "gasolina") {
            1
        } else {
            2
        }


        val opcao1Consumo = binding.opcao1ConsumoValue
            .text
            .toString()
            .replace(",", ".")
            .toDouble()


        val opcao2Consumo = binding.opcao2ConsumoValue
            .text
            .toString()
            .replace(",", ".")
            .toDouble()


        val alcoolPreco: Double
        val gasolinaPreco: Double

        val alcoolConsumo: Double
        val gasolinaConsumo: Double

        if (gasolinaOption == 1) {
            alcoolPreco = opcao2Preco.toString().toDouble()
            gasolinaPreco = opcao1Preco.toString().toDouble()
            alcoolConsumo = opcao2Consumo
            gasolinaConsumo = opcao1Consumo
        } else {
            gasolinaPreco = opcao2Preco.toString().toDouble()
            alcoolPreco = opcao1Preco.toString().toDouble()
            gasolinaConsumo = opcao2Consumo
            alcoolConsumo = opcao1Consumo
        }


        val rendimento = (alcoolConsumo / gasolinaConsumo)

        val precoMaxAlcool = rendimento * gasolinaPreco

        val combustivelMaisBarato = if (alcoolPreco <= precoMaxAlcool) "Álcool" else "Gasolina"

        binding.resultadoDescricao.text =
            getString(R.string.resultado_descricao, combustivelMaisBarato)

        val rendimentoFormatado = String.format("%.2f", rendimento * 100)
        val precoMaxFormatado = String.format("R$ %.2f", precoMaxAlcool)

        binding.resultadoDescricao2.text =
            getString(R.string.resultado_descricao_2, rendimentoFormatado, precoMaxFormatado)
        binding.resultadoDescricao2.visibility = View.VISIBLE
    }

    private fun checaPrecosVazios(
        opcao1Preco: Editable,
        opcao2Preco: Editable
    ): Boolean {
        val errorMessagePreco = "O preço do combustivel deve ser fornecido"

        if (opcao1Preco.isEmpty()) {
            binding.precoCombustivel1.error = errorMessagePreco
            return true
        }

        if (opcao2Preco.isEmpty()) {
            binding.precoCombustivel2.error = errorMessagePreco
            return true
        }
        return false
    }

    private fun checaCamposVazios(
        opcao1Value: CharSequence,
        opcao2Value: CharSequence
    ): Boolean {
        val errorMessage = "É necessário definir um combustível"
        if (opcao1Value.isEmpty()) {
            binding.valueOpcao1.error = errorMessage
            return true
        }
        if (opcao2Value.isEmpty()) {
            binding.valueOpcao2.error = errorMessage
            return true
        }

        if (opcao1Value == opcao2Value) {
            binding.resultadoDescricao.text = getString(
                R.string.resultado_descricao,
                "nenhum, os combustíveis escolhidos são iguais."
            )
            binding.resultadoDescricao2.text =
                getString(R.string.resultado_descricao_2, "", "")
            binding.resultadoDescricao2.visibility = View.INVISIBLE
            return true
        }
        return false
    }

    private fun onOpcaoClick() {
        val intent = Intent(this, ListaCombustiveisActivity::class.java)

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

                if (opcaoAtual == 1) {
                    binding.valueOpcao1.text = opcao
                    binding.valueOpcao1.error = null
                    binding.opcao1ConsumoValue.text = formattedConsumo
                    binding.opcao1ConsumoValue.error = null
                }
                if (opcaoAtual == 2) {
                    binding.valueOpcao2.text = opcao
                    binding.valueOpcao2.error = null
                    binding.opcao2ConsumoValue.text = formattedConsumo
                    binding.opcao2ConsumoValue.error = null
                }

                opcaoAtual = 0
            }
        }

}