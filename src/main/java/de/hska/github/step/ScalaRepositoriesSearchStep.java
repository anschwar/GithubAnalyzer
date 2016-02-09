package de.hska.github.step;

import de.hska.github.connection.GitHubManager;
import de.hska.github.context.DefaultProcessContext;
import de.hska.github.context.ProcessContext;
import de.hska.github.result.RepositorySearchResult;
import de.hska.github.result.RepositorySearchResultEntry;
import de.hska.github.util.IntervalIterator;
import de.hska.github.util.IntervalIterator.IntervalPrecision;
import org.apache.log4j.Logger;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.List;


public class ScalaRepositoriesSearchStep extends AbstractProcessingStep {

    // the first projects in Scala were created in april 2008
    private static final String START_DATE = "2008-04-01";
    private static final String END_DATE = "2015-09-31";

    private long _position = 1;

    public ScalaRepositoriesSearchStep() {
        setLogger(Logger.getLogger(ScalaRepositoriesSearchStep.class));
    }

    @Override
    public void process(ProcessContext context) {
        if (skipStep(context)) return;
        if (!(context instanceof DefaultProcessContext)) {
            throw new IllegalArgumentException("Wrong context passed");
        }
        _logger.info("start searching for repositories");
        DefaultProcessContext defaultContext = (DefaultProcessContext) context;
        // execute the search with the builder object
        IntervalIterator intervalIterator = new IntervalIterator(START_DATE, END_DATE, IntervalPrecision.WEEK);
        // this search step always creates a new result and overwrites the old one if necessary
        RepositorySearchResult searchResult = _objectFactory.createRepositorySearchResult();
        while (intervalIterator.hasNext()) {
            String currentInterval = intervalIterator.next();
            _logger.debug("searching for repositories in the interval " + currentInterval);
            searchRepositoriesForInterval(searchResult, currentInterval, defaultContext);
        }
        defaultContext.setRepositoryResult(searchResult);
        defaultContext.storeRepositorySearchResult();
        _logger.info("finished searching for repositories");
    }

    private void searchRepositoriesForInterval(RepositorySearchResult searchResult, String currentInterval,
                                               DefaultProcessContext context) {
        GitHubManager gitHubManager = context.getGitHubManager();
        List<GHRepository> repositories = gitHubManager.getRepositories(currentInterval);
        int count = 0;
        for (GHRepository repository : repositories) {
            searchResult.getRepositorySearchResultEntry().add(createEntry(repository, _position++));
            count++;
        }
        _logger.debug("processed " + count + " for interval " + currentInterval);
    }

    private RepositorySearchResultEntry createEntry(GHRepository currentRepo, long position) {
        RepositorySearchResultEntry entry = _objectFactory.createRepositorySearchResultEntry();
        entry.setPosition(position);
        entry.setRepositoryName(currentRepo.getName());
        entry.setFullName(currentRepo.getFullName());
        entry.setHtmlURL(currentRepo.getHtmlUrl().toString());
        try {
            entry.setCreated(currentRepo.getCreatedAt().toString());
        } catch (IOException e) {
            _logger.error("faild to retrieve date from repository: " + currentRepo.getName());
        }
        entry.setSize(currentRepo.getSize());
        return entry;
    }

    @Override
    public Step getStep() {
        return Step.SEARCH;
    }
}
