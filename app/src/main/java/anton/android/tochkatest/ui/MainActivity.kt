package anton.android.tochkatest.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.savedstate.SavedStateRegistryOwner
import anton.android.tochkatest.R
import anton.android.tochkatest.databinding.ActivityMainBinding
import anton.android.tochkatest.ui.base.NoInternetSnackbar
import anton.android.tochkatest.utils.ConnectionStateMonitor
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
    SavedStateRegistryOwner,
    ConnectionStateMonitor.OnNetworkAvailableCallbacks {

    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding get() = _viewBinding!!

    private var snackbar: NoInternetSnackbar? = null
    private var connectionStateMonitor: ConnectionStateMonitor? = null
    private var viewGroup: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(viewBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph_main)
        val keyExtra = getString(R.string.login_extra)
        if (intent.hasExtra(keyExtra)) {
            when (intent.getBooleanExtra(keyExtra, false)) {

                true -> graph.setStartDestination(R.id.home_fragment)

                false -> graph.setStartDestination(R.id.auth_fragment)
            }
            val navController = navHostFragment.navController
            navController.setGraph(graph, intent.extras)
        }
    }

    override fun onResume() {
        super.onResume()

        if (viewGroup == null)
            viewGroup = findViewById(android.R.id.content)

        if (snackbar == null)
            snackbar = NoInternetSnackbar
                .build(
                    viewGroup!!,
                    Snackbar.LENGTH_INDEFINITE
                )

        if (connectionStateMonitor == null)
            connectionStateMonitor = ConnectionStateMonitor(this, this)

        connectionStateMonitor?.enable()

        if (connectionStateMonitor?.hasNetworkConnection() == false) onNegative()
        else onPositive()
    }

    override fun onPause() {

        snackbar?.dismiss()
        snackbar = null
        connectionStateMonitor?.disable()
        connectionStateMonitor = null
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        _viewBinding = null
    }

    override fun onPositive() {

        lifecycleScope.launch(Dispatchers.Main) {
            snackbar?.dismiss()
        }
    }

    override fun onNegative() {

        lifecycleScope.launch(Dispatchers.Main) {
            snackbar?.show()
        }
    }
}