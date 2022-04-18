package com.mahmoud_ashraf.menustask.menu.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.menustask.R
import com.mahmoud_ashraf.menustask.databinding.ItemItemsOfTagBinding

class ItemsOfTagAdapter :
    ListAdapter<ItemOfTagModel, ItemOfTagViewHolder>(ItemOfTagDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemOfTagViewHolder {
        return ItemOfTagViewHolder(
            ItemItemsOfTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: ItemOfTagViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class ItemOfTagViewHolder(
    private val binding: ItemItemsOfTagBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        data: ItemOfTagModel
    ) {
       with(binding){
           Glide.with(ivItem.context).load(Uri.parse(data.photoUrl)).placeholder(R.drawable.img_placeholder).into(ivItem)
           tvItemName.text = data.name
       }
    }
}

class ItemOfTagDiffCallback : DiffUtil.ItemCallback<ItemOfTagModel>() {
    override fun areItemsTheSame(
        oldItem: ItemOfTagModel,
        newItem: ItemOfTagModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ItemOfTagModel,
        newItem: ItemOfTagModel
    ): Boolean {
        return oldItem == newItem
    }
}