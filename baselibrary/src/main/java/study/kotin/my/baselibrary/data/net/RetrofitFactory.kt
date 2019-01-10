package study.kotin.my.baselibrary.data.net


import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory
import study.kotin.my.baselibrary.common.baseurl
import java.util.concurrent.TimeUnit
import okhttp3.Cookie
import android.R.attr.host
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.open.utils.Global.getSharedPreferences
import study.kotin.my.baselibrary.common.BaseApplication


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
        val cookieStore = HashMap<String, List<Cookie>>()
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(httpLogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .cookieJar(object : CookieJar {
                    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                        val gson = Gson()
                        val toJson = gson.toJson(cookies)
                        BaseApplication.context.getSharedPreferences("userCookie",Context.MODE_PRIVATE).edit().putString(url.host(),toJson).apply()
                        cookieStore.put(url.host(), cookies)
                    }

                    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                        val cookies:List<Cookie>?
                        if(cookieStore.size==0){
                            val gson = Gson()
                            val string = BaseApplication.context.getSharedPreferences("userCookie", Context.MODE_PRIVATE).getString(url.host(), "")
                            if(string==""){
                                return ArrayList()
                            }
                            val list = gson.fromJson(string,object:TypeToken<List<Cookie>>(){}.type) as List<Cookie>
                            cookies= list
                        }else{
                            cookies = cookieStore[url.host()]
                        }
                        return cookies?.toMutableList() ?: ArrayList()
                    }
                })
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