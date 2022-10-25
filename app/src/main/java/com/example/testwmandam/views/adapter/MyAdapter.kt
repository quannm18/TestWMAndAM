package com.example.testwmandam.views.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testwmandam.databinding.ItemTimeBinding
import com.example.testwmandam.model.TimeModel

class MyAdapter : PagingDataAdapter<TimeModel, MyAdapter.MyViewHolder>(Diff()) {
    private val listener: MutableLiveData<Any> = MutableLiveData()
    val event: LiveData<Any> by lazy {
        listener
    }

    class Diff() : DiffUtil.ItemCallback<TimeModel>() {
        override fun areItemsTheSame(oldItem: TimeModel, newItem: TimeModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TimeModel, newItem: TimeModel): Boolean {
            return oldItem == newItem
        }

    }

    inner class MyViewHolder(private val binding: ItemTimeBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(timeModel: TimeModel) {
            binding.tvTime.text = "${timeModel.hour} : ${timeModel.minute}"

            binding.imageDelete.setOnClickListener {
                listener.postValue(timeModel)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding
        )
    }

}