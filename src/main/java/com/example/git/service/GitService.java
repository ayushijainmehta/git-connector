package com.example.git.service;

import com.example.git.connector.GitHubConnector;
import com.example.git.connector.GitConnector;
import com.example.git.model.Commit;
import com.example.git.model.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitService {

    public Map<Repository, List<Commit>> getCommitsGroupedByRepo(String user, String token, int commitLimit) throws Exception {
        GitConnector connector = new GitHubConnector(token);
        List<Repository> repos = connector.getRepositories(user);

        Map<Repository, List<Commit>> result = new HashMap<>();
        for (Repository repo : repos) {
            List<Commit> commits = connector.getRecentCommits(user + "/" + repo.name(), commitLimit);
            result.put(repo, commits);
        }
        return result;
    }
}
