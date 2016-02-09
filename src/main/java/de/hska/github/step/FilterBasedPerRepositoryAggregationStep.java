package de.hska.github.step;

import de.hska.github.context.DefaultProcessContext;
import de.hska.github.context.ProcessContext;
import de.hska.github.filters.CommentsRemovingFilter;
import de.hska.github.filters.FilterChain;
import de.hska.github.filters.GenericFilter;
import de.hska.github.filters.TrimFilter;
import de.hska.github.matcher.*;
import de.hska.github.result.*;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;


public class FilterBasedPerRepositoryAggregationStep extends AbstractProcessingStep {

    private Map<String, Set<String>> _concurrencyFeaturePerRepo = new HashMap<>();
    private FilterChain _filterChain;
    private MatcherChain _matcherChain;

    public FilterBasedPerRepositoryAggregationStep() {
        setLogger(Logger.getLogger(FilterBasedPerRepositoryAggregationStep.class));
        _filterChain = new FilterChain(new GenericFilter("|"), new GenericFilter("@"), new GenericFilter("package"),
                new GenericFilter("\""), new TrimFilter(), new CommentsRemovingFilter());
        _matcherChain = new MatcherChain(new AkkaActorMatcher(), new ScalaFutureMatcher(), new JavaLocksMatcher(),
                new JavaSemaphoreMatcher(), new JavaFutureMatcher(), new ScalaLockMatcher(), new ScalaPromiseMatcher(),
                new ScalaStmMatcher(), new SemaphoreMatcher(), new SynchronizedMatcher());
    }

    @Override
    public void process(ProcessContext context) {
        if (skipStep(context)) return;
        if (!(context instanceof DefaultProcessContext)) {
            throw new IllegalArgumentException("Wrong context passed");
        }
        DefaultProcessContext defaultContext = (DefaultProcessContext) context;
        // aggregate the data first
        aggregateData(defaultContext);
        // the create the result object
        storeResult(defaultContext);
    }

    private void aggregateData(DefaultProcessContext context) {
        List<CodeSearchResult> allCodeSearchResults = context.getAllCodeSearchResults();
        for (CodeSearchResult codeSearchResult : allCodeSearchResults) {
            if (null != codeSearchResult) {
                List<CodeSearchResultEntry> codeSearchResultEntry = codeSearchResult.getCodeSearchResultEntry();
                for (CodeSearchResultEntry repo : codeSearchResultEntry) {
                    Set<String> repositoryMatches;
                    if (_concurrencyFeaturePerRepo.containsKey(repo.getRepositoryName())) {
                        repositoryMatches = _concurrencyFeaturePerRepo.get(repo.getRepositoryName());
                    } else {
                        repositoryMatches = new HashSet<>();
                    }
                    List<Occurrences> occurrences = repo.getOccurrences();
                    // this is the class and all matching lines
                    for (Occurrences occurrence : occurrences) {
                        TextMatches textMatches = occurrence.getTextMatches();
                        if (null != textMatches) {
                            for (String textMatch : textMatches.getTextMatch()) {
                                String filtered = _filterChain.doFilter(textMatch);
                                if (null != filtered && !filtered.isEmpty()) {
                                    List<String> matchingResult = _matcherChain.doMatch(filtered);
                                    if (null != matchingResult) {
                                        repositoryMatches.addAll(matchingResult);
                                    }
                                }
                            }
                        }
                    }
                    _concurrencyFeaturePerRepo.put(repo.getRepositoryName(), repositoryMatches);
                }
            }
        }
    }

    private void storeResult(DefaultProcessContext context) {
        AggregationResult aggregationResult = _objectFactory.createAggregationResult();
        context.setAggregationResult(aggregationResult, DefaultProcessContext.AGGREGATION_REPOS);
        for (Entry<String, Set<String>> entrySet : _concurrencyFeaturePerRepo.entrySet()) {
            AggregationResultEntry entry = _objectFactory.createAggregationResultEntry();
            Features features = _objectFactory.createFeatures();
            entry.setRepositoryName(entrySet.getKey());
            entry.setFeatures(features);
            for (String name : entrySet.getValue()) {
                Feature feature = _objectFactory.createFeature();
                feature.setValue(name);
                features.getFeature().add(feature);
            }
            aggregationResult.getAggregationResultEntry().add(entry);
        }
        context.storeAggregationResult(DefaultProcessContext.AGGREGATION_REPOS);
    }

    @Override
    public Step getStep() {
        return Step.REPO_AGGREGATION;
    }

}
