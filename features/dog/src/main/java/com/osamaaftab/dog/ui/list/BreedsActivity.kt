package com.osamaaftab.dog.ui.list

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.osamaaftab.dog.R
import com.osamaaftab.dog.databinding.ActivityBreedsBinding
import com.osamaaftab.dog.model.Breeds
import com.osamaaftab.dog.ui.details.BreedsDetailActivity
import com.osamaaftab.dog.ui.list.adapter.BreedsAdapter
import com.osamaaftab.dog.ui.search.BreedsSearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedsBinding
    private val viewModel: BreedsViewModel by viewModels()

    private val listLayoutManager = LinearLayoutManager(this)
    private val gridLayoutManager = GridLayoutManager(this, PARAM_GRID_COUNT)

    private lateinit var adapter: BreedsAdapter
    private var layoutManager: RecyclerView.LayoutManager = listLayoutManager

    private var isLoadingMoreItems = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBreedsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAdapter()
        setupObservers()
    }

    private fun setupView() {
        viewModel.loadBreeds()

        animation(R.drawable.animation_list, binding.ivLoading)

        binding.toolbar.setNavigationOnClickListener {
           finish()
        }

        binding.ivGrid.setOnClickListener {
            changeLayoutView()
        }

        binding.ivOrder.setOnClickListener {
            adapter.toggleSortingOrder()
        }

        binding.iSearch.etSearch.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val textSearch = binding.iSearch.etSearch.text.toString()
                    startActivity(BreedsSearchActivity.newInstance(this, textSearch))
                    return@OnEditorActionListener true
                }
                false
            }
        )

        binding.rvBreeds.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = when (layoutManager) {
                    is LinearLayoutManager -> (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    is GridLayoutManager -> (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    else -> RecyclerView.NO_POSITION
                }

                val totalItemCount = layoutManager.itemCount

                checkLoadMoreBreeds(lastVisibleItemPosition, totalItemCount)
            }
        })
    }

    private fun setupAdapter() {
        adapter = BreedsAdapter(::goToBreedDetail)
        binding.rvBreeds.adapter = adapter
        binding.rvBreeds.layoutManager = listLayoutManager
    }

    private fun setupObservers() {
        viewModel.breedsLiveData.observe(this) { dogsList ->
            adapter.addBreeds(dogsList)
            binding.llLoading.visibility = View.GONE
            isLoadingMoreItems = false
        }

        viewModel.breedsErrorLiveData.observe(this) {
            binding.ivError.visibility = View.VISIBLE
            binding.tvError.visibility = View.VISIBLE
        }
    }

    private fun goToBreedDetail(breeds: Breeds) {
        startActivity(BreedsDetailActivity.newInstance(this, breeds.id))
    }

    private fun checkLoadMoreBreeds(lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isLoadingMoreItems && lastVisibleItemPosition == totalItemCount - 1) {
            isLoadingMoreItems = true
            binding.llLoading.visibility = View.VISIBLE
            viewModel.loadMoreBreeds()
        }
    }

    private fun animation(drawable: Int, imageView: ImageView) {
        imageView.setBackgroundResource(drawable)
        val animation = imageView.background as AnimationDrawable
        animation.setEnterFadeDuration(PARAM_FADE)
        animation.setExitFadeDuration(PARAM_FADE)
        animation.start()
    }

    private fun changeLayoutView() {
        if (layoutManager == listLayoutManager) {
            layoutManager = gridLayoutManager
            binding.ivGrid.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_list))
        } else {
            layoutManager = listLayoutManager
            binding.ivGrid.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_grid))
        }
        binding.rvBreeds.layoutManager = layoutManager
        adapter.changeDataSet()
    }

    companion object {
        private const val PARAM_FADE = 1500
        private const val PARAM_GRID_COUNT = 2
    }
}