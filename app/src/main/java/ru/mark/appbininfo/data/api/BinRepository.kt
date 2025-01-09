package ru.mark.appbininfo.data.api

import ru.mark.appbininfo.data.model.BinInfo

class BinRepository(private val apiService: BinApiService) {

    // Хранение кэша для информации о BIN, можно использовать Map или подобную структуру
    private val binInfoCache: MutableMap<String, BinInfo> = mutableMapOf()

    suspend fun fetchBinInfo(bin: String): BinInfo {
        // Проверяем кэш
        binInfoCache[bin]?.let {
            return it // Возвращаем информацию из кэша, если она существует
        }

        // Если не нашли в кэше, делаем запрос к API
        return try {
            val info = apiService.getBinInfo(bin)
            binInfoCache[bin] = info // Сохраняем в кэш
            info
        } catch (e: Exception) {
            // Обработка ошибок (например, сетевые ошибки)
            throw e // Можно выбрасывать исключение или возвращать другую ошибку
        }
    }
}