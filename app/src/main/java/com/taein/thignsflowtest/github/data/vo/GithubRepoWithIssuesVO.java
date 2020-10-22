package com.taein.thignsflowtest.github.data.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GithubRepoWithIssuesVO implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("repository_url")
    private String repositoryUrl;

    @SerializedName("url")
    private String issueUrl;

    @SerializedName("number")
    private int number;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("user")
    private UserVO user;
}

