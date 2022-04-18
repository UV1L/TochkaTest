package anton.android.tochkatest.ui.user

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import anton.android.domain_api.entities.RepositoryEntity
import anton.android.tochkatest.databinding.RepositoryLayoutBinding

class GithubRepositoriesAdapter(private val context: Context) :
    ListAdapter<RepositoryEntity, GithubRepositoriesAdapter.RepositoryViewHolder>(DiffCallback()) {

    inner class RepositoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val _binding: RepositoryLayoutBinding? = DataBindingUtil.bind(itemView)
        private val binding get() = _binding!!  

        fun bind(repo: RepositoryEntity?) {

            binding.repo = repo!!

            binding.repoName.setOnClickListener {
                val reposIntent = Intent(Intent.ACTION_VIEW)
                reposIntent.data = Uri.parse(repo.url)
                context.startActivity(reposIntent)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<RepositoryEntity>() {

        override fun areItemsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {

        val binding = RepositoryLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoryViewHolder(binding.root)
    }
}