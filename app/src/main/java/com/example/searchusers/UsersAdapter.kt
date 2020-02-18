package com.example.searchusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.single_user_view.view.*

class UserAdapter : PagedListAdapter<Item, UserAdapter.UserViewHolder>(UserDiffCallback()){
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName = view.userName!!
        val userIcon = view.UserIcon!!
        val userUrl = view.userUrl!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder{
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_user_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.userName.text = it.login
            holder.userUrl.text = it.url
            Glide.with(holder.userIcon.context).load(it.avatar_url).into(holder.userIcon)
        }

    }
}

class UserDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item ,newItem:Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem:Item, newItem: Item): Boolean {
        return newItem == oldItem
    }
}