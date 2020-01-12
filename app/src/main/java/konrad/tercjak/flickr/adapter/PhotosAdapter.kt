package konrad.tercjak.flickr.adapter

import android.util.Log
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import konrad.tercjak.flickr.adapter.PhotosAdapter.PhotoHolder
import androidx.recyclerview.widget.DiffUtil
import konrad.tercjak.flickr.network.FlickrApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import konrad.tercjak.flickr.R
import konrad.tercjak.flickr.model.FlickrPhoto
import java.io.IOException


class PhotosAdapter(private val onClick: ((String) -> Unit)) : PagedListAdapter<FlickrPhoto, PhotoHolder>(getFlickrPhotoDiff()) {

    fun onError(throwable: Throwable) {
        if (throwable is IOException) {
            Log.e("Network failure", "( inform the user and possibly retry")
        } else {
            Log.e("Conversion issue", "(  big problems")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PhotoHolder(inflater.inflate(R.layout.photo_item, null))
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val photo: FlickrPhoto = getItem(position) ?: return
        val call = FlickrApi.getApi().getSizes(photo.id)

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ root ->
                    val sizes = root.data.items

                    val square = sizes.find { it.label == "Large Square" }
                    val medium = sizes.find { it.label == "Large" }

                    val minSquare = sizes.find { it.label == "Square" }

                    holder.image?.let {
                        Glide.with(it)
                                .load(square?.url)
                                .thumbnail(
                                        Glide.with(it).load(minSquare?.url)
                                )
                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                                .into(it)

                        it.setOnClickListener {
                            if (medium != null && square != null) {
                                onClick(medium.url)
                            }
                        }
                    }

                }, this::onError
                )
    }

    class PhotoHolder(itemView: View) : ViewHolder(itemView) {
        var image: ImageView? = null

        init {
            image = itemView.findViewById(R.id.image)
        }
    }

    companion object {
        fun getFlickrPhotoDiff() = object : DiffUtil.ItemCallback<FlickrPhoto>() {
            override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
                return oldItem?.id.equals(newItem?.id)
            }

            override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
                return oldItem == newItem
            }
        }
    }
}
