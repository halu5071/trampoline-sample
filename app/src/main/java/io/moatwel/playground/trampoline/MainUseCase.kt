package io.moatwel.playground.trampoline

import io.reactivex.Single

class MainUseCase {

    fun getCurrentThreadName(): Single<String> = Single.fromCallable {
        Thread.sleep(1000)
        "UseCase: ${Thread.currentThread().name}"
    }
}
