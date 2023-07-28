package com.sam.khabri.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sam.khabari.R
import com.sam.khabari.databinding.HeadingItemBinding
import com.sam.khabri.data.model.response.Article
import com.sam.khabri.utils.ApplicationUtils

class HeadingsAdapter :
    RecyclerView.Adapter<HeadingsAdapter.HeadingViewHolder>() {

    var items = mutableListOf<Article>()

    var listener: ((Article) -> Unit)? = null
    
    inner class HeadingViewHolder(val viewBinding: HeadingItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadingViewHolder {
        val binding = HeadingItemBinding.inflate(LayoutInflater.from(parent.context))
        return HeadingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(items: ArrayList<Article>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HeadingViewHolder, position: Int) {
        holder.viewBinding.headingText.text = items[position].title
        holder.viewBinding.authorText.text = items[position].author
        holder.viewBinding.postedDate.text =
            ApplicationUtils.getRelativeTime(items[position].publishedAt.toString())


        holder.viewBinding.headingImage.load(items[position].urlToImage) {
            crossfade(true)
            placeholder(
                AppCompatResources.getDrawable(
                    holder.viewBinding.headingImage.context,
                    R.drawable.place_holder
                )
            )
        }
        holder.viewBinding.root.setOnClickListener {
            listener?.invoke(items[position])
        }
    }
}