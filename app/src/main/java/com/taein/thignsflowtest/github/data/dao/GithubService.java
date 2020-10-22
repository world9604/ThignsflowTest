package com.taein.thignsflowtest.github.data.dao;

import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {

    @GET("repos/{org}/{repo}/issues")
    Flowable<List<GithubRepoWithIssuesVO>> getGithubRepoWithIssuesFromQuery(
            @Path("org") String org,
            @Path("repo") String repo
    );
}
