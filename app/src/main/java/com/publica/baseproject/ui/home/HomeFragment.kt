package com.publica.baseproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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

     override lateinit var mViewModel: HomeViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        mViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = mViewBinding.root

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
}