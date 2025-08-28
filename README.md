# GitHub Repository Activity Connector

A Spring Boot application that fetches repositories and recent commits for a GitHub user or organization using a **Personal Access Token (PAT)**. Supports **public repositories**, pagination, configurable commit limits.

---

## Features

- Fetch **public repositories** (token with proper scopes).  
- Retrieve **recent commits per repository** (configurable limit, default 20).  
- Handles **GitHub API pagination** for repositories and commits.  
- Returns **structured JSON** grouped by repository.  
- REST API endpoint for easy integration.  
- Extensible for other version control systems like GitLab or Bitbucket.  

---

## REST API

###  GET `/api/git/repos`

Fetches public repositories for a GitHub user or organization, along with recent commits.

###  Authorization: Bearer <GITHUB_PAT>



**Query Parameters:**  
| Parameter | Type    | Required | Description                                |
|-----------|---------|----------|--------------------------------------------|
| user      | string  | Yes      | GitHub username or organization            |
| limit     | integer | No       | Number of commits per repository (default: 20) |

**Request Example:**  
```bash
curl -H "Authorization: Bearer ghp_yourTokenHere" \
"http://localhost:8080/api/git/repos?user={username}&limit=20"
```
**Response Example:**  
```
{
  "repositories": [
    {
      "name": "git-connector",
      "url": "https://github.com/ayushijainmehta/git-connector",
      "commits": [
        {
          "message": "Initial commit",
          "author": "Ayushi Jain",
          "timestamp": "2025-08-28T15:00:30Z"
        }
      ]
    }
  ]
}
```
```

## Setup Instructions

1. Clone the repository
   git clone https://github.com/ayushijainmehta/git-connector.git
2. mvn clean install
3. mvn spring-boot:run

---

## Steps to Create a GitHub PAT 

1. Go to GitHub → Settings → Developer Settings → Personal Access Tokens → Tokens (classic)
2. Give the token a descriptive name and expiration.
3. Select the required scopes: repo, read:org
4. Click Generate token

---

## Production Readiness

1. Security & Configuration: Manage secrets via AWS Secrets Manager, and centralize configurations in application.properties or environment-specific profiles.
2. Reliability & Resilience: Add circuit breakers, caching of recent commits, and handling of GitHub API rate limits.
3. Observability: Implement structured logging, metrics, and error reporting for better monitoring and debugging.
4. API Enhancements: Provide Swagger/OpenAPI documentation.
5. Testing & CI/CD: Include comprehensive unit and integration tests, automated pipelines, and quality checks.
6. Deployment & Containerization: Package the application as a Docker container.
   

