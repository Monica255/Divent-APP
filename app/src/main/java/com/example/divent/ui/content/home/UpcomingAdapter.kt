package com.example.divent.ui.content.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.databinding.ItemUpcomingEventBinding
import com.bumptech.glide.Glide
import com.example.divent.R
import com.example.divent.ui.MyDiffCallback


class UpcomingAdapter(
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<UpcomingAdapter.ItemViewHolder>() {

    var list = mutableListOf<Event>()
    lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemUpcomingEventBinding = ItemUpcomingEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        context = parent.context
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ItemViewHolder(private val binding: ItemUpcomingEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.root.setOnClickListener {
                onClick.invoke(event.id)
            }
            Glide.with(context)
                .load(event.imageLogo)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.ivBanner)

            binding.tvTitle.text =event.name
        }
    }

    fun updateData(newItems: List<Event>) {
        val diffCallback = MyDiffCallback(list, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}