package com.mahmoud_ashraf.menustask.menu.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.menustask.R
import com.mahmoud_ashraf.menustask.databinding.ItemTagBinding

class TagsAdapter(private val onItemClicked: (TagsModel) -> Unit) :
    ListAdapter<TagsModel, TagsViewHolder>(TagsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        return TagsViewHolder(
            ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) =
        holder.bind(getItem(position),onItemClicked)

}

class TagsViewHolder(
    private val binding: ItemTagBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        data: TagsModel,
        onItemClicked: (TagsModel) -> Unit
    ) {
       with(binding){
           root.setOnClickListener { onItemClicked(data) }
           Glide.with(ivTag.context).load(Uri.parse(data.photoURL)).placeholder(R.drawable.img_placeholder).into(ivTag)
           tvTagName.text = data.tagName
       }
    }
}

class TagsDiffCallback : DiffUtil.ItemCallback<TagsModel>() {
    override fun areItemsTheSame(
        oldItem: TagsModel,
        newItem: TagsModel
    ): Boolean {
        return oldItem.tagName == newItem.tagName
    }

    override fun areContentsTheSame(
        oldItem: TagsModel,
        newItem: TagsModel
    ): Boolean {
        return oldItem == newItem
    }
}