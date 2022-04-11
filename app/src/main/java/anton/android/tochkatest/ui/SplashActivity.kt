package anton.android.tochkatest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import anton.android.tochkatest.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        val intent = Intent(this, MainActivity::class.java)
        if (FirebaseAuth.getInstance().currentUser != null) {
            intent.putExtra(getString(R.string.login_extra), true)
        } else {
            intent.putExtra(getString(R.string.login_extra), false)
        }
        startActivity(intent)
        finish()
    }
}