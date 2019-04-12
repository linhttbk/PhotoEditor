package com.example.core

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.core.permission.Permission
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_base.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass


abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(private val classModel: KClass<VM>) :
    AppCompatActivity(), BaseConstant, ViewContract {
    var fragmentHelper: FragmentHelper? = null
    lateinit var binding: DB

    val PERMISSIONS_REQUEST_CODE = 42

    private val baseViewModel: VM by viewModelByClass(classModel)

    private var mSubjects: MutableMap<String, PublishSubject<Permission>> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binBase: com.example.core.databinding.ActivityBaseBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_base)
        if (getLayoutResourceId() != 0) {
            val viewGroup = binBase.root
            binding = DataBindingUtil.inflate(layoutInflater, getLayoutResourceId(), viewGroup, true)
        }
        bus.register(this)
        fragmentHelper = FragmentHelper(supportFragmentManager, getRoot())
        initView()
        initData(baseViewModel)
    }

    abstract fun getRoot(): Int
    override fun onDestroy() {
        super.onDestroy()
        bus.unregister(this)
    }

    abstract fun getLayoutResourceId(): Int;

    fun getViewModel(): VM = baseViewModel

    open fun initView() {
        swrBase.isRefreshing = false
    }

    open fun initData(viewModel: VM) {

    }

    override fun switchFragment(fragment: Fragment, hasAnim: Boolean) {
        fragmentHelper!!.switchFragment(fragment, hasAnim)
    }

    override fun showDialogLoading(isShow: Boolean) {
        if (isShow) layoutDisableTouch.visibility = View.VISIBLE else layoutDisableTouch.visibility = View.GONE
    }

    override fun enableRefresh(isEnable: Boolean) {
        swrBase.isEnabled = isEnable
        swrBase.isRefreshing = isEnable
    }
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissions(@NonNull permissions: Array<String>) {
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
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
            val granted = (grantResults[i] == PackageManager.PERMISSION_GRANTED)
            Log.e(
                "RequestResult",
                granted.toString()
            )
            subject.onNext(Permission(permissions[i], granted, shouldShowRequestPermissionRationale[i]))
            subject.onComplete()
            i++
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isGranted(permission: String): Boolean {

        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isRevoked(permission: String): Boolean {
        return packageManager.isPermissionRevokedByPolicy(permission,packageName)
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