package com.tasklist.presentation.task_2.post_api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.ExceptionDomainModel
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.use_case.DeleteOrInsertFavoritesUseCase
import com.tasklist.domain.use_case.GetAllFavoritesUseCase
import com.tasklist.domain.use_case.GetPostsUseCase
import com.tasklist.presentation.Navigation.SingleFlowEvent
import com.tasklist.presentation.task_2.components.parseToString
import com.tasklist.presentation.task_2.post_api.model.PostIntent
import com.tasklist.presentation.task_2.post_api.model.PostState
import com.tasklist.presentation.task_2.post_api.model.Task2Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostApiViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val deleteOrInsertFavoritesUseCase: DeleteOrInsertFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<Task2Event>(viewModelScope)
    val event = _event.flow

    init {
        getPosts()
        getAllFavorites()
    }

    private fun getPosts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result: Resource<List<PostsDomainModel>, ExceptionDomainModel> =
                getPostsUseCase()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            originalPosts = result.data,
                            error = null
                        )
                    }

                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.exception.parseToString())
                    }
                }
            }
        }
    }

    fun processIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.EnterTextField -> {
                _state.update {
                    it.copy(
                        textField = intent.textField
                    )
                }
            }

            is PostIntent.NavigateToCommentsScreen -> viewModelScope.launch {
                _event.emit(Task2Event.NavigateToCommentsScreen(intent.post))
            }

            is PostIntent.IsFavorites -> viewModelScope.launch {
                deleteOrInsertFavoritesUseCase(intent.post)
            }
        }
    }


    private fun getAllFavorites() {
        viewModelScope.launch {
            getAllFavoritesUseCase().collect { favorites ->
                _state.update { state ->
                    val updatedPosts = state.originalPosts.map { post ->
                        post.copy(isFavorite = favorites.any { it.id == post.id })
                    }

                    state.copy(
                        originalPosts = updatedPosts
                    )
                }
            }
        }
    }

}

