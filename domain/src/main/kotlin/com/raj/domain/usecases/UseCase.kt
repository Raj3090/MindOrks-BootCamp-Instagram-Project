package com.raj.domain.usecases

import com.raj.domain.common.Transformer
import io.reactivex.Observable

/**
 * Created by raj kumar on 11/11/2017.
 */
abstract class UseCase<T>(private val transformer: Transformer<T>) {

    abstract fun createObservable(data: Map<String, Any>? = null): Observable<T>

    fun observable(withData: Map<String, Any>? = null): Observable<T> {
        return createObservable(withData).compose(transformer)
    }



}