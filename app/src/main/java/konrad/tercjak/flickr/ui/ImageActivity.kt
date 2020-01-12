package konrad.tercjak.flickr.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.image_activity.*

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import konrad.tercjak.flickr.R


class ImageActivity : AppCompatActivity() {
    companion object {
        val URL = "URL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_activity)

        val bundle = intent.extras
        if (bundle != null) {
            val url = bundle.getString(URL, null)
            if (url != null) {
                loadImage(url)
            }
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val url = savedInstanceState.getString(URL, null)

        if (url != null) {
            loadImage(url)
        }
    }
    fun loadImage(url:String){
        Glide.with(image_view)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(image_view)
    }

}
