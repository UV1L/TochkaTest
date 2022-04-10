package anton.android.tochkatest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import anton.android.tochkatest.R

class MainActivity : AppCompatActivity() {
    
    private val keyExtra = getString(R.string.login_extra)

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null, false)
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph_main)
        if (intent.hasExtra(keyExtra)) {
            when (intent.getBooleanExtra(keyExtra, false)) {

                true -> graph.setStartDestination(R.id.home_fragment)

                false -> graph.setStartDestination(R.id.auth_fragment)
            }
            val navController = navHostFragment.navController
            navController.setGraph(graph, intent.extras)
        }
    }
}