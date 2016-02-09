package de.hska.github.step;

import de.hska.github.context.DefaultProcessContext;
import de.hska.github.context.ProcessContext;
import de.hska.github.result.*;
import org.apache.commons.io.IOUtils;
import org.kohsuke.github.GHContent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;


public abstract class ScalaRepositoryAnalyzingStep extends AbstractProcessingStep {

    private int position = 1;

    @Override
    public void process(ProcessContext context) {
        if (skipStep(context)) return;
        if (!(context instanceof DefaultProcessContext)) {
            throw new IllegalArgumentException("Wrong context passed");
        }
        DefaultProcessContext defaultContext = (DefaultProcessContext) context;
        RepositorySearchResult repositoryResult = defaultContext.getRepositorySearchResult();
        CodeSearchResult result = _objectFactory.createCodeSearchResult();
        defaultContext.setCodeSearchResult(result, getSearchResultName());
        for (RepositorySearchResultEntry entry : repositoryResult.getRepositorySearchResultEntry()) {
            try {
                if (skipRepo(entry.getFullName())) continue;
                List<GHContent> content = defaultContext.getGitHubManager().getContent(entry.getFullName(), getQuery());
                if (content == null || content.isEmpty()) continue;
                _logger.info("new project found which uses " + getQuery() + ": " + entry.getFullName());
                result.getCodeSearchResultEntry().add(createEntry(content));
            } catch (Throwable e) {
                // in case something went wrong
                _logger.error("something went wrong while searching for " + getQuery() + " in repositories.");
                _logger.error("Skipping repository: " + entry.getFullName());
            }
        }
        defaultContext.storeCodeSearchResult(getSearchResultName());
    }

    private CodeSearchResultEntry createEntry(List<GHContent> content) {
        CodeSearchResultEntry resultEntry = _objectFactory.createCodeSearchResultEntry();
        String repoName = content.get(0).getOwner().getFullName();
        resultEntry.setRepositoryName(repoName);
        resultEntry.setPosition(position++);
        for (GHContent ghContent : content) {
            try {
                // create the necessary objects first
                Occurrences occurrences = _objectFactory.createOccurrences();
                TextMatches textMatches = _objectFactory.createTextMatches();
                occurrences.setTextMatches(textMatches);
                occurrences.setClassName(ghContent.getName());
                // then get the content and execute the search
                InputStream read = ghContent.read();
                String fileContent = IOUtils.toString(read);
                List<String> matchedLines = getMatchedLines(fileContent);
                textMatches.getTextMatch().addAll(matchedLines);
                resultEntry.getOccurrences().add(occurrences);
            } catch (IOException ex) {
                _logger.error("could not download content for file: " + ghContent.getName() + " from repos: " +
                        repoName);
            }
        }
        return resultEntry;
    }

    private List<String> getMatchedLines(String input) {
        Matcher matcher = matcher(input);
        List<String> toReturn = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group().trim();
            toReturn.add(match);
        }
        return toReturn;
    }

    protected abstract boolean skipRepo(String repo);

    protected abstract Matcher matcher(String input);

    protected abstract String getSearchResultName();

    protected abstract String getQuery();
}
