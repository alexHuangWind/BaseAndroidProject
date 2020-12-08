package com.publica.baseproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.publica.baseproject.adapter.PostListAdapter
import com.publica.baseproject.databinding.FragmentHomeBinding
import com.publica.baseproject.model.Post
import com.publica.baseproject.model.State
import com.publica.baseproject.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment: BaseFragment<HomeViewModel,FragmentHomeBinding>() {
    private val mAdapter = PostListAdapter(this::onItemClicked)
    override val mViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val root = mViewBinding.root
        mViewBinding.postsRecyclerView.adapter = mAdapter
        mViewModel.postsLiveData.observe(viewLifecycleOwner){
            state->
            when(state){
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        mAdapter.submitList(state.data.toMutableList())
                        showLoading(false)                    }
                }
                is State.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                    showLoading(false)
                }
            }
        }
        initPosts()
        return root
    }

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)


    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }
    private fun onItemClicked(post: Post, imageView: ImageView) {
        val postId = post.id ?: run {
            Toast.makeText(context, "Unable to launch details", Toast.LENGTH_LONG).show()
            return
        }
        Toast.makeText(context, "PostDetailsActivity", Toast.LENGTH_LONG).show()
//        val intent = PostDetailsActivity.getStartIntent(this, postId)
//        startActivity(intent, options.toBundle())
    }
    private fun initPosts() {
        mViewModel.postsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        mAdapter.submitList(state.data.toMutableList())
                        showLoading(false)
                    }
                }
                is State.Error -> {
                    showLoading(false)
                }
            }
        }

        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getPosts()
        }

        // If State isn't `Success` then reload posts.
        if (mViewModel.postsLiveData.value !is State.Success) {
            getPosts()
        }
    }
    private fun getPosts() {
        mViewModel.getPosts()
    }
}