package anton.android.tochkatest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import anton.android.tochkatest.R
import anton.android.tochkatest.databinding.FragmentHomeBinding

class HomeScreenFragment : Fragment() {

    lateinit var viewBinding: FragmentHomeBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        toggle = ActionBarDrawerToggle(activity, viewBinding.root, R.string.open, R.string.close)
        viewBinding.root.addDrawerListener(toggle)
        toggle.syncState()
        return viewBinding.root
    }
}