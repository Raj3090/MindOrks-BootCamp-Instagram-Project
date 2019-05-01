package com.raj.sharephoto.utils.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Singleton
 class RxBus {

    private val bus = PublishSubject.create<Any>()

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObservable(): Observable<Any> {
        return bus
    }

}