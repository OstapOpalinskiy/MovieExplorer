package com.opalynskyi.common

import timber.log.Timber

sealed class Either<out E, out V> {
    data class Error<out E>(val error: E) : Either<E, Nothing>()

    data class Value<out V>(val value: V) : Either<Nothing, V>()
}

fun <V> value(value: V): Either<Nothing, V> = Either.Value(value)

fun <E> error(value: E): Either<E, Nothing> = Either.Error(value)

suspend fun <R> asEither(
    errorMessage: String = "",
    successMessage: String = "",
    action: suspend () -> R,
): Either<Exception, R> {
    return try {
        Timber.i(successMessage)
        Either.Value(action())
    } catch (e: Exception) {
        Timber.e("$errorMessage, Error: ${e.message}")
        Either.Error(e)
    }
}

inline infix fun <E, V, V2> Either<E, V>.map(f: (V) -> V2): Either<E, V2> =
    when (this) {
        is Either.Error -> this
        is Either.Value -> Either.Value(f(this.value))
    }

inline infix fun <E, V, V2> Either<E, List<V>>.mapList(f: (V) -> V2): Either<E, List<V2>> =
    when (this) {
        is Either.Error -> this
        is Either.Value -> Either.Value(this.value.map { f(it) })
    }

inline infix fun <E, V, V2> Either<E, V>.flatMap(f: (V) -> Either<E, V2>): Either<E, V2> =
    when (this) {
        is Either.Error -> this
        is Either.Value -> f(value)
    }

inline infix fun <E, E2, V> Either<E, V>.mapError(f: (E) -> E2): Either<E2, V> =
    when (this) {
        is Either.Error -> Either.Error(f(error))
        is Either.Value -> this
    }

inline fun <E, V, A> Either<E, V>.fold(
    onError: (E) -> A,
    onSuccess: (V) -> A,
): A =
    when (this) {
        is Either.Error -> onError(this.error)
        is Either.Value -> {
            onSuccess(this.value)
        }
    }
