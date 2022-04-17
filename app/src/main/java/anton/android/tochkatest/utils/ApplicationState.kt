package anton.android.tochkatest.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object ApplicationState {

    var currentUser: FirebaseUser? = null
        get() = when (field) {
            null -> FirebaseAuth.getInstance().currentUser

            else -> field
        }
}