package com.example.recipebook.presentation.viewModel.accountScreen.model

sealed interface AccountUiEvent {
    object GoBack: AccountUiEvent
}