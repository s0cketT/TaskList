package com.tasklist.presentation.task_2.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.use_case.DeleteOrInsertFavoritesUseCase
import com.tasklist.domain.use_case.GetCommentsUseCase
import com.tasklist.presentation.task_2.components.parseToString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CommentsViewModel(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val post: PostsDomainModel,
    private val deleteOrInsertFavoritesUseCase: DeleteOrInsertFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CommentsState(currentPost = post))
    val state = _state.asStateFlow()


    init {
        getComments(post.id)
    }

    private fun getComments(postId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = getCommentsUseCase(postId)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, comments = result.data) }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = (result.exception.parseToString()).toString()
                        )
                    }
                }
            }
        }
    }

    fun processIntent(intent: CommentsIntent) {
        when (intent) {
            is CommentsIntent.IsFavorites -> viewModelScope.launch {
                deleteOrInsertFavoritesUseCase(state.value.currentPost)

                _state.update { state ->
                    val current = state.currentPost
                    val updatedPost = current.copy(isFavorite = !current.isFavorite)

                    state.copy(currentPost = updatedPost)
                }
            }
        }
    }

}

