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
import anton.android.tochkatest.databinding.UserPreviewLayoutBinding

class GithubUsersPreviewAdapter(context: Context) :
    PagingDataAdapter<UserEntity, GithubUsersPreviewAdapter.GithubUsersPreviewViewHolder>(
        GithubUsersDiffCallback
    ) {

    class GithubUsersPreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewBinding = UserPreviewLayoutBinding.bind(itemView)

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

    override fun onBindViewHolder(holder: GithubUsersPreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubUsersPreviewViewHolder {
        return GithubUsersPreviewViewHolder(
            layoutInflater.inflate(
                R.layout.user_preview_layout,
                parent,
                false
            )
        )
    }
}