package com.example.git.controller;

import com.example.git.model.Commit;
import com.example.git.model.Repository;
import com.example.git.service.GitService;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping("/api/git")
public class GitController {

    private final GitService gitService;

    public GitController(GitService gitService) {
        this.gitService = gitService;
    }

    @GetMapping("/repos")
    public Map<String, List<Map<String, Object>>> getAllCommits(@RequestParam String user, @RequestParam(defaultValue = "20") int limit, @RequestHeader("Authorization") String authHeader) throws Exception {
        // Remove "Bearer " prefix if present
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        Map<Repository, List<Commit>> repoCommits = gitService.getCommitsGroupedByRepo(user, token, limit);

        List<Map<String, Object>> repositories = new ArrayList<>();
        for (Map.Entry<Repository, List<Commit>> entry : repoCommits.entrySet()) {
            Repository repo = entry.getKey();
            List<Commit> commits = entry.getValue() != null ? entry.getValue() : Collections.emptyList();

            Map<String, Object> repoData = new LinkedHashMap<>();
            repoData.put("name", repo.name());
            repoData.put("url", repo.url());
            repoData.put("commits", commits);

            repositories.add(repoData);
        }

        Map<String, List<Map<String, Object>>> response = new LinkedHashMap<>();
        response.put("repositories", repositories);

        return response;
    }


}
