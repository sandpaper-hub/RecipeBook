package com.example.recipebook.data.mapper

import com.example.recipebook.domain.model.error.SearchDataError
import com.example.recipebook.domain.model.error.authentication.AuthenticationException
import com.example.recipebook.domain.model.error.dataError.DataError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.TimeoutCancellationException

fun FirebaseFirestoreException.toDataError(): SearchDataError {
    return when (code) {
        FirebaseFirestoreException.Code.UNAVAILABLE ->
            SearchDataError.NoInternet

        FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> SearchDataError.Timeout
        else -> SearchDataError.Unknown
    }
}

fun Throwable.toAuthException(): AuthenticationException =
    when (this) {
        is FirebaseAuthUserCollisionException -> AuthenticationException.EmailAlreadyInUse()
        is FirebaseAuthInvalidCredentialsException -> AuthenticationException.InvalidEmail()
        is FirebaseNetworkException -> AuthenticationException.NetworkException()
        else -> AuthenticationException.Unknown()
    }

fun Throwable.toDataError(): DataError = when (this) {
    is TimeoutCancellationException -> DataError.Unavailable()
    is FirebaseFirestoreException -> when (code) {
        FirebaseFirestoreException.Code.UNAVAILABLE -> DataError.Unavailable()
        FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> DataError.Unavailable()
        FirebaseFirestoreException.Code.ABORTED -> DataError.Unavailable()
        else -> DataError.Unknown(message)
    }
    else -> DataError.Unknown(message)
}

inline fun <T> Result<T>.mapFailure(
    transform: (Throwable) -> Throwable
): Result<T> = exceptionOrNull()
    ?.let { Result.failure(transform(it)) }
    ?: this