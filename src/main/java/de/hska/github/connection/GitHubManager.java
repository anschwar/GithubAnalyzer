package de.hska.github.connection;

import org.apache.log4j.Logger;
import org.kohsuke.github.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;


public class GitHubManager {

    /**
     * Empty elements to avoid null references
     */
    private static final List<GHRepository> EMPTY_REPO_RESULT = new ArrayList<>();
    private static final List<GHContent> EMPTY_CODE_RESULT = new ArrayList<>();

    /**
     * Login constants
     */
    private static final String DEFAULT_RESOURCE = "/login.properties";
    private static final String LOGIN_PREFIX = "login";
    private static final String OAUTH_PREFIX = "oauth";

    /**
     * Search constants
     */
    private static final int RESULTS_PER_PAGE = 100;
    private static final String SCALA = "Scala";
    private static final String EXTENSION = "scala";

    private Logger _log = Logger.getLogger(GitHubManager.class);
    private GitHub _gitHubConnection = null;

    public GitHubManager() {
        this(null);
    }

    public GitHubManager(String resource) {
        Map<String, String> credentials = loadProperties(resource);
        setUpConnections(credentials);
    }

    /**
     * Abstraction of the repository search. This search will be handled under the control of this manager. The Github
     * object is used for every request.
     * <p>
     * This method returns all repositories belonging to a given interval. If the interval is to wide only 1000
     * elements will be returned. This is the limit given by the github api.
     *
     * @return returns the search result if any result was found. If none was found <b>null</b> will be returned.
     * If the rate limit is reached an empty list will be returned.
     */
    public List<GHRepository> getRepositories(String currentInterval) {
        try {
            GHRepositorySearchBuilder searchBuilder = _gitHubConnection.searchRepositories();
            searchBuilder.language(SCALA);
            searchBuilder.created(currentInterval);
            PagedSearchIterable<GHRepository> paginator = searchBuilder.list();
            paginator.withPageSize(RESULTS_PER_PAGE);
            return paginator.asList();
        } catch (NullPointerException ex) {
            // there are no results for this interval
            _log.info("null");
            return null;
        } catch (Exception ex) {
            // rate limit is reached, try it again
            _log.info("empty");
            return EMPTY_REPO_RESULT;
        }
    }

    /**
     * similar to {@link GitHubManager#getRepositories(String)}.
     */
    public List<GHContent> getContent(String fullRepositoryName, String query) {
        try {
            GHContentSearchBuilder searchBuilder = _gitHubConnection.searchContent();
            searchBuilder.q(query);
            // only search in Scala files
            searchBuilder.extension(EXTENSION);
            searchBuilder.repo(fullRepositoryName);
            PagedSearchIterable<GHContent> paginator = searchBuilder.list();
            paginator.withPageSize(RESULTS_PER_PAGE);
            return paginator.asList();
        } catch (NullPointerException ex) {
            // there are no results for this interval
            _log.info("null");
            return null;
        } catch (Exception ex) {
            // rate limit is reached, try it again
            _log.info("empty");
            return EMPTY_CODE_RESULT;
        }
    }

    private void setUpConnections(Map<String, String> credentials) {
        for (Entry<String, String> entry : credentials.entrySet()) {
            try {
                GitHubBuilder githubBuilder = new GitHubBuilder();
                githubBuilder.withOAuthToken(entry.getValue(), entry.getKey());
                GitHub gitHub = githubBuilder.build();
                if (!gitHub.isCredentialValid()) {
                    _log.error("credentials not valid or bad credentials. Shutting down!");
                    throw new IllegalArgumentException();
                }
                _gitHubConnection = gitHub;
            } catch (IOException e) {
                _log.error("Error occurred while setting up connection");
            }
        }
    }

    private Map<String, String> loadProperties(String resource) {
        Properties props = new Properties();
        InputStream resourceAsStream;
        if (resource == null || resource.isEmpty()) {
            resourceAsStream = GitHubManager.class.getResourceAsStream(DEFAULT_RESOURCE);
        } else {
            resourceAsStream = GitHubManager.class.getResourceAsStream(resource);
        }
        try {
            props.load(resourceAsStream);
        } catch (IOException e) {
            _log.error("could not load resources");
        }
        Map<String, String> credentials = new HashMap<>();
        credentials.put(props.getProperty(LOGIN_PREFIX), props.getProperty(OAUTH_PREFIX));
        return credentials;
    }

}
