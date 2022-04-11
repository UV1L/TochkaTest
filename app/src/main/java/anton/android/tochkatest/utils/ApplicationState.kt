package anton.android.tochkatest.utils

import com.google.firebase.auth.FirebaseUser

object ApplicationState {

    var currentUser: FirebaseUser? = null

    lateinit var username: String
}