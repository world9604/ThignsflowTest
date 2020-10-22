package com.taein.thignsflowtest.github.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "github_issue")
public class GithubIssue {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "github_repo_id")
    private int githubRepoid;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = "body")
    private String body;

    public static GithubIssue create(GithubRepoWithIssuesVO githubRepoWithIssuesVO) {
        return new GithubIssue(githubRepoWithIssuesVO.getId(),
                githubRepoWithIssuesVO.getId(), githubRepoWithIssuesVO.getIssueUrl(),
                githubRepoWithIssuesVO.getTitle(), githubRepoWithIssuesVO.getNumber(),
                githubRepoWithIssuesVO.getBody());
    }
}
