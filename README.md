# GitHub Repository Activity Connector

## Setup
1. Clone the repository
2. Set environment variable `GITHUB_TOKEN` with your GitHub personal access token
3. Run `mvn spring-boot:run`
4. Access endpoint: `GET /api/github/repos/{username}`

## Design Principles
- Pluggable connector architecture via `Connector` interface
- Handles pagination, rate-limits, and retries
- Returns structured POJOs: Repository and Commit
- Scalable to add connectors for GitLab, Bitbucket, etc.