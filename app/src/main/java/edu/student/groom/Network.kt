package edu.student.groom

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {
    
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8765/institute/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    
    
    
    companion object {
        
        @Volatile
        private var instance: Network? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Network().also { instance = it }
            }
    }

}