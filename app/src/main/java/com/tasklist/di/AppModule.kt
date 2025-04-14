package com.tasklist.di

import com.tasklist.data.database.favorites.AutoMigrationDao
import com.tasklist.data.database.favorites.FavoritesDao
import com.tasklist.data.remote.ICommentsApi
import com.tasklist.data.remote.IPostApi
import com.tasklist.data.repository.AuthRepositoryImpl
import com.tasklist.data.repository.AutoMigrationLocalRepositoryImpl
import com.tasklist.data.repository.CommentsRepositoryImpl
import com.tasklist.data.repository.PostFavoritesLocalRepositoryImpl
import com.tasklist.data.repository.PostsRepositoryImpl
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.repository.IAuthRepository
import com.tasklist.domain.repository.IAutoMigrationRepository
import com.tasklist.domain.repository.ICommentsRepository
import com.tasklist.domain.repository.IPostFavoritesRepository
import com.tasklist.domain.repository.IPostsRepository
import com.tasklist.domain.use_case.DeleteItemAutoMigrationUseCase
import com.tasklist.domain.use_case.DeleteOrInsertFavoritesUseCase
import com.tasklist.domain.use_case.GetAllAutoMigrationUseCase
import com.tasklist.domain.use_case.GetAllFavoritesUseCase
import com.tasklist.domain.use_case.GetCommentsUseCase
import com.tasklist.domain.use_case.GetPostsUseCase
import com.tasklist.domain.use_case.InsertItemAutoMigrationUseCase
import com.tasklist.domain.use_case.ValidateUserUseCase
import com.tasklist.presentation.Task_1.Task1ViewModel
import com.tasklist.presentation.auto_migration_room.AutoMigrationViewModel
import com.tasklist.presentation.home.HomeViewModel
import com.tasklist.presentation.task_2.comments.CommentsViewModel
import com.tasklist.presentation.task_2.post_api.PostApiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<IAuthRepository> { AuthRepositoryImpl() }
    factory<ValidateUserUseCase> { ValidateUserUseCase(authRepository = get<IAuthRepository>()) }

    viewModel<Task1ViewModel> { Task1ViewModel(validateUserUseCase = get<ValidateUserUseCase>()) }
    viewModel<HomeViewModel> { HomeViewModel() }

    single<IPostFavoritesRepository> { PostFavoritesLocalRepositoryImpl(dao = get<FavoritesDao>()) }
    single<IPostsRepository> { PostsRepositoryImpl(postApi = get<IPostApi>()) }
    factory<GetAllFavoritesUseCase> { GetAllFavoritesUseCase(getAllFavoritesRepository = get<IPostFavoritesRepository>()) }
    factory<DeleteOrInsertFavoritesUseCase> { DeleteOrInsertFavoritesUseCase(favoritesRepository = get<IPostFavoritesRepository>()) }
    factory<GetPostsUseCase> {
        GetPostsUseCase(
            postRepository = get<IPostsRepository>(),
            favoritesRepository = get<IPostFavoritesRepository>()
        )
    }
    viewModel<PostApiViewModel> {
        PostApiViewModel(
            getPostsUseCase = get<GetPostsUseCase>(),
            getAllFavoritesUseCase = get<GetAllFavoritesUseCase>(),
            deleteOrInsertFavoritesUseCase = get<DeleteOrInsertFavoritesUseCase>()
        )
    }


    single<ICommentsRepository> { CommentsRepositoryImpl(postByIdApi = get<ICommentsApi>()) }
    factory<GetCommentsUseCase> { GetCommentsUseCase(commentsRepository = get<ICommentsRepository>()) }
    viewModel<CommentsViewModel> { (post: PostsDomainModel) ->
        CommentsViewModel(
            getCommentsUseCase = get<GetCommentsUseCase>(),
            post = post,
            deleteOrInsertFavoritesUseCase = get<DeleteOrInsertFavoritesUseCase>()
        )
    }


    single<IAutoMigrationRepository> { AutoMigrationLocalRepositoryImpl(dao = get<AutoMigrationDao>()) }
    factory<GetAllAutoMigrationUseCase> { GetAllAutoMigrationUseCase(autoMigrationRepository = get<IAutoMigrationRepository>()) }
    factory<InsertItemAutoMigrationUseCase> { InsertItemAutoMigrationUseCase(autoMigrationRepository = get<IAutoMigrationRepository>()) }
    factory<DeleteItemAutoMigrationUseCase> { DeleteItemAutoMigrationUseCase(autoMigrationRepository = get<IAutoMigrationRepository>()) }
    viewModel<AutoMigrationViewModel> {
        AutoMigrationViewModel(
            getAutoMigrationState = get<GetAllAutoMigrationUseCase>(),
            insertItemAutoMigrationUseCase = get<InsertItemAutoMigrationUseCase>(),
            deleteItemAutoMigrationUseCase = get<DeleteItemAutoMigrationUseCase>()
        )
    }
}
