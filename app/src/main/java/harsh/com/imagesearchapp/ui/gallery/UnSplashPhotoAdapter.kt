package harsh.com.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import harsh.com.imagesearchapp.R
import harsh.com.imagesearchapp.data.UnsplashPhoto
import harsh.com.imagesearchapp.databinding.ItemImageBinding
import harsh.com.imagesearchapp.util.OnPhotoClickListener

class UnSplashPhotoAdapter(private val listener: OnPhotoClickListener) :
    PagingDataAdapter<UnsplashPhoto, UnSplashPhotoAdapter.ViewHolder>(PHOTO_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean =
                oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position: Int = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null)
                        listener.OnPhotoClick(item)
                }
            }
        }

        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView).load(photo.urls.small)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(ivImage)

                tvUsername.setText(photo.user.username)
            }
        }

    }
}