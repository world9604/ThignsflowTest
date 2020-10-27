package com.taein.thignsflowtest.github.githubIssue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.facebook.stetho.Stetho;
import com.taein.thignsflowtest.R;
import com.taein.thignsflowtest.databinding.ActivityGithubIssueListBinding;
import com.taein.thignsflowtest.github.utils.ErrorHandler;
import com.taein.thignsflowtest.github.utils.ViewModelFactory;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class GithubIssueListActivity extends AppCompatActivity {

    public static final String GITHUB_TAG = "GITHUB_TAG";
    private GithubIssueListViewModel viewModel;
    private GithubIssueListAdapter githubIssueListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);

        ActivityGithubIssueListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_github_issue_list);
        RxJavaPlugins.setErrorHandler(ErrorHandler.get());

        viewModel = obtainViewModel(this);
        binding.setViewModel(viewModel);
        binding.setListener(getActionListener());
        binding.setLifecycleOwner(this);

        githubIssueListAdapter = new GithubIssueListAdapter();
        binding.githubIssueItemRecyclerView.setHasFixedSize(true);
        binding.githubIssueItemRecyclerView.setAdapter(githubIssueListAdapter);
        binding.githubIssueItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpObservable();
//        startWithTest();
    }

    @SuppressLint("CheckResult")
    private void startWithTest() {
        final List<String> items = Arrays.asList("one", "two", "three", "four");
        Observable.range(0, items.size())
                .subscribeOn(Schedulers.io())
                .flatMap(str -> Observable.just(str + "!!"))
                .observeOn(AndroidSchedulers.mainThread())
                .startWith("Top Priority Item")
                .subscribe(resp -> Log.d(GITHUB_TAG, "startWithTest: " + resp));
    }


    private void setUpObservable() {
        // Adapter item 추가
        viewModel.getGithubIssueLiveData().observe(this, githubIssue -> {
            githubIssueListAdapter.add(githubIssue);
        });

        // Adapter item clear
        viewModel.getClearItemsLiveData().observe(this, canClearItem -> {
            Log.d(GITHUB_TAG, "Clear Items LiveData: " + canClearItem);
            if (canClearItem) githubIssueListAdapter.clearItems();
        });
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