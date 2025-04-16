package com.tasklist.di

import com.tasklist.data.database.favorites.FavoritesDao
import com.tasklist.data.remote.ICommentsApi
import com.tasklist.data.remote.IPostApi
import com.tasklist.data.repository.AuthRepositoryImpl
import com.tasklist.data.repository.CommentsRepositoryImpl
import com.tasklist.data.repository.PostFavoritesLocalRepositoryImpl
import com.tasklist.data.repository.PostsRepositoryImpl
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.repository.IAuthRepository
import com.tasklist.domain.repository.ICommentsRepository
import com.tasklist.domain.repository.IPostFavoritesRepository
import com.tasklist.domain.repository.IPostsRepository
import com.tasklist.domain.use_case.DeleteOrInsertFavoritesUseCase
import com.tasklist.domain.use_case.GetAllFavoritesUseCase
import com.tasklist.domain.use_case.GetCommentsUseCase
import com.tasklist.domain.use_case.GetPostsUseCase
import com.tasklist.domain.use_case.ValidateUserUseCase
import com.tasklist.presentation.Task_1.Task1ViewModel
import com.tasklist.presentation.home.HomeViewModel
import com.tasklist.presentation.task_2.comments.CommentsViewModel
import com.tasklist.presentation.task_2.post_api.PostApiViewModel
import com.tasklist.presentation.task_pages.TaskPagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<IAuthRepository> { AuthRepositoryImpl() }

    single<IPostsRepository> { PostsRepositoryImpl(postApi = get<IPostApi>()) }
    single<ICommentsRepository> { CommentsRepositoryImpl(postByIdApi = get<ICommentsApi>()) }

    single<IPostFavoritesRepository> { PostFavoritesLocalRepositoryImpl(dao = get<FavoritesDao>()) }


    factory<ValidateUserUseCase> { ValidateUserUseCase(authRepository = get<IAuthRepository>()) }

    factory<GetPostsUseCase> { GetPostsUseCase(
        postRepository = get<IPostsRepository>(),
        favoritesRepository = get<IPostFavoritesRepository>()
        ) }
    factory<GetCommentsUseCase> { GetCommentsUseCase(commentsRepository = get<ICommentsRepository>()) }

    factory<GetAllFavoritesUseCase> { GetAllFavoritesUseCase(getAllFavoritesRepository = get<IPostFavoritesRepository>()) }
    factory<DeleteOrInsertFavoritesUseCase> { DeleteOrInsertFavoritesUseCase(favoritesRepository = get<IPostFavoritesRepository>()) }


    viewModel<Task1ViewModel> { Task1ViewModel(validateUserUseCase = get<ValidateUserUseCase>()) }
    viewModel<HomeViewModel> { HomeViewModel() }

    viewModel<PostApiViewModel> { PostApiViewModel(
        getPostsUseCase = get<GetPostsUseCase>(),
        getAllFavoritesUseCase = get<GetAllFavoritesUseCase>(),
        deleteOrInsertFavoritesUseCase = get<DeleteOrInsertFavoritesUseCase>()
        ) }

    viewModel<CommentsViewModel> { (post: PostsDomainModel) -> CommentsViewModel(
        getCommentsUseCase = get<GetCommentsUseCase>(),
        post = post,
        deleteOrInsertFavoritesUseCase = get<DeleteOrInsertFavoritesUseCase>()
        ) }

    viewModel<TaskPagesViewModel> { TaskPagesViewModel() }
}
