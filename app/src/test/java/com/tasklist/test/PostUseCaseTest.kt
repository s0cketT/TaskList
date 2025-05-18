package com.tasklist.test

import com.tasklist.BaseTestClass
import com.tasklist.domain.use_case.GetPostsUseCase
import com.tasklist.mock.ApiService_Mock
import com.tasklist.mock.FavoritesDao_Mock
import org.junit.Test
import org.koin.test.inject
import kotlin.test.assertEquals

class PostUseCaseTest: BaseTestClass() {

    @Test
    fun testPostIsFavoritesFromUseCase() {
        test {
            val post = inject<GetPostsUseCase>().value.invoke().data!!

            assertEquals(post.size, ApiService_Mock.initPosts.size)
            assertEquals(post.filter { it.isFavorite }.size, FavoritesDao_Mock.initFavoritePost.size)
        }
    }

}