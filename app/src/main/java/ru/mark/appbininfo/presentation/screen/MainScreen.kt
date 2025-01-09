package ru.mark.appbininfo.presentation.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.mark.appbininfo.presentation.viewmodel.BinViewModel

@Composable
fun MainScreen(navController: NavHostController, viewModel: BinViewModel = koinViewModel()) {
    val context = LocalContext.current
    var inputBin by remember { mutableStateOf("") }
    val binInfo by viewModel.binInfo.observeAsState()
    val error by viewModel.error.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = inputBin,
            onValueChange = { inputBin = it },
            label = { Text("Введите BIN") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (inputBin.isNotEmpty()) {
                    viewModel.fetchBinInfo(inputBin) // Запрос информации о BIN
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Получить информацию")
        }

        // Отображение информации о BIN
        binInfo?.let {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text("Страна: ${it.country.name}")
                Text("Широта: ${it.country.latitude}")
                Text("Долгота: ${it.country.longitude}")
                Text("Тип карты: ${it.scheme}")
                Text("Банк: ${it.bank.name}")
                // URL банка
                ClickableText(text = "URL банка: ${it.bank.url}") {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.bank.url))
                    context.startActivity(intent)
                }
                // Телефон банка
                ClickableText(text = "Телефон банка: ${it.bank.phone}") {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${it.bank.phone}"))
                    context.startActivity(intent)
                }
                // Координаты страны
                ClickableText(text = "Город: ${it.bank.city}") {
                    val uri = "geo:${it.country.latitude},${it.country.longitude}?q=${it.country.latitude},${it.country.longitude}(${it.bank.city})"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    context.startActivity(intent)
                }
            }
        } ?: run {
            Text("Введите BIN и нажмите 'Получить информацию'", color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("history") }) {
            Text(text = "История запросов")
        }

        error?.let {
            if (it != "") {
                Text(it, color = Color.Red)
            }
        }
    }
}
@Composable
fun ClickableText(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color.Blue,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    )
}