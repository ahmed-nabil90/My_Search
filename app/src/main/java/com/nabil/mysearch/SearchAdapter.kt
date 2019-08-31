package com.nabil.mysearch

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nabil.mysearch.network.model.SearchItem
import kotlinx.android.synthetic.main.item_layout_search.view.*
import java.util.*


class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var data: List<SearchItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout_search, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<SearchItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(searchItem: SearchItem) = with(itemView) {
            tvTitle.text = searchItem.title
            setOnClickListener {

                val intent = Intent(Intent.ACTION_VIEW).apply {

                    data = Uri.parse(searchItem.link)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}