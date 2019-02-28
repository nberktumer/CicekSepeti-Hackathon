package com.hackathon.lib.subscriber

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object Subscriber {
    inline fun <S> subscribe(
            event: Single<S>,
            crossinline result: (S?) -> Unit
    ): Disposable {
        return event.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    result(it)
                }, {
                    result(null)
                })
    }

    inline fun <S> subscribe(
            event: Maybe<S>,
            crossinline result: (S?) -> Unit
    ): Disposable {
        return event.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    result(it)
                }, {
                    result(null)
                }, {
                    result(null)
                })
    }

    inline fun <S> subscribe(
            event: Flowable<S>,
            crossinline result: (S?) -> Unit
    ): Disposable {
        return event.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    result(it)
                }, {
                    result(null)
                })
    }

    inline fun <S> subscribe(
            event: Single<S>,
            crossinline success: (S) -> Unit,
            crossinline error: (Throwable) -> Unit
    ): Disposable {
        return event.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    success(result)
                }, { err ->
                    error(err)
                })
    }

    inline fun <S> subscribe(
            event: Maybe<S>,
            crossinline success: (S?) -> Unit,
            crossinline error: (Throwable) -> Unit
    ): Disposable {
        return event.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    success(result)
                }, { err ->
                    error(err)
                }, {
                    success(null)
                })
    }

    inline fun <S> subscribe(
            event: Flowable<S>,
            crossinline success: (S) -> Unit,
            crossinline error: (Throwable) -> Unit
    ): Disposable {
        return event.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> success(result) }, { err -> error(err) })
    }
}