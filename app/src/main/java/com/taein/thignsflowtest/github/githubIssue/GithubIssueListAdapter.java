package com.taein.thignsflowtest.github.githubIssue;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.taein.thignsflowtest.R;
import com.taein.thignsflowtest.databinding.GithubIssueItemBinding;
import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import java.util.ArrayList;
import java.util.List;

import static com.taein.thignsflowtest.github.githubIssue.GithubIssueListActivity.GITHUB_TAG;

public class GithubIssueListAdapter extends RecyclerView.Adapter<GithubIssueListAdapter.GithubIssueListViewHolder> {
    private List<GithubIssue> data = new ArrayList<>();

    @NonNull
    @Override
    public GithubIssueListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GithubIssueItemBinding binding = GithubIssueItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GithubIssueListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubIssueListViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(GithubIssue githubIssue) {
        if (githubIssue != null) {
            this.data.add(0, githubIssue);
            notifyItemInserted(0);
        }
    }

    public void add(MutableLiveData<GithubIssue> githubIssuesLiveData) {
        if (githubIssuesLiveData.getValue() != null) {
            this.data.add(0, githubIssuesLiveData.getValue());
            notifyItemInserted(0);
        }
    }

    void setItem(List<GithubIssue> githubIssues) {
        if (githubIssues == null) {
            return;
        }
        this.data = githubIssues;
        notifyDataSetChanged();
    }

    class GithubIssueListViewHolder extends RecyclerView.ViewHolder {

        GithubIssueItemBinding binding;

        public GithubIssueListViewHolder(GithubIssueItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(GithubIssue githubIssue) {
            binding.setGithubIssue(githubIssue);
            binding.executePendingBindings();
        }
    }
}
