package konrad.tercjak.flickr.model

import com.squareup.moshi.Json

data class FlickrSearchResponse(
    @field:Json(name = "photos") val data: FlickrPhotoList
)

data class FlickrPhotoList(
    @field:Json(name = "page")
    val page: Int,
    @field:Json(name = "pages")
    val pages: Long,
    @field:Json(name = "perpage")
    val perPage: Int,
    @field:Json(name = "total")
    val total: Int,
    @field:Json(name = "photo")
    var items: List<FlickrPhoto>
)

data class FlickrPhoto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "title")
    val title: String
)

