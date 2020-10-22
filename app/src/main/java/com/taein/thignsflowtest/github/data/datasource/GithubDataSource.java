package com.taein.thignsflowtest.github.data.datasource;

import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.entity.GithubRepo;
import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import java.util.List;

import io.reactivex.Flowable;

public interface GithubDataSource {

    Flowable<List<GithubRepoWithIssuesVO>> getGithubRepoWithIssuesFromQuery(String org, String repo);

    void insertGithubRepoWithIssues(GithubRepo githubRepo, List<GithubIssue> githubIssue);

    void insert(GithubIssue githubIssue);

    void insert(GithubRepo githubRepo);

    Flowable<GithubIssue> getGithubIssues(int repoId);
}
