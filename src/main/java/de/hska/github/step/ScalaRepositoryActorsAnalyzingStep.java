package de.hska.github.step;

import de.hska.github.context.DefaultProcessContext;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScalaRepositoryActorsAnalyzingStep extends ScalaRepositoryAnalyzingStep {

    private static final String QUERY = "\"akka.actor\"";
    private static final Pattern PATTERN = Pattern.compile(".*akka.actor.*");

    private Set<String> _reposToSkip = new HashSet<String>();


    public ScalaRepositoryActorsAnalyzingStep() {
        setLogger(Logger.getLogger(ScalaRepositoryActorsAnalyzingStep.class));
        setupReposToSkip();
    }

    private void setupReposToSkip() {
        _reposToSkip.add("akka/akka");
    }

    protected String getSearchResultName() {
        return DefaultProcessContext.ACTOR_SEARCH_RESULT;
    }

    @Override
    public Step getStep() {
        return Step.ANALYZE_ACTORS;
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
