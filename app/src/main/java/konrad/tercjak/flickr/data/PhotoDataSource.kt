package konrad.tercjak.flickr.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import konrad.tercjak.flickr.model.FlickrPhoto
import konrad.tercjak.flickr.network.FlickrApi
import java.io.IOException

class PhotoDataSource(private val flickrApi: FlickrApi, private val query: String) :
        PageKeyedDataSource<Int, FlickrPhoto>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, FlickrPhoto>) {
        flickrApi.search(query)
                .subscribe({ response ->
                    val data = response.data
                    val items = data.items

                    if (data.total == 0) {
                        callback.onResult(items, 0, 0, null, null);
                    } else {
                        callback.onResult(
                                items,
                                data.page * data.perPage,
                                data.total,
                                null,
                                data.page + 1)
                    }

                }, this::onError
                )
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FlickrPhoto>) {
        flickrApi.search(query, params.key)
                .subscribe({ response ->
                    var nextPage: Int? = null
                    val data = response.data
                    if (data.page < data.pages) {
                        nextPage = data.page + 1
                    }
                    callback.onResult(data.items, nextPage)
                }, this::onError)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FlickrPhoto>) {
        flickrApi.search(query, params.key)
                .subscribe({ response ->
                    var previousPage: Int? = null
                    val data = response.data
                    if (data.page > 0) {
                        previousPage = data.page - 1
                    }
                    callback.onResult(data.items, previousPage)
                },this::onError)
    }

    fun onError(throwable: Throwable) {
        if (throwable is IOException) {
            Log.e("Network failure", "( inform the user and possibly retry")
        } else {
            Log.e("Conversion issue", "(  big problems")
        }
    }

}