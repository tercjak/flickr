package konrad.tercjak.flickr.network


import io.reactivex.Observable
import io.reactivex.Single
import konrad.tercjak.flickr.model.FlickrSearchResponse
import konrad.tercjak.flickr.model.FlickrSizesResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    companion object {
        const val apiKey = "9a95c68a9c6ec61104cd3967dcbb8bd3"
        const val defaults = "api_key=$apiKey&nojsoncallback=1&format=json"
        val httpClient = OkHttpClient.Builder()
        val retrofit: Retrofit


        init {
            retrofit = Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl("https://api.flickr.com/services/rest/")
                    .addConverterFactory(MoshiConverterFactory.create().asLenient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

        }

        fun getApi(): FlickrApi {
            return retrofit.create(FlickrApi::class.java)
        }
    }

    @GET("?method=flickr.photos.search&$defaults")
    fun search(
            @Query("tags") tags: String,
            @Query("page") page: Int=1
    ): Single<FlickrSearchResponse>

    @GET("?method=flickr.photos.getSizes&$defaults")
    fun getSizes(@Query("photo_id") id: String): Observable<FlickrSizesResponse>


}

