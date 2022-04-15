package anton.android.tochkatest.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import anton.android.domain_api.entities.UserEntity
import anton.android.tochkatest.R
import anton.android.tochkatest.databinding.UserLayoutBinding

class GithubUsersAdapter(context: Context) :
    PagingDataAdapter<UserEntity, GithubUsersAdapter.GithubUsersViewHolder>(GithubUsersDiffCallback) {

    class GithubUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewBinding = UserLayoutBinding.bind(itemView)

        fun bind(user: UserEntity?) {
            viewBinding.userName.text = user?.username
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
        return GithubUsersViewHolder(
            layoutInflater.inflate(
                R.layout.user_layout,
                parent,
                false
            )
        )
    }
}