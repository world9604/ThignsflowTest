package com.taein.thignsflowtest.github.githubIssue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.taein.thignsflowtest.R;
import com.taein.thignsflowtest.databinding.ActivityGithubIssueListBinding;
import com.taein.thignsflowtest.github.utils.ErrorHandler;
import com.taein.thignsflowtest.github.utils.ViewModelFactory;

import io.reactivex.plugins.RxJavaPlugins;

public class GithubIssueListActivity extends AppCompatActivity {

    public static final String GITHUB_TAG = "GITHUB_TAG";
    private GithubIssueListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGithubIssueListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_github_issue_list);
        RxJavaPlugins.setErrorHandler(ErrorHandler.get());

        viewModel = obtainViewModel(this);
        binding.setViewModel(viewModel);
        binding.setListener(getActionListener());
        binding.setLifecycleOwner(this);

        GithubIssueListAdapter githubIssueListAdapter = new GithubIssueListAdapter();
        binding.githubIssueItemRecyclerView.setHasFixedSize(true);
        binding.githubIssueItemRecyclerView.setAdapter(githubIssueListAdapter);
        binding.githubIssueItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private GithubIssueActionListener getActionListener() {
        return () -> showAlertDialog();
    }

    public void showAlertDialog() {
        final EditText txtUrl = new EditText(this);
        txtUrl.setHint("example : dagger");

        new AlertDialog.Builder(this)
                .setTitle("Change Repository")
                .setMessage("Please input the name of the Repository.")
                .setView(txtUrl)
                .setPositiveButton("Ok", (dialog, whichButton) -> {
                    String repoName = txtUrl.getText().toString();
                    viewModel.changeRepo(repoName);
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> {
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    public static GithubIssueListViewModel obtainViewModel(FragmentActivity ac) {
        ViewModelFactory factory = ViewModelFactory.getInstance(ac.getApplication());
        return new ViewModelProvider(ac.getViewModelStore(), factory)
                .get(GithubIssueListViewModel.class);
    }
}