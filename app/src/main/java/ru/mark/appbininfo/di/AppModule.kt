import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mark.appbininfo.data.api.BinApiService
import ru.mark.appbininfo.data.api.BinRepository
import ru.mark.appbininfo.presentation.screen.SharedPrefHelper
import ru.mark.appbininfo.presentation.viewmodel.BinViewModel
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://lookup.binlist.net/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(BinApiService::class.java) }// Создание экземпляра API
    single { BinRepository(get()) } // Внедрение зависимости
    single { SharedPrefHelper(get()) } // Внедрение зависимости
    viewModel { BinViewModel(get(), get()) } // Внедрение ViewModel
}