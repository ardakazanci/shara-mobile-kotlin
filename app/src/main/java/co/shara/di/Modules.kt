package co.shara.di

import android.content.Context
import androidx.room.Room
import co.shara.BuildConfig
import co.shara.data.Database
import co.shara.data.repo.UserRepository
import co.shara.data.retrofit.UserAPI
import co.shara.network.AuthInterceptor
import co.shara.settings.Settings
import co.shara.ui.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

fun injectFeature() = loadFeature

private val loadFeature by lazy {

    loadKoinModules(
        listOf(
            retrofitModule,
            networkingModule,
            databaseModule,
            daoModule,
            repositoriesModule,
            viewModelsModule,
            settingsModule
        )
    )
}

val retrofitModule = module(override = true) {
    single {

        val baseUrl = "https://shara-api.herokuapp.com/"

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = when (BuildConfig.BUILD_TYPE) {
            "release" -> HttpLoggingInterceptor.Level.NONE
            else -> HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor(get()))
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}

val networkingModule = module {
    single<UserAPI> { get<Retrofit>().create() }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "shara_db"
        ).build()
    }
}

val daoModule = module {
    single { get<Database>().userDao() }
}

val repositoriesModule = module {
    single { UserRepository(get(), get(), get()) }
}

val viewModelsModule = module {
    viewModel { UserViewModel(get()) }
}

val settingsModule = module {
    single {
        androidContext().getSharedPreferences(
            "shara_settings",
            Context.MODE_PRIVATE
        )
    }
    single {
        Settings(get())
    }
}