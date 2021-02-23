package com.koombea.posts.presentation.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.koombea.posts.R
import com.koombea.posts.databinding.Holder2DetailBinding
import com.koombea.posts.databinding.HolderDetailBinding
import com.koombea.posts.utils.extratNameUrl
import com.koombea.posts.utils.getPathFile
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates


class DetailAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnAction {
        fun onItemClick(url: String)
    }

    lateinit var mOnAction: OnAction

    var mPostList: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    var state: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderDetailBinding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context), viewType, parent, false
        )
        return if (state) Detail2ViewHolder(holderDetailBinding)
        else DetailViewHolder(holderDetailBinding)
    }

    private fun getItem(position: Int): String = mPostList[position]

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(state)(holder as DetailAdapter.Detail2ViewHolder).onBind(getItem(position))
        else (holder as DetailAdapter.DetailViewHolder).onBind(getItem(position))
    }

    override fun getItemCount(): Int = if (mPostList.isNullOrEmpty()) 0 else mPostList.size

    private inner class DetailViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(url: String) {
            (viewDataBinding as HolderDetailBinding).url = url
            viewDataBinding.ivPhoto.setOnClickListener(View.OnClickListener {
                mOnAction.onItemClick(url)
            })
        }
    }

    private inner class Detail2ViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun onBind(url: String) {
            (viewDataBinding as Holder2DetailBinding).url = getPathFile(extratNameUrl(url), viewDataBinding.root.context).path
            viewDataBinding.ivPhoto.setOnClickListener(View.OnClickListener {
                mOnAction.onItemClick(url)
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (state) {
            return R.layout.holder2_detail
        } else
            return R.layout.holder_detail
    }
}