package com.example.core

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.permission.Permission
import io.reactivex.subjects.PublishSubject
import org.koin.android.viewmodel.ext.android.viewModelByClass
import kotlin.collections.set
import kotlin.reflect.KClass

const val PERMISSIONS_REQUEST_CODE = 42

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(private val classModel: KClass<VM>) : Fragment(),
    BaseConstant, ViewContract {
    val baseViewModel: VM  by lazy {
        initViewModel().value
    }
    private var isRegistered = false
    private var mSubjects: MutableMap<String, PublishSubject<Permission>> = HashMap()
    protected lateinit var mBinding: DB

    open fun initViewModel(): Lazy<VM> {
        return viewModelByClass(classModel)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData(baseViewModel)
    }


    override fun onResume() {
        super.onResume()
        if (!isRegistered) {
            bus.register(this)
            isRegistered = !isRegistered
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (isRegistered) {
            bus.unregister(this)
            isRegistered = !isRegistered
        }
    }

    open fun initView() {

    }

    open fun initData(viewModel: VM) {

    }

    abstract fun getLayoutResourceId(): Int


    override fun switchFragment(fragment: Fragment, hasAnim: Boolean) {
        if (activity != null) {
            (activity as BaseActivity<*, *>).switchFragment(fragment, hasAnim)

        }
    }

    override fun switchFragment(fragment: Fragment, root: Int, hasAnim: Boolean) {
        if (activity != null) {
            (activity as BaseActivity<*, *>).switchFragment(fragment, root, hasAnim)

        }
    }

    override fun enableRefresh(isEnable: Boolean) {
        if (activity != null) {
            (activity as BaseActivity<*, *>).enableRefresh(isEnable)

        }
    }

    override fun showDialogLoading(isShow: Boolean) {
        if (activity != null) {
            (activity as BaseActivity<*, *>).showDialogLoading(isShow)

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissions(@NonNull permissions: Array<String>) {
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSIONS_REQUEST_CODE) return

        val shouldShowRequestPermissionRationale = BooleanArray(permissions.size)

        for (i in 0 until permissions.size) {
            shouldShowRequestPermissionRationale[i] = shouldShowRequestPermissionRationale(permissions[i])
        }

        onRequestPermissionsResult(permissions, grantResults, shouldShowRequestPermissionRationale)
    }

    fun onRequestPermissionsResult(
        permissions: Array<String>,
        grantResults: IntArray,
        shouldShowRequestPermissionRationale: BooleanArray
    ) {
        var i = 0
        val size = permissions.size
        while (i < size) {
            // Find the corresponding subject
            val subject = mSubjects[permissions[i]]
            if (subject == null) {
                // No subject found
                Log.e(
                    "BaseFragment",
                    "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request."
                )
                return
            }
            mSubjects.remove(permissions[i])
            val granted = grantResults[i] == PackageManager.PERMISSION_GRANTED
            subject.onNext(Permission(permissions[i], granted, shouldShowRequestPermissionRationale[i]))
            subject.onComplete()
            i++
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isGranted(permission: String): Boolean {
        val fragmentActivity = activity ?: throw IllegalStateException("This fragment must be attached to an activity.")
        return fragmentActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isRevoked(permission: String): Boolean {
        val fragmentActivity = activity ?: throw IllegalStateException("This fragment must be attached to an activity.")
        return fragmentActivity.packageManager.isPermissionRevokedByPolicy(permission, activity!!.packageName)
    }

    fun getSubjectByPermission(permission: String): PublishSubject<Permission>? {
        return mSubjects[permission]
    }

    fun containsByPermission(permission: String): Boolean {
        return mSubjects.containsKey(permission)
    }

    fun setSubjectForPermission(permission: String, subject: PublishSubject<Permission>) {
        mSubjects[permission] = subject
    }
}