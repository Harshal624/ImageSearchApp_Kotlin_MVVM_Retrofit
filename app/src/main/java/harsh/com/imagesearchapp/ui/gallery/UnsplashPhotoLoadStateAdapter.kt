package harsh.com.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import harsh.com.imagesearchapp.databinding.PhotoLoadstateFooterBinding

class UnsplashPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnsplashPhotoLoadStateAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding =
            PhotoLoadstateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(binding).also {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    class ViewHolder(private val binding: PhotoLoadstateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /*  init {
              binding.btnRetry.setOnClickListener{
                  retry.invoke()
              }
          }*/

        fun bind(loadState: LoadState) {

            binding.apply {
                progressbar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvError.isVisible = loadState !is LoadState.Loading
            }

        }

    }
}