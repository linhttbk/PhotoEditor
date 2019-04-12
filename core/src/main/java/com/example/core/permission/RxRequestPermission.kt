package com.example.core.permission

import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.core.BaseActivity
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject


class RxRequestPermission(val baseActivity: BaseActivity<*, *>) {
    val TAG = "RxRequestPermission"
    val TRIGGER = Any()


    fun <T> ensure(permissions: List<Permission>): ObservableTransformer<T, Boolean> {
        return ObservableTransformer<T, Boolean> {
            request(it, permissions)
                .buffer(permissions.size)
                .flatMap { results ->
                    if (permissions.isEmpty()) {
                        // Occurs during orientation change, when the subject receives onComplete.
                        // In that case we don't want to propagate that empty list to the
                        // subscriber, only the onComplete.
                        Observable.empty<Any>()
                    }
                    // Return true if all permissions are granted.
                    for (p in results) {
                        if (!p.granted) {
                            return@flatMap Observable.just(false)
                        }
                    }
                    return@flatMap Observable.just(true)
                }
        }
    }

    fun request(permissions: List<Permission>): Observable<Boolean> {
        return Observable.just(TRIGGER).compose(ensure(permissions))
    }

    private fun requestPermission(trigger: Observable<*>, permissions: List<Permission>): Observable<Permission> {
        if (permissions.isEmpty()) {
            throw IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission")
        }
        return oneOf(trigger, pending(permissions))
            .flatMap {

                requestImplementation(permissions)

            }
    }

    fun <T> ensureEach(permissions: List<Permission>): ObservableTransformer<T, Permission> {
        return ObservableTransformer { o -> requestPermission(o, permissions) }
    }

    /**
     * Map emitted items from the source observable into one combined [Permission] object. Only if all permissions are granted,
     * permission also will be granted. If any permission has `shouldShowRationale` checked, than result also has it checked.
     *
     *
     * If one or several permissions have never been requested, invoke the related framework method
     * to ask the user if he allows the permissions.
     */
    fun <T> ensureEachCombined(permissions: List<Permission>): ObservableTransformer<T, Permission> {
        return ObservableTransformer { o ->
            request(o, permissions)
                .buffer(permissions.size)
                .flatMap {results ->

                    if (results.isEmpty()) {
                        return@flatMap Observable.empty<Permission>()
                    } else {
                        return@flatMap Observable.just(Permission(results))
                    }

                }
        }
    }


    /**
     * Request permissions immediately, **must be invoked during initialization phase
     * of your application**.
     */
    fun requestEach(permissions: List<Permission>): Observable<Permission> {
        return Observable.just(TRIGGER).compose(ensureEach(permissions))
    }

    /**
     * Request permissions immediately, **must be invoked during initialization phase
     * of your application**.
     */
    fun requestEachCombined(permissions: List<Permission>): Observable<Permission> {
        return Observable.just(TRIGGER).compose(ensureEachCombined(permissions))
    }

    private fun request(trigger: Observable<*>, permissions: List<Permission>): Observable<Permission> {
        if (permissions.isEmpty()) {
            throw IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission")
        }
        return oneOf(trigger, pending(permissions))
            .flatMap {

                requestImplementation(permissions)

            }
    }

    private fun pending(permissions: List<Permission>): Observable<*> {
        for (p in permissions) {
            if (!baseActivity.containsByPermission(p.name)) {
                return Observable.empty<Any>()
            }
        }
        return Observable.just(TRIGGER)
    }


    private fun oneOf(trigger: Observable<*>?, pending: Observable<*>): Observable<*> {
        return if (trigger == null) {
            Observable.just(TRIGGER)
        } else Observable.merge(trigger, pending)
    }


    private fun requestImplementation(permissions: List<Permission>): Observable<Permission> {
        val list = ArrayList<Observable<Permission>>(permissions.size)
        val unrequestedPermissions = ArrayList<Permission>()

        // In case of multiple permissions, we create an Observable for each of them.
        // At the end, the observables are combined to have a unique response.
        permissions.forEach{
            Log.e("requestImplementation",it.name + " " + it.granted)
        }

        for (permission in permissions) {
            if (isGranted(permission)) {
                // Already granted, or not Android M
                // Return a granted Permission object.
                Log.e(" requestImplementation2",permission.name + " " + permission.granted)
                permission.granted = true
                permission.shouldShowRequestPermissionRationale = false
                list.add(Observable.just(permission))
                continue
            }

            if (isRevoked(permission)) {
                Log.e(" requestImplementation3",permission.name + " " + permission.granted)
                // Revoked by a policy, return a denied Permission object.
                permission.granted = false
                permission.shouldShowRequestPermissionRationale = false
                list.add(Observable.just(permission))
                continue
            }
            Log.e("Granted", permission.granted.toString())
            var subject = baseActivity.getSubjectByPermission(permission.name)
            // Create a new subject if not exists
            if (subject == null) {
                unrequestedPermissions.add(permission)
                subject = PublishSubject.create<Permission>()
                baseActivity.setSubjectForPermission(permission.name, subject)
            }

            if (subject != null)
                list.add(subject)
        }

        if (!unrequestedPermissions.isEmpty()) {
            val unrequestedPermissionsArray = unrequestedPermissions.toTypedArray()
            val unRequestPermission: ArrayList<String> = ArrayList()
            unrequestedPermissionsArray.forEach {
                unRequestPermission.add(it.name)
            }
            requestPermissionsFromFragment(unRequestPermission.toTypedArray())
        }
        return Observable.concat(Observable.fromIterable<Observable<Permission>>(list))
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsFromFragment(permissions: Array<String>) {
        baseActivity.requestPermissions(permissions)
    }


    /**
     * Returns true if the permission is already granted.
     *
     *
     * Always true if SDK &lt; 23.
     */
    private fun isGranted(permission: Permission): Boolean {
        return !isMarshmallow() || baseActivity.isGranted(permission.name)
    }

    /**
     * Returns true if the permission has been revoked by a policy.
     *
     *
     * Always false if SDK &lt; 23.
     */
    private fun isRevoked(permission: Permission): Boolean {
        return isMarshmallow() && baseActivity.isRevoked(permission.name)
    }

    private fun isMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray) {
        baseActivity
            .onRequestPermissionsResult(permissions, grantResults, BooleanArray(permissions.size))
    }

}