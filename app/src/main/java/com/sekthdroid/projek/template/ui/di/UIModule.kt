package com.sekthdroid.projek.template.ui.di

import com.sekthdroid.projek.template.ui.screens.character.CharacterDetailViewModel
import com.sekthdroid.projek.template.ui.screens.characters.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        CharactersViewModel(get())
    }

    viewModel {
        CharacterDetailViewModel(get(), get())
    }
}