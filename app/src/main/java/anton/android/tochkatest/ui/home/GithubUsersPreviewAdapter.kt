package anton.android.tochkatest.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import anton.android.domain_api.entities.UserEntity
import anton.android.tochkatest.databinding.UserPreviewLayoutBinding

class GithubUsersPreviewAdapter(context: Context) :
    PagingDataAdapter<UserEntity, GithubUsersPreviewAdapter.GithubUsersPreviewViewHolder>(
        GithubUsersDiffCallback
    ) {

    class GithubUsersPreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val _binding: UserPreviewLayoutBinding? = DataBindingUtil.bind(itemView)
        private val binding get() = _binding!!
        private val navController get() = Navigation.findNavController(itemView)

        fun bind(user: UserEntity?) {

            binding.user = user!!

            val userIdString = user.userId.toString()
            ViewCompat.setTransitionName(binding.userAvatar, "avatar_$userIdString")
            ViewCompat.setTransitionName(binding.userName, "name_$userIdString")
            binding.root.setOnClickListener {
                val direction = HomeScreenFragmentDirections.actionHomeFragmentToUserFragment(
                    userIdString,
                    user
                )
                val extras = FragmentNavigatorExtras(
                    binding.userAvatar to "avatar_$userIdString",
                    binding.userName to "name_$userIdString"
                )
                navController.navigate(direction, extras)
            }
        }
    }

    private object GithubUsersDiffCallback : DiffUtil.ItemCallback<UserEntity>() {

        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.username == newItem.username
        }
    }

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: GithubUsersPreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubUsersPreviewViewHolder {

        val binding = UserPreviewLayoutBinding.inflate(layoutInflater, parent, false)
        return GithubUsersPreviewViewHolder(binding.root)
    }
}