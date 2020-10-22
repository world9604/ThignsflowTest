package com.taein.thignsflowtest.github.utils;

import android.util.Log;

import static com.taein.thignsflowtest.github.githubIssue.GithubIssueListActivity.GITHUB_TAG;

import io.reactivex.functions.Consumer;


public class ErrorHandler implements Consumer<Throwable> {

    private static final ErrorHandler INSTANCE = new ErrorHandler();

    public static ErrorHandler get() {
        return INSTANCE;
    }

    private ErrorHandler() {}

    @Override
    public void accept(Throwable throwable) {
        Log.e(GITHUB_TAG, "Error on " + Thread.currentThread().getName() + ":", throwable);
    }
}
