package konrad.tercjak.flickr.model

import com.squareup.moshi.Json


data class FlickrSizesResponse(
    @field:Json(name = "sizes") val data: FlickrPhotoSizeList
)

data class FlickrPhotoSizeList(
    @field:Json(name = "size")
    val items:List<FlickrImage>
)
data class FlickrImage(
    @field:Json(name = "label")
    val label:String,
    @field:Json(name = "width")
    val width:Int,
    @field:Json(name = "height")
    val height:Int,
    @field:Json(name = "source")
    val url:String
)
