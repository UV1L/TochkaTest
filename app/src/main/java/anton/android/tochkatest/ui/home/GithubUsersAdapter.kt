package anton.android.tochkatest.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import anton.android.domain_api.entities.UserEntity
import anton.android.tochkatest.databinding.UserLayoutBinding

class GithubUsersAdapter(private val context: Context) :
    PagingDataAdapter<UserEntity, GithubUsersAdapter.GithubUsersViewHolder>(GithubUsersDiffCallback) {

    inner class GithubUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val _binding: UserLayoutBinding? = DataBindingUtil.bind(itemView)
        private val binding get() = _binding!!

        fun bind(user: UserEntity?) {

            binding.user = user!!

            binding.userProfile.setOnClickListener {
                val reposIntent = Intent(Intent.ACTION_VIEW)
                reposIntent.data = Uri.parse(user.profileUrl)
                context.startActivity(reposIntent)
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

    override fun onBindViewHolder(holder: GithubUsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUsersViewHolder {

        val binding = UserLayoutBinding.inflate(layoutInflater, parent, false)
        return GithubUsersViewHolder(binding.root)
    }
}