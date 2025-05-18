package com.tasklist.test

import com.tasklist.BaseTestClass
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.mock.ApiService_Mock
import com.tasklist.presentation.task_2.comments.CommentsIntent
import com.tasklist.presentation.task_2.comments.CommentsViewModel
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.delay
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf
import org.koin.test.inject
import kotlin.test.assertEquals

class CommentViewModelTest: BaseTestClass() {

    private val testPost = PostsDomainModel(
        id = 1,
        userId = 1,
        title = "Test Post",
        body = "Test Body",
        isFavorite = false
    )

    @Test
    fun testViewModel() {
        test {
            val vmCom = inject<CommentsViewModel>{ parametersOf(testPost) }.value
            assertEquals(vmCom.state.value.isLoading, true)

            delay(100)

            assertEquals(vmCom.state.value.isLoading, false)

            assertEquals(vmCom.state.value.comments.size, ApiService_Mock.initComments.size)
            assertEquals(vmCom.state.value.currentPost.isFavorite, false)

            vmCom.processIntent(CommentsIntent.IsFavorites(testPost.copy(isFavorite = true)))

            delay(100)

            assertEquals(vmCom.state.value.currentPost.isFavorite, true)
        }
    }

    @Test
    fun testInvalidDate() {
        test {
            loadKoinModules(ApiService_Mock().mModule(
                getComments = {
                    ApiService_Mock.initComments.mapIndexed() { index, item ->
                        if(index == 0) {
                            item.copy(body = null)
                        }else item
                    }
                }
            )
            )

            val vm = inject<CommentsViewModel> { parametersOf(testPost) }.value
            delay(100)
            assertEquals(vm.state.value.comments.size, ApiService_Mock.initComments.size - 1)
        }
    }

    @Test
    fun testInvalidRequest() {
        test {
            loadKoinModules(ApiService_Mock().mModule(
                getComments = { throw Exception("Error Comments") }
            )
            )

            val vm = inject<CommentsViewModel> { parametersOf(testPost) }.value
            delay(100)
            assertEquals(vm.state.value.comments.size, 0)

        }
    }

    @Test
    fun testEmptyResponse() {
        test {
            loadKoinModules(ApiService_Mock().mModule(
                getComments = { emptyList() }
            ))
            val vm = inject<CommentsViewModel> { parametersOf(testPost) }.value
            delay(100)

            assertEquals(vm.state.value.comments.size, 0)

            assertNull(vm.state.value.error)

            assertEquals(vm.state.value.isLoading, false)
        }
    }
}