package de.hska.github.step;

import de.hska.github.context.DefaultProcessContext;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScalaRepositoryScalaConcurrencyAnalyzingStep extends ScalaRepositoryAnalyzingStep {

    private static final String QUERY = "\"scala.concurrent\"";
    private static final Pattern PATTERN = Pattern.compile(".*scala.concurrent.*");

    private Set<String> _reposToSkip = new HashSet<String>();

    public ScalaRepositoryScalaConcurrencyAnalyzingStep() {
        setLogger(Logger.getLogger(ScalaRepositoryScalaConcurrencyAnalyzingStep.class));
        setupReposToSkip();
    }

    private void setupReposToSkip() {
        _reposToSkip.add("nbronson/scala-stm");
    }

    protected String getSearchResultName() {
        return DefaultProcessContext.SCALA_SEARCH_RESULT;
    }

    @Override
    public Step getStep() {
        return Step.ANALYZE_SCALA_CONCURRENCY;
    }

    @Override
    protected String getQuery() {
        return QUERY;
    }

    @Override
    protected Matcher matcher(String input) {
        return PATTERN.matcher(input);
    }

    @Override
    protected boolean skipRepo(String repo) {
        return _reposToSkip.contains(repo);
    }
}
