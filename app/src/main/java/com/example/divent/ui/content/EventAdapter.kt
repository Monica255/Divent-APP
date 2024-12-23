package com.example.divent.ui.content

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.divent.R
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.databinding.ItemEventBinding
import com.example.divent.ui.MyDiffCallback


class EventAdapter(
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<EventAdapter.ItemViewHolder>() {

    var list = mutableListOf<Event>()
    lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemEventBinding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        context = parent.context
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ItemViewHolder(private val binding: ItemEventBinding) :
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
            val text = "${event.registrants}/${event.quota}"
            binding.tvRegis.text = text
            val time = event.beginTime

            val datePart = time.substringBefore(" ")
            val clockPart = time.substringAfter(" ")

            binding.tvDate.text = datePart
            binding.tvClock.text = clockPart


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