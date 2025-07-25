package dev.zux13.decision;

import dev.zux13.event.EventBus;
import dev.zux13.navigator.Navigator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DecisionMakerFactory {

    private final Navigator navigator;
    private final RoamingHelper roamingHelper;
    private final EventBus eventBus;

    private DecisionMaker herbivoreDecisionMaker;
    private DecisionMaker predatorDecisionMaker;

    public synchronized DecisionMaker forHerbivore() {
        if (herbivoreDecisionMaker == null) {
            herbivoreDecisionMaker = new HerbivoreDecisionMaker(navigator, roamingHelper, eventBus);
        }
        return herbivoreDecisionMaker;
    }

    public synchronized DecisionMaker forPredator() {
        if (predatorDecisionMaker == null) {
            predatorDecisionMaker = new PredatorDecisionMaker(navigator, roamingHelper, eventBus);
        }
        return predatorDecisionMaker;
    }
}