package konrad.tercjak.flickr.data

import androidx.paging.DataSource
import konrad.tercjak.flickr.model.FlickrPhoto
import konrad.tercjak.flickr.network.FlickrApi

class PhotoDataFactory(private val flickrApi: FlickrApi,
                       private val query: String) : DataSource.Factory<Int, FlickrPhoto>() {

    override fun create(): DataSource<Int, FlickrPhoto> {
        return PhotoDataSource(flickrApi, query)
    }
}
