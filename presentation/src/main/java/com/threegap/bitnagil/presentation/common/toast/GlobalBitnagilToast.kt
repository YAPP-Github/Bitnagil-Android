package com.threegap.bitnagil.presentation.common.toast

import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilToastState
import java.lang.ref.WeakReference

object GlobalBitnagilToast {
    private var _toastStateRef: WeakReference<BitnagilToastState>? = null

    fun initialize(toastState: BitnagilToastState) {
        _toastStateRef = WeakReference(toastState)
    }

    fun show(text: String, icon: Int? = null) {
        _toastStateRef?.get()?.show(text, icon)
    }

    fun showCheck(text: String) = show(text, R.drawable.ic_check)

    fun showWarning(text: String) = show(text, R.drawable.ic_warning)
}
