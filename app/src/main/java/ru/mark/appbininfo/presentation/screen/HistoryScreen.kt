package ru.mark.appbininfo.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.mark.appbininfo.presentation.viewmodel.BinViewModel

@Composable
fun HistoryScreen(navController: NavHostController, viewModel: BinViewModel = koinViewModel()) {
    val history = viewModel.getHistory() // Получаем историю запросов

    Column(modifier = Modifier.padding(16.dp)) {
        Text("История запросов")

        if (history.isEmpty()) {
            Text("История пуста", color = Color.Gray)
        } else {
            LazyColumn {
                items(history) { binInfo ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text("Страна: ${binInfo.country.name}")
                        Text("Тип карты: ${binInfo.type}")
                        Text("Банк: ${binInfo.bank.name}")
                        // Добавьте другие поля по необходимости
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("main") }) {
            Text(text = "Назад")
        }
    }
}