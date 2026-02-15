package com.example.recipebook.presentation.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph.CollectionDetailDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val collectionId =
        checkNotNull(savedStateHandle[CollectionDetailDestination.COLLECTION_ID_ARG])
}