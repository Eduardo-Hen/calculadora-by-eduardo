package com.example.calculadorabyeduardo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadorabyeduardo.ui.theme.CalculadoraByEduardoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraByEduardoTheme {
                    Calculadora()

                }
            }
        }
    }


val buttonList = listOf(
    "1", "2", "3", "C",
    "4", "5", "6", "+",
    "7", "8", "9", "-",
    "0", "*", "/", "="
)



@Preview(showBackground = true)
@Composable
fun Calculadora(modifier: Modifier = Modifier) {

    var visor by remember { mutableStateOf("263") } // Estado do visor
    var result by remember { mutableStateOf("") } // Estado do resultado

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        Spacer(modifier = modifier.size(70.dp))

        Text(
            text = visor,
            style = TextStyle(fontSize = 50.sp, color = Color.Gray),
            modifier = modifier.align(alignment = Alignment.End)
        )
        Spacer(modifier = modifier.size(50.dp))

        // Exibe o resultado
        Text(
            text = result,
            style = TextStyle(fontSize = 80.sp, color = Color.Gray),
            modifier = modifier.align(alignment = Alignment.End)
        )
        Spacer(modifier = modifier.size(50.dp))

        // Chama Botoes e atualiza o visor e o resultado
        Botoes(onButtonClick = { valor ->
            if (valor == "=") {
                result = calcularExpressao(visor) // Calcula e mostra o resultado
                visor = ""
            }
            if (valor == "C") {
                visor = "" // Limpa o visor
                result = "" // Limpa o resultado
            }
            else {
                if(valor!="=")
                visor += valor // Adiciona o valor ao visor
            }
        })
    }
}

@Composable
fun Botoes(onButtonClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(buttonList) { btn ->
                DesingBotoes(btn = btn, onClick = { onButtonClick(btn) }) // Chama a função ao clicar
            }
        }
    }
}

@Composable
fun DesingBotoes(btn: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(10.dp)) {
        FloatingActionButton(
            onClick = { onClick() }, // Executa a ação ao clicar no botão
            modifier = Modifier.size(80.dp),
            shape = CircleShape
        ) {
            Text(text = btn, style = TextStyle(fontSize = 50.sp))
        }
    }
}

// Função para calcular a expressão
fun calcularExpressao(expressao: String): String {
    return try {
        val visor = when {
            expressao.contains("+") -> {
                val (a, b) = expressao.split("+").map { it.trim().toInt() }
                a + b
            }
            expressao.contains("-") -> {
                val (a, b) = expressao.split("-").map { it.trim().toInt() }
                a - b
            }
            expressao.contains("*") -> {
                val (a, b) = expressao.split("*").map { it.trim().toInt() }
                a * b
            }
            expressao.contains("/") -> {
                val (a, b) = expressao.split("/").map { it.trim().toInt() }
                a / b
            }
            else -> expressao.toInt()
        }
        visor.toString()
    } catch (e: Exception) {
        "Erro"
    }
}

