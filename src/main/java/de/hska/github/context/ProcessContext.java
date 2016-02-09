package de.hska.github.context;

import de.hska.github.connection.GitHubManager;
import de.hska.github.step.AbstractProcessingStep.Step;

import java.util.Set;


public interface ProcessContext {

    GitHubManager getGitHubManager();

    Set<Step> getStepsToSkip();

}
