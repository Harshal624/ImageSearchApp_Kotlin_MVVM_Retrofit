package harsh.com.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import harsh.com.imagesearchapp.R
import harsh.com.imagesearchapp.data.UnsplashPhoto
import harsh.com.imagesearchapp.databinding.FragmentGalleryBinding
import harsh.com.imagesearchapp.util.OnPhotoClickListener

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery), OnPhotoClickListener {

    private val viewmodel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!  //return non nullable binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        setHasOptionsMenu(true)

        val adapter = UnSplashPhotoAdapter(this)

        binding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.itemAnimator = null
            recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter {
                    adapter.retry()
                },
                footer = UnsplashPhotoLoadStateAdapter {
                    adapter.retry()
                }
            )
            btnRetry.setOnClickListener({
                adapter.retry()
            })
        }



        viewmodel.photos.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvNoresult.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && adapter.itemCount <= 0
                ) {
                    recyclerview.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }


            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.searchbar)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    binding.recyclerview.scrollToPosition(0)
                    viewmodel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnPhotoClick(photo: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }
}