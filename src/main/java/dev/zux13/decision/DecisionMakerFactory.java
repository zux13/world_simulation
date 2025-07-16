package dev.zux13.decision;

import dev.zux13.event.EventBus;
import dev.zux13.finder.PathFinder;
import dev.zux13.finder.TargetLocator;

public class DecisionMakerFactory {

    private final PathFinder pathFinder;
    private final TargetLocator targetLocator;
    private final EventBus eventBus;

    private DecisionMaker herbivoreDecisionMaker;
    private DecisionMaker predatorDecisionMaker;

    public DecisionMakerFactory(PathFinder pathFinder, TargetLocator targetLocator, EventBus eventBus) {
        this.pathFinder = pathFinder;
        this.targetLocator = targetLocator;
        this.eventBus = eventBus;
    }

    public synchronized DecisionMaker forHerbivore() {
        if (herbivoreDecisionMaker == null) {
            herbivoreDecisionMaker = new HerbivoreDecisionMaker(pathFinder, targetLocator, eventBus);
        }
        return herbivoreDecisionMaker;
    }

    public synchronized DecisionMaker forPredator() {
        if (predatorDecisionMaker == null) {
            predatorDecisionMaker = new PredatorDecisionMaker(pathFinder, targetLocator, eventBus);
        }
        return predatorDecisionMaker;
    }
}