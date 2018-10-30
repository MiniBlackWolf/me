package study.kotin.my.baselibrary.data.net


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory
import study.kotin.my.baselibrary.common.baseurl
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor() {
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit
    private val interceptor: Interceptor

    init {
        interceptor = Interceptor {
            val request = it.request()
                    .newBuilder()
                    .header("Content-type", "application/json")
                    .header("charset", "utf-8")
                    .build()
            it.proceed(request)
        }
        retrofit = Retrofit.Builder()
                .baseUrl(baseurl.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initclient())
                .build()

    }

    private fun initclient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(httpLogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

    }

    private fun httpLogInterceptor(): Interceptor {
        HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            return this
        }
    }

    fun <T> creat(service: Class<T>): T {
        return retrofit.create(service)

    }
}