package anton.android.tochkatest.utils

import anton.android.tochkatest.view_model.base.BaseViewModel

fun interface BaseViewModelProvider {

    fun provideViewModel(): BaseViewModel
}