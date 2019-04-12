package com.example.core.permission

import io.reactivex.Observable


class Permission {
    var name: String
    var granted: Boolean = false
    var shouldShowRequestPermissionRationale: Boolean = false

    constructor(name: String, granted: Boolean) : this(name, granted, false)

    constructor(name: String, granted: Boolean, shouldShowRequestPermissionRationale: Boolean) {
        this.name = name
        this.granted = granted
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale
    }

    constructor(permissions: List<Permission>) {
        name = combineName(permissions)
        granted = combineGranted(permissions)
        shouldShowRequestPermissionRationale = combineShouldShowRequestPermissionRationale(permissions)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that = other as Permission

        if (granted != that.granted) return false
        return if (shouldShowRequestPermissionRationale != that.shouldShowRequestPermissionRationale) false else name.equals(
            that.name
        )
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + if (granted) 1 else 0
        result = 31 * result + if (shouldShowRequestPermissionRationale) 1 else 0
        return result
    }
    private fun combineName(permissions: List<Permission>): String {
        return Observable.fromIterable(permissions)
            .map {
                it.name
            }.collectInto(StringBuilder()) { s, s2 ->
                if (s.isEmpty()) {
                    s.append(s2)
                } else {
                    s.append(", ").append(s2)
                }
            }.blockingGet().toString()
    }

    private fun combineGranted(permissions: List<Permission>): Boolean {
        return Observable.fromIterable(permissions)
            .all {
                it.granted
            }.blockingGet()

    }
    private fun combineShouldShowRequestPermissionRationale(permissions: List<Permission>): Boolean {
        return Observable.fromIterable(permissions)
            .any {
                it.shouldShowRequestPermissionRationale
            }.blockingGet()

    }
}