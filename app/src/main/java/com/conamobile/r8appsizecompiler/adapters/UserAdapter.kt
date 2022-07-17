package com.conamobile.r8appsizecompiler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.conamobile.r8appsizecompiler.databinding.ItemUserBinding
import com.conamobile.r8appsizecompiler.model.User

class UserAdapter : ListAdapter<User, UserAdapter.Vh>(MyDiffUtil()) {
    lateinit var itemCLick: ((User) -> Unit)

    inner class Vh(private var itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(user: User) {
            itemUserBinding.apply {
                nameTv.text = user.name
                phoneTv.text = user.phone

                nameTv.setOnClickListener {
                    itemCLick.invoke(user)
                }
            }
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}