package com.taein.thignsflowtest.github.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.entity.GithubRepo;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface GithubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGithubIssue(List<GithubIssue> githubIssue);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGithubRepo(GithubRepo githubRepo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GithubIssue githubIssue);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GithubRepo githubRepo);

    @Query("SELECT * FROM github_issue WHERE github_repo_id = :id ORDER BY number DESC")
    Flowable<List<GithubIssue>> getGithubIssues(int id);
}
