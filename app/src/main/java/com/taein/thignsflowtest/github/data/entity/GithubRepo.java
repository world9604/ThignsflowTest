package com.taein.thignsflowtest.github.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "github_repository")
public class GithubRepo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "name")
    private String name;

    public static GithubRepo create(String repoName, GithubRepoWithIssuesVO githubRepoWithIssuesVO) {
        return new GithubRepo(githubRepoWithIssuesVO.getId(),
                githubRepoWithIssuesVO.getRepositoryUrl(), repoName);
    }
}
