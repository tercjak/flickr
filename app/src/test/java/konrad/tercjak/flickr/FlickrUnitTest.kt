package konrad.tercjak.flickr

import konrad.tercjak.flickr.network.FlickrApi
import org.junit.Test

class FlickrUnitTest {
    @Test
    fun request_isCorrect() {
        val api = FlickrApi.getApi()
        api.search("kittens", 1).subscribe({ response ->
            assert(response.data.total > 0)
        }, {
            assert(false)
        })
    }
}

