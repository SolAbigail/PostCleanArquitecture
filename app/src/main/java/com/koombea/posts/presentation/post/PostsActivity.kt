package com.koombea.posts.presentation.post.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.koombea.posts.R
import com.koombea.posts.databinding.ActivityPostsBinding
import com.koombea.posts.databinding.ItemPhotoBinding
import com.koombea.posts.presentation.post.DetailAdapter
import com.koombea.posts.presentation.post.adapter.PostsAdapter
import com.koombea.posts.presentation.post.PostsViewModel
import com.koombea.posts.utils.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_posts.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class PostsActivity() : AppCompatActivity(), DetailAdapter.OnAction{

    private lateinit var activityPostsBinding: ActivityPostsBinding
    private var mAdapter: PostsAdapter? = PostsAdapter()
    private val postsViewModel: PostsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostsBinding = DataBindingUtil.setContentView(this, R.layout.activity_posts)

        activityPostsBinding.postsRecyclerView.adapter = mAdapter

        getPosts()

        with(postsViewModel) {

            postsData.observe(this@PostsActivity, {
                activityPostsBinding.postsProgressBar.visibility = View.GONE
                mAdapter?.mPostList = it
                mAdapter?.mOnAction = this@PostsActivity
            })

            messageData.observe(this@PostsActivity, {
                Toast.makeText(this@PostsActivity, it, Toast.LENGTH_LONG).show()
            })

            swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
                getPosts()
            })

            swipeRefreshLayout.observe(this@PostsActivity, {
                activityPostsBinding.swipeRefresh.isRefreshing = false
            })
        }
    }

    override fun onDestroy() {
        mAdapter = null
        super.onDestroy()
    }

    private fun getPosts(){
        if (isNetworkAvailable()){
            postsViewModel.getPosts()
        }else {
            postsViewModel.getAllPosts()
        }
    }

    override fun onItemClick(url: String) {
        val binding: ItemPhotoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.item_photo,
            null,
            false
        )
        binding.url = url
        AlertDialog.Builder(this)
            .setView(binding.root)
            .show()
    }

}

