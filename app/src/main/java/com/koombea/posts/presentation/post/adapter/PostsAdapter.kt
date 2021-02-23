package com.koombea.posts.presentation.post.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koombea.posts.R
import com.koombea.posts.databinding.HolderPostBinding
import com.koombea.posts.domain.model.User
import com.koombea.posts.utils.extratNameUrl
import com.koombea.posts.utils.saveImgToCache
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class PostsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var mPostList: List<User> by Delegates.observable(emptyList()) {
        _,_,_ ->
        notifyDataSetChanged()
    }

    lateinit var mOnAction: DetailAdapter.OnAction

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderPostBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_post, parent, false
        )
        return PostViewHolder(holderPostBinding, parent.context)
    }

    private fun getItem(position: Int): User = mPostList[position]

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).onBind(getItem(position))
    }

    override fun getItemCount(): Int = if (mPostList.isNullOrEmpty()) 0 else mPostList.size

    private inner class PostViewHolder(private val viewDataBinding: ViewDataBinding, private val context: Context) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        private var mAdapter: DetailAdapter? = DetailAdapter()
        private var mAdapter2: DetailAdapter? = DetailAdapter()

        var listPics : ArrayList<String> = ArrayList()
        var listPics2 : ArrayList<String> = ArrayList()

        fun onBind(user: User) {
            (viewDataBinding as HolderPostBinding).user = user
            onSaveCahceImages(user.post.pics, viewDataBinding.root.context)
            viewDataBinding.rvPosts.adapter = mAdapter
            viewDataBinding.rvPosts2.adapter = mAdapter2

            listPics.clear()
            listPics2.clear()

            if(user.post.pics.size>3){
                listPics.add(user.post.pics[0])
                for (i in 1..(user.post.pics.size-1)) listPics2.add(user.post.pics[i])
            }else listPics= user.post.pics as ArrayList<String>

            val gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if(position == 0 && (user.post.pics.size == 1 || user.post.pics.size >= 3)){
                        2
                    }else 1
                }
            }
            viewDataBinding.rvPosts.layoutManager = gridLayoutManager
            mAdapter?.mPostList =  listPics
            mAdapter?.state = false
            mAdapter?.mOnAction = mOnAction

            mAdapter2?.mPostList =  listPics2
            mAdapter2?.state = true
            mAdapter2?.mOnAction = mOnAction
        }

        fun onSaveCahceImages(listUrl: List<String>, context: Context){
            for (url in listUrl){
                Picasso.get().load(url).into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        saveImgToCache(bitmap!!, extratNameUrl(url), context)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
                })
            }
        }
    }

}