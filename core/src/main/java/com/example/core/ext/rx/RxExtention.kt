package com.example.core.ext.rx

import io.reactivex.*


fun <T> Observable<T>.fromNetwork(schedulerProvider: SchedulerProvider): Observable<T> =
    subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun <T> Observable<T>.fromNewThread(schedulerProvider: SchedulerProvider): Observable<T> =
    subscribeOn(schedulerProvider.other()).observeOn(schedulerProvider.ui())

fun <T> Observable<T>.fromComputation(schedulerProvider: SchedulerProvider): Observable<T> =
    subscribeOn(schedulerProvider.computation()).observeOn(schedulerProvider.ui())

fun <T> Flowable<T>.fromNetwork(schedulerProvider: SchedulerProvider): Flowable<T> =
    subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun <T> Flowable<T>.fromNewThread(schedulerProvider: SchedulerProvider): Flowable<T> =
    subscribeOn(schedulerProvider.other()).observeOn(schedulerProvider.ui())

fun <T> Flowable<T>.fromComputation(schedulerProvider: SchedulerProvider): Flowable<T> =
    subscribeOn(schedulerProvider.computation()).observeOn(schedulerProvider.ui())

fun <T> Single<T>.fromNetwork(schedulerProvider: SchedulerProvider): Single<T> =
    subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun <T> Single<T>.fromNewThread(schedulerProvider: SchedulerProvider): Single<T> =
    subscribeOn(schedulerProvider.other()).observeOn(schedulerProvider.ui())

fun <T> Single<T>.fromComputation(schedulerProvider: SchedulerProvider): Single<T> =
    subscribeOn(schedulerProvider.computation()).observeOn(schedulerProvider.ui())

fun <T> Maybe<T>.fromNetwork(schedulerProvider: SchedulerProvider): Maybe<T> =
    subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun <T> Maybe<T>.fromNewThread(schedulerProvider: SchedulerProvider): Maybe<T> =
    subscribeOn(schedulerProvider.other()).observeOn(schedulerProvider.ui())

fun <T> Maybe<T>.fromComputation(schedulerProvider: SchedulerProvider): Maybe<T> =
    subscribeOn(schedulerProvider.computation()).observeOn(schedulerProvider.ui())

fun Completable.fromNetwork(schedulerProvider: SchedulerProvider): Completable =
    subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun Completable.fromNewThread(schedulerProvider: SchedulerProvider): Completable =
    subscribeOn(schedulerProvider.other()).observeOn(schedulerProvider.ui())

fun Completable.fromComputation(schedulerProvider: SchedulerProvider): Completable =
    subscribeOn(schedulerProvider.computation()).observeOn(schedulerProvider.ui())