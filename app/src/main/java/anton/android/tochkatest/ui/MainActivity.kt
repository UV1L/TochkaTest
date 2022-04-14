package anton.android.tochkatest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import anton.android.tochkatest.R
import anton.android.tochkatest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding get() = _viewBinding!!

    @SuppressLint("InflateParams")
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
            viewBinding.activityMainNavigation.setupWithNavController(navController)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _viewBinding = null
    }

    fun openDrawer() {

        viewBinding.root.openDrawer(Gravity.LEFT)
    }

    fun showSignOutNavigation() {

        viewBinding.navigationMenuContainer.visibility = View.VISIBLE
    }
}