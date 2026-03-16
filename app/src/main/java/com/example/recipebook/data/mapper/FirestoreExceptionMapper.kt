package com.example.recipebook.data.mapper

import com.example.recipebook.domain.model.DataError
import com.example.recipebook.domain.model.authentication.AuthenticationException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestoreException

fun FirebaseFirestoreException.toDataError(): DataError {
    return when (code) {
        FirebaseFirestoreException.Code.UNAVAILABLE ->
            DataError.NoInternet

        FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> DataError.Timeout
        else -> DataError.Unknown
    }
}

fun Throwable.toAuthException(): AuthenticationException =
    when (this) {
        is FirebaseAuthUserCollisionException -> AuthenticationException.EmailAlreadyInUse()
        is FirebaseAuthInvalidCredentialsException -> AuthenticationException.InvalidEmail()
        is FirebaseNetworkException -> AuthenticationException.NetworkException()
        else -> AuthenticationException.Unknown()
    }

inline fun <T> Result<T>.mapFailure(
    transform: (Throwable) -> Throwable
): Result<T> = exceptionOrNull()
    ?.let { Result.failure(transform(it)) }
    ?: this