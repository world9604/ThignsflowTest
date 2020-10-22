package com.taein.thignsflowtest.github.data.repository;

import android.app.Application;

import com.taein.thignsflowtest.github.data.dao.GithubRepoDao;
import com.taein.thignsflowtest.github.data.dao.GithubService;
import com.taein.thignsflowtest.github.data.datasource.GithubDataSource;
import com.taein.thignsflowtest.github.data.datasource.LocalGithubDataSource;
import com.taein.thignsflowtest.github.data.datasource.RemoteGithubDataSource;
import com.taein.thignsflowtest.github.data.datasource.RetrofitGithubServiceFactory;
import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.entity.GithubRepo;
import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class GithubRepository {
    private GithubDataSource localDataSource;
    private GithubDataSource remoteDataSource;
    private static volatile GithubRepository INSTANCE;

    public GithubRepository(Application app) {
        GithubDatabase db = GithubDatabase.getInstance(app);

        // Local Data Source
        GithubRepoDao githubRepoDao = db.ParcelInfoDao();
        localDataSource = LocalGithubDataSource.getInstance(githubRepoDao);

        // Remote Data Source
        GithubService sweetTrackerService = new RetrofitGithubServiceFactory().create();
        remoteDataSource = RemoteGithubDataSource.getInstance(sweetTrackerService);
    }

    // 싱글톤 객체 반환 메소드
    public static GithubRepository getInstance(Application app) {
        if (INSTANCE == null) {
            synchronized (GithubRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GithubRepository(app);
                }
            }
        }
        return INSTANCE;
    }

    public Flowable<List<GithubRepoWithIssuesVO>> getGithubRepoWithIssuesFromQuery(String org, String repo) {
        return remoteDataSource.getGithubRepoWithIssuesFromQuery(org, repo);
    }

    public void insertGithubRepoWithIssues(GithubRepo githubRepo, List<GithubIssue> githubIssues) {
        localDataSource.insertGithubRepoWithIssues(githubRepo, githubIssues);
    }

    public void insertGithubRepo(GithubRepo githubRepo) {
        localDataSource.insert(githubRepo);
    }

    public void insertGithubIssue(GithubIssue githubIssue) {
        localDataSource.insert(githubIssue);
    }

    public Flowable<GithubIssue> getGithubIssues(int repoId) {
        return localDataSource.getGithubIssues(repoId);
    }
}
