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

## Setup Instructions

1. Clone the repository
   git clone https://github.com/ayushijainmehta/git-connector.git
2. mvn clean install
3. mvn spring-boot:run

## Steps to Create a GitHub PAT 

1. Go to GitHub → Settings → Developer Settings → Personal Access Tokens → Tokens (classic)
2. Give the token a descriptive name and expiration.
3. Select the required scopes: repo, read:org
4. Click Generate token



