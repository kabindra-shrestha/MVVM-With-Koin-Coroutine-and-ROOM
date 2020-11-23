package com.kabindra.sample.di

import com.kabindra.sample.viewmodel.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build UserViewModel
    viewModel {
        UserViewModel(repository = get())
    }

}