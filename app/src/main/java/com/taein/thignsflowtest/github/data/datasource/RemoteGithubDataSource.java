package com.taein.thignsflowtest.github.data.datasource;

import androidx.annotation.NonNull;

import com.taein.thignsflowtest.github.data.dao.GithubService;
import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.entity.GithubRepo;
import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import java.util.List;

import io.reactivex.Flowable;

public class RemoteGithubDataSource implements GithubDataSource{
    private static volatile RemoteGithubDataSource INSTANCE;
    private GithubService githubService;

    private RemoteGithubDataSource(GithubService githubService){
        this.githubService = githubService;
    }

    public static RemoteGithubDataSource getInstance(@NonNull GithubService githubService) {
        if (INSTANCE == null) {
            synchronized (RemoteGithubDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteGithubDataSource(githubService);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<GithubRepoWithIssuesVO>> getGithubRepoWithIssuesFromQuery(String org, String repo) {
        return githubService.getGithubRepoWithIssuesFromQuery(org, repo);
    }

    @Override
    public void insertGithubRepoWithIssues(GithubRepo githubRepo, List<GithubIssue> githubIssue) {}

    @Override
    public void insert(GithubIssue githubIssue) {}

    @Override
    public void insert(GithubRepo githubRepo) {}

    @Override
    public Flowable<GithubIssue> getGithubIssues(int repoId) {
        return null;
    }
}
