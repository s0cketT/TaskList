package com.tasklist.test

import com.tasklist.BaseTestClass
import com.tasklist.mock.ApiService_Mock
import com.tasklist.mock.FavoritesDao_Mock
import com.tasklist.presentation.task_2.post_api.PostApiViewModel
import com.tasklist.presentation.task_2.post_api.model.PostIntent
import com.tasklist.presentation.task_2.post_api.model.Task2Event
import kotlinx.coroutines.delay
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.inject
import java.util.UUID
import kotlin.test.assertEquals

class PostViewModelTest: BaseTestClass() {

    @Test
    fun testViewModel() {
        test {
            val vm = inject<PostApiViewModel>().value
            val event = vm.event.parseEvent()

            delay(100)

            assertEquals(vm.state.value.originalPosts.size, ApiService_Mock.initPosts.size)
            assertEquals(vm.state.value.posts.size, ApiService_Mock.initPosts.size)
            assertEquals(
                vm.state.value.posts.filter { it.isFavorite }.size,
                FavoritesDao_Mock.initFavoritePost.size)

            val selectedPost = vm.state.value.posts.random()
            assertEquals(event.value, null)
            vm.processIntent(PostIntent.NavigateToCommentsScreen(selectedPost))
            delay(100)
            assertEquals(event.value is Task2Event.NavigateToCommentsScreen, true)
        }
    }


    @Test
    fun testInvalidData() {
        test {
            loadKoinModules(ApiService_Mock().mModule(
                getPost = {
                    ApiService_Mock.initPosts.mapIndexed { index, item ->
                        if (index == 0) {
                            item.copy(title = null)
                        } else item
                    }
                }
            ))

            val vm = inject<PostApiViewModel>().value
            delay(100)
            assertEquals(ApiService_Mock.initPosts.size - 1, vm.state.value.originalPosts.size)
        }
    }

    @Test
    fun testInvalidRequest() {
        test {
            loadKoinModules(ApiService_Mock().mModule(
                getPost = { throw Exception("exception thrown") }
            ))

            val vm = inject<PostApiViewModel>().value

            delay(100)

            assertEquals(vm.state.value.originalPosts.size, 0)
        }
    }

    @Test
    fun testSearch() {
        test {
            val textSearch = UUID.randomUUID().toString()

            val newPost = ApiService_Mock.initPosts.mapIndexed { index, item ->
                if(index == 0 || index == 1) {
                    item.copy(
                        body = "$index - $textSearch",
                        id = UUID.randomUUID().hashCode().toString()
                    )
                } else item
            }

            loadKoinModules(ApiService_Mock().mModule(
                getPost = { newPost }
            ))

            val vm = inject<PostApiViewModel>().value
            delay(100)

            vm.processIntent(PostIntent.EnterTextField(textSearch))
            assertEquals(vm.state.value.posts.size, 2)
        }
    }

}