package com.taein.thignsflowtest.github.githubIssue;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import java.util.List;

public class AdapterBindings {

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, GithubIssue githubIssue) {
        GithubIssueListAdapter adapter = (GithubIssueListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.add(githubIssue);
        }
    }

    @BindingAdapter("bind:githubRepo")
    public static void bindGithubRepo(TextView textView, String repoName) {
        final String title = String.format("google / %s", repoName);
        textView.setText(title);
    }

    @BindingAdapter("bind:issueNumber")
    public static void bindIssueNumber(TextView textView, int issueNumber) {
        final String title = String.format("#%d: ", issueNumber);
        textView.setText(title);
    }

    @BindingAdapter("bind:imageUrl")
    public static void bindImageUrl(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageDrawable(null);
        } else {
            Picasso.get().load(url).into(imageView);
        }
    }
}