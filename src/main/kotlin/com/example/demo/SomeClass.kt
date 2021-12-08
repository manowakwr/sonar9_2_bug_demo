package com.example.demo

import arrow.core.Either
import arrow.core.curried
import arrow.core.right

data class ClassA(val value: String)
data class ClassB(val value: String)
data class ConvertError(val message: String)

typealias TryConvertA = suspend (String) -> Either<ConvertError, ClassA>
typealias TryConvertB = suspend (String) -> Either<ConvertError, ClassB>

@JvmName("effectMap")
fun <P1, E, R, P> (suspend (P1) -> Either<E, R>).map(mapper: (R) -> P): suspend (P1) -> Either<E, P> =
        { p1 ->
            this(p1).map(mapper)
        }


class SomeClass {
    private fun callTryConvertConstant(tryConvertA: TryConvertA): suspend (String) -> Either<ConvertError, ClassB> {
        return ::callTryConvertParametrized.curried()(tryConvertA.map { classA -> ClassB(classA.value) })
    }

    private suspend fun callTryConvertParametrized(tryConvertB: TryConvertB /* = suspend (kotlin.String) -> kotlin.String */, s: String): Either<ConvertError, ClassB> {
        return tryConvertB(s)
    }

    suspend fun justReturnValue(): Either<ConvertError, ClassB> {
        return callTryConvertConstant { i -> ClassA(i).right() }("xxx")
    }
}

