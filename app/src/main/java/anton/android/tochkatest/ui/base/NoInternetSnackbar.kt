package anton.android.tochkatest.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anton.android.tochkatest.R
import com.google.android.material.snackbar.BaseTransientBottomBar

class NoInternetSnackbar private constructor(
    parent: ViewGroup,
    content: View,
    callback: com.google.android.material.snackbar.ContentViewCallback
) : BaseTransientBottomBar<NoInternetSnackbar>(parent, content, callback) {

    companion object {

        fun build(parent: ViewGroup, duration: Int): NoInternetSnackbar {

            val content = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.no_internet_snackbar, parent, false)

            val viewCallback = EmptyContentViewCallback()

            return NoInternetSnackbar(parent, content, viewCallback).run {
                getView().setPadding(0, 0, 0, 0)
                this.duration = duration
                this
            }
        }
    }

    private class EmptyContentViewCallback :
        com.google.android.material.snackbar.ContentViewCallback {

        override fun animateContentIn(delay: Int, duration: Int) {}
        override fun animateContentOut(delay: Int, duration: Int) {}
    }
}