package com.taein.thignsflowtest.github.data.datasource;

import androidx.annotation.NonNull;

import com.taein.thignsflowtest.github.data.dao.GithubRepoDao;
import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.entity.GithubRepo;
import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import java.util.List;

import io.reactivex.Flowable;

public class LocalGithubDataSource implements GithubDataSource{
    private static volatile LocalGithubDataSource INSTANCE;
    private GithubRepoDao githubRepoDao;

    public LocalGithubDataSource(GithubRepoDao githubRepoDao) {
        this.githubRepoDao = githubRepoDao;
    }

    public static LocalGithubDataSource getInstance(@NonNull GithubRepoDao githubRepoDao) {
        if (INSTANCE == null) {
            synchronized (LocalGithubDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalGithubDataSource(githubRepoDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<GithubRepoWithIssuesVO>> getGithubRepoWithIssuesFromQuery(String org, String repo) {
        return null;
    }

    @Override
    public void insertGithubRepoWithIssues(GithubRepo githubRepo, List<GithubIssue> githubIssue) {
        githubRepoDao.insertGithubIssue(githubIssue);
        githubRepoDao.insertGithubRepo(githubRepo);
    }

    @Override
    public void insert(GithubIssue githubIssue) {
        githubRepoDao.insert(githubIssue);
    }

    @Override
    public void insert(GithubRepo githubRepo) {
        githubRepoDao.insert(githubRepo);
    }

    @Override
    public Flowable<List<GithubIssue>> getGithubIssues(int repoId) {
        return githubRepoDao.getGithubIssues(repoId);
    }
}
