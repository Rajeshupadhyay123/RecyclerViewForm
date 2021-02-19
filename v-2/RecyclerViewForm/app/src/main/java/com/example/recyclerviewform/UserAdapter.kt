package com.example.recyclerviewform

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewform.database.UserTable
import com.example.recyclerviewform.databinding.ListViewBinding

class UserAdapter(val clickListener: UserListener) :
    ListAdapter<UserTable, UserAdapter.ViewHolder>(UserAdapter.UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, clickListener)
    }

    fun getPositionAt(position: Int): UserTable {
        val item = getItem(position)
        return item!!
    }


    class ViewHolder private constructor(val binding: ListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val firstData: TextView = binding.firstData
        val secondData: TextView = binding.secondData

        fun bind(
            item: UserTable,
            clickListener: UserListener
        ) {
            firstData.text = item.firstData.toString()
            secondData.text = item.secondData.toString()
            binding.table = item
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    class UserDiffCallback() : DiffUtil.ItemCallback<UserTable>() {
        override fun areItemsTheSame(oldItem: UserTable, newItem: UserTable): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserTable, newItem: UserTable): Boolean {
            return oldItem == newItem
        }

    }
}

class UserListener(val clickListener: (userId: Long) -> Unit) {
    fun onClick(table: UserTable) = clickListener(table.userId)
}