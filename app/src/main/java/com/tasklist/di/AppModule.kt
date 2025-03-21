package com.tasklist.di

import com.tasklist.data.repository.AuthRepositoryImpl
import com.tasklist.domain.repository.IAuthRepository
import com.tasklist.domain.use_case.ValidateUserUseCase
import com.tasklist.presentation.Home.HomeViewModel
import com.tasklist.presentation.Task_1.Task1ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<IAuthRepository> { AuthRepositoryImpl() }

    factory<ValidateUserUseCase> { ValidateUserUseCase(authRepository = get<IAuthRepository>()) }

    viewModel<Task1ViewModel> { Task1ViewModel(validateUserUseCase = get<ValidateUserUseCase>()) }
    viewModel<HomeViewModel> { HomeViewModel() }
}
