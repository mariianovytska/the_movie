package com.mobile_app.themovie.domain.usecase

import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

object BaseUseCases {
    abstract class CompletableUseCase<in Params> : UseCase<Params, Completable>()
    abstract class FlowableUseCase<in Params, T> : UseCase<Params, Flowable<T>>()
    abstract class SingleUseCase<in Params, T> : UseCase<Params, Single<T>>()
    abstract class ObservableUseCase<in Params, T> : UseCase<Params, Observable<T>>()
    abstract class LiveDataUseCase<in Params, T> : UseCase<Params, LiveData<T>>()
}
