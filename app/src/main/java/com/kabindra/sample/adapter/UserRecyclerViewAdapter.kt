package com.kabindra.sample.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kabindra.sample.R
import com.kabindra.sample.base.RecyclerViewBaseAdapter
import com.kabindra.sample.db.model.UserData
import kotlinx.android.synthetic.main.user_row.view.*
import kotlin.properties.Delegates


class UserRecyclerViewAdapter(
    val context: Context?,
    val onItemClick: (userData: UserData) -> Unit
) :
    RecyclerViewBaseAdapter<UserData>() {
    override var itemLayoutId = R.layout.user_row
    override var items: List<UserData>
            by Delegates.observable(emptyList()) { _, old, new ->
                autoNotify(old, new) { o, n -> o == n }
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            itemView.apply {
                // item_iv.imageHandler(item.image)
                user_name.text = item.name!!.official
                user_currency.text = item.region!!

                itemView.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }


    fun setUser(user: List<UserData>) {
        this.items = user
        notifyDataSetChanged()
    }
}