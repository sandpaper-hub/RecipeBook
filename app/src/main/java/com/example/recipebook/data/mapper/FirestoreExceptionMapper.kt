package com.example.recipebook.data.mapper

import com.example.recipebook.domain.model.DataError
import com.google.firebase.firestore.FirebaseFirestoreException

fun FirebaseFirestoreException.toDataError(): DataError {
    return when(code) {
        FirebaseFirestoreException.Code.UNAVAILABLE ->
            DataError.NoInternet

        FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> DataError.Timeout
        else -> DataError.Unknown
    }
}