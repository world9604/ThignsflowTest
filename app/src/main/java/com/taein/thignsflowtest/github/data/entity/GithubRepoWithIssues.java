package com.taein.thignsflowtest.github.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubRepoWithIssues {

    @Embedded
    private GithubRepo githubRepo;

    @Relation(
            parentColumn = "id",
            entityColumn = "github_repo_id"
    )

    private List<GithubIssue> githubIssues;
}
