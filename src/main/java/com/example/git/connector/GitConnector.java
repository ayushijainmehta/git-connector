package com.example.git.connector;

import com.example.git.model.Commit;
import com.example.git.model.Repository;

import java.util.List;

public interface GitConnector {
    List<Repository> getRepositories(String orgOrUser) throws Exception;

    List<Commit> getRecentCommits(String repoName, int limit) throws Exception;
}
