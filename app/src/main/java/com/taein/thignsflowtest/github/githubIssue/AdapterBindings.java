package com.taein.thignsflowtest.github.githubIssue;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import com.squareup.picasso.Picasso;


public abstract class AdapterBindings {

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