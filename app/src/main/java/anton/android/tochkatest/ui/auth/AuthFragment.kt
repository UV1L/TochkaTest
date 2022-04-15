package anton.android.tochkatest.ui.auth

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import anton.android.tochkatest.databinding.FragmentAuthBinding
import anton.android.tochkatest.utils.ApplicationState
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class AuthFragment : Fragment() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { onSignInResult(it) }
    private var _viewBinding: FragmentAuthBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentAuthBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.authSignInButton.setOnClickListener {
            launchSignInIntent()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _viewBinding = null
    }

    private fun launchSignInIntent() {

        val googleProvider = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(googleProvider)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            ApplicationState.currentUser = user
            findNavController().navigate(AuthFragmentDirections.authFragmentToHomeFragment())
        } else {
            Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}