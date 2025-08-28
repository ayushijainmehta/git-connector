package com.example.git.connector;

import com.example.git.exception.GitException;
import com.example.git.model.Commit;
import com.example.git.model.Repository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GitHubConnector implements RepositoryConnector {

    private static final String BASE_URL = "https://api.github.com";
    private final String token;
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public GitHubConnector(String token) {
        this.token = token;
    }

    @Override
    public List<Repository> getRepositories(String user) {
        List<Repository> repos = new ArrayList<>();
        String url = BASE_URL + "/users/" + user + "/repos?per_page=100";
        try {
            while (url != null) {
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", "token " + token)
                        .build();
                Response response = RetryTemplate.execute(() -> client.newCall(request).execute());
                if (!response.isSuccessful()) throw new GitException("Failed to fetch repos: " + response);
                JsonNode nodes = mapper.readTree(response.body().string());
                for (JsonNode node : nodes) {
                    repos.add(new Repository(node.get("name").asText(), node.get("html_url").asText()));
                }
                url = getNextPage(response.headers().get("Link"));
            }
        } catch (Exception e) {
            throw new GitException("Error fetching repositories for user: " + user, e);
        }
        return repos;
    }

    @Override
    public List<Commit> getRecentCommits(String repo, int limit) {
        List<Commit> commits = new ArrayList<>();
        String url = BASE_URL + "/repos/" + repo + "/commits?per_page=100";
        int remaining = limit;
        try {
            while (url != null && remaining > 0) {
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", "token " + token)
                        .build();

                Response response = RetryTemplate.execute(() -> client.newCall(request).execute());

                if (!response.isSuccessful()) {
                    throw new GitException("Failed to fetch commits for repo: " + repo + " Response: "
                            + response.code() + " " + response.message());
                }

                JsonNode nodes = mapper.readTree(response.body().string());
                for (JsonNode node : nodes) {
                    if (remaining-- <= 0) break;
                    JsonNode commitNode = node.get("commit");
                    commits.add(new Commit(
                            commitNode.get("message").asText(),
                            commitNode.get("author").get("name").asText(),
                            Instant.parse(commitNode.get("author").get("date").asText())
                    ));
                }

                url = getNextPage(response.headers().get("Link"));
            }
        } catch (Exception e) {
            // Wrap original exception in GitException and propagate
            throw new GitException("Error fetching commits for repo: " + repo, e);
        }
        return commits;
    }

    private String getNextPage(String linkHeader) {
        if (linkHeader == null) return null;
        for (String link : linkHeader.split(",")) {
            if (link.contains("rel=\"next\"")) {
                return link.substring(link.indexOf("<") + 1, link.indexOf(">"));
            }
        }
        return null;
    }
}
