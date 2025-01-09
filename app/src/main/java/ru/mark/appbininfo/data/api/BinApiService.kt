package ru.mark.appbininfo.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.mark.appbininfo.data.model.BinInfo

interface BinApiService {
    @GET("{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BinInfo
}