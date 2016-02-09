package de.hska.github.context;

import de.hska.github.connection.GitHubManager;
import de.hska.github.result.AggregationResult;
import de.hska.github.result.CodeSearchResult;
import de.hska.github.result.RepositorySearchResult;
import de.hska.github.step.AbstractProcessingStep.Step;
import de.hska.github.util.JAXBUtils;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;


/**
 * Context File that stores all necessary information used within the processing steps
 */
public class DefaultProcessContext implements ProcessContext {

    public static final String JAVA_SEARCH_RESULT = "JavaConcurrency";
    public static final String SCALA_SEARCH_RESULT = "ScalaConcurrency";
    public static final String ACTOR_SEARCH_RESULT = "Actor";
    public static final String SYNCHRONIZED_SEARCH_RESULT = "Synchronized";
    public static final String AGGREGATION_REPOS = "Repositories";
    public static final String AGGREGATION_COMBINATION = "Combinations";
    public static final String AGGREGATION_ALL = "ALL";

    private static final String[] SEARCH_RESULTS = {JAVA_SEARCH_RESULT, SCALA_SEARCH_RESULT, ACTOR_SEARCH_RESULT,
            SYNCHRONIZED_SEARCH_RESULT};

    private static final File DEFAULT_BASEDIR = new File("results");
    private static final String REPOSITORY_SEARCH_FILE = "repositorySearchResult.xml";
    private static final String AGGREGATION_RESULT_FILE = "aggregationResult";
    private static final String CODE_SEARCH_FILE = "codeSearchResultFor";
    private static final String XML_EXTENSION = ".xml";

    private final JAXBUtils JAXB_UTILS = new JAXBUtils();

    private Logger _log = Logger.getLogger(DefaultProcessContext.class);

    private File _basedir = DEFAULT_BASEDIR;

    private GitHubManager _gitHubManager;
    private Set<Step> _stepsToSkip = new HashSet<Step>();

    // results
    private RepositorySearchResult _repositorySearchResult;
    private Map<String, CodeSearchResult> _codeSearchResultByName;
    private Map<String, AggregationResult> _aggregationResult;

    public DefaultProcessContext() {
        _gitHubManager = new GitHubManager();
        _codeSearchResultByName = new HashMap<String, CodeSearchResult>();
        _aggregationResult = new HashMap<String, AggregationResult>();
    }

    public RepositorySearchResult getRepositorySearchResult() {
        if (_repositorySearchResult != null) {
            return _repositorySearchResult;
        }
        try {
            Unmarshaller unmarshaller = JAXB_UTILS.createUnmarshaller(RepositorySearchResult.class);
            return (RepositorySearchResult) unmarshaller.unmarshal(new File(_basedir, REPOSITORY_SEARCH_FILE));
        } catch (JAXBException e) {
            _log.error("could not unmarshal data");
            throw new RuntimeException("process is broken could not unmarshall result");
        }
    }

    public void storeRepositorySearchResult() {
        if (_repositorySearchResult != null) {
            try {
                Marshaller marshaller = JAXB_UTILS.createMarshaller(RepositorySearchResult.class);
                _log.info("storing repository search results");
                marshaller.marshal(_repositorySearchResult, new File(_basedir, REPOSITORY_SEARCH_FILE));
            } catch (JAXBException e) {
                _log.error("could not marshal to directory");
            }
        }
    }

    public List<CodeSearchResult> getAllCodeSearchResults() {
        List<CodeSearchResult> results = new ArrayList<CodeSearchResult>();
        for (String name : SEARCH_RESULTS) {
            if (_codeSearchResultByName != null && _codeSearchResultByName.get(name) != null) {
                results.add(_codeSearchResultByName.get(name));
                continue;
            }
            try {
                Unmarshaller unmarshaller = JAXB_UTILS.createUnmarshaller(CodeSearchResult.class);
                CodeSearchResult unmarshalledResult = (CodeSearchResult) unmarshaller.unmarshal(new File(_basedir, CODE_SEARCH_FILE + name + XML_EXTENSION));
                results.add(unmarshalledResult);
            } catch (JAXBException e) {
                _log.error("could not unmarshal data");
                throw new RuntimeException("process is broken could not unmarshall result");
            }
        }
        return results;
    }

    public CodeSearchResult getCodeSearchResult(String name) {
        if (_codeSearchResultByName != null && _codeSearchResultByName.get(name) != null) {
            return _codeSearchResultByName.get(name);
        }
        try {
            Unmarshaller unmarshaller = JAXB_UTILS.createUnmarshaller(CodeSearchResult.class);
            return (CodeSearchResult) unmarshaller.unmarshal(new File(_basedir, CODE_SEARCH_FILE + name + XML_EXTENSION));
        } catch (JAXBException e) {
            _log.error("could not unmarshal data");
            throw new RuntimeException("process is broken could not unmarshall result");
        }
    }

    public void storeCodeSearchResult(String name) {
        if (_codeSearchResultByName != null && _codeSearchResultByName.get(name) != null) {
            try {
                Marshaller marshaller = JAXB_UTILS.createMarshaller(CodeSearchResult.class);
                _log.info("storing code search results");
                CodeSearchResult result = _codeSearchResultByName.get(name);
                marshaller.marshal(result, new File(_basedir, CODE_SEARCH_FILE + name + XML_EXTENSION));
            } catch (JAXBException e) {
                _log.error("could not marshal to directory");
            }
        }
    }

    public AggregationResult getAggregationResult(String name) {
        if (null != _aggregationResult.get(name)) {
            return _aggregationResult.get(name);
        }
        try {
            Unmarshaller unmarshaller = JAXB_UTILS.createUnmarshaller(AggregationResult.class);
            return (AggregationResult) unmarshaller.unmarshal(new File(_basedir, AGGREGATION_RESULT_FILE + name + XML_EXTENSION));
        } catch (JAXBException e) {
            _log.error("could not unmarshal data");
            throw new RuntimeException("process is broken could not unmarshall result");
        }
    }

    public void storeAggregationResult(String name) {
        if (_aggregationResult.get(name) != null) {
            try {
                Marshaller marshaller = JAXB_UTILS.createMarshaller(AggregationResult.class);
                _log.info("storing aggregation results");
                marshaller.marshal(_aggregationResult.get(name), new File(_basedir, AGGREGATION_RESULT_FILE + name + XML_EXTENSION));
            } catch (JAXBException e) {
                _log.error("could not marshal to directory");
            }
        }
    }

    public void setRepositoryResult(RepositorySearchResult repositoryResult) {
        _repositorySearchResult = repositoryResult;
    }


    public void setCodeSearchResult(CodeSearchResult result, String name) {
        _codeSearchResultByName.put(name, result);
    }

    public void setAggregationResult(AggregationResult result, String name) {
        _aggregationResult.put(name, result);
    }

    public Set<Step> getStepsToSkip() {
        return _stepsToSkip;
    }

    public void addStepToSkip(Step stepToSkip) {
        _stepsToSkip.add(stepToSkip);
    }

    public GitHubManager getGitHubManager() {
        return _gitHubManager;
    }
}
