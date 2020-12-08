package com.publica.baseproject.data.repository

import androidx.annotation.MainThread
import com.publica.baseproject.data.local.dao.PostsDao
import com.publica.baseproject.data.remote.api.BaseApiService
import com.publica.baseproject.model.Post
import com.publica.baseproject.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class PostsRepository @Inject constructor(
    private val postsDao: PostsDao,
    private val baseApiService: BaseApiService,
    ) {
    /**
     * Fetched the posts from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    fun getAllPosts(): Flow<State<List<Post>>> {
        return object : NetworkBoundRepository<List<Post>, List<Post>>() {

            override suspend fun saveRemoteData(response: List<Post>) =
                postsDao.insertPosts(response)

            override fun fetchFromLocal(): Flow<List<Post>> = postsDao.getAllPosts()

            override suspend fun fetchFromRemote(): Response<List<Post>> = baseApiService.getPosts()
        }.asFlow()
    }

    /**
     * Retrieves a post with specified [postId].
     * @param postId Unique id of a [Post].
     * @return [Post] data fetched from the database.
     */
    @MainThread
    fun getPostById(postId: Int): Flow<Post> = postsDao.getPostById(postId)
}