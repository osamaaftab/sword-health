package com.osamaaftab.dog.ui.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.osamaaftab.dog.R
import com.osamaaftab.dog.databinding.ItemBreedsBinding
import com.osamaaftab.dog.model.Breeds
import com.osamaaftab.dog.ui.common.GlideImageLoader

class BreedsAdapter(
    private val goToBreedDetail: (breed: Breeds) -> Unit
) : RecyclerView.Adapter<BreedsAdapter.ItemViewHolder>() {

    private var listBreeds = mutableListOf<Breeds>()
    private var isLoadingMoreItems = false
    private var ascendingOrder = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBreedsBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listBreeds[position]
        holder.bind(item)

        if (position == itemCount - 1 && itemCount < listBreeds.size)
            isLoadingMoreItems = true
    }

    override fun getItemCount(): Int {
        return listBreeds.size
    }

    fun addBreeds(newBreeds: List<Breeds>) {
        listBreeds.addAll(newBreeds)
        sortBreeds()
        changeDataSet()
    }

    fun toggleSortingOrder() {
        ascendingOrder = !ascendingOrder
        sortBreeds()
        changeDataSet()
    }

    private fun sortBreeds() {
        listBreeds.sortWith(compareBy { it.name })

        if (!ascendingOrder)
            listBreeds.reverse()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeDataSet() = notifyDataSetChanged()

    inner class ItemViewHolder(private val binding: ItemBreedsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Breeds) {
            binding.tvName.text = item.name
            loadImage(item)
            clickCard(item)
        }

        private fun loadImage(item: Breeds) {

            val options = RequestOptions()
                .centerCrop()
                .error(R.drawable.icon_placeholder)
                .priority(Priority.HIGH)

            GlideImageLoader(binding.ivDog, binding.progressBar).load(
                item.image?.url,
                options
            )
        }

        private fun clickCard(item: Breeds) {
            binding.card.setOnClickListener {
                goToBreedDetail(item)
            }
        }
    }
}