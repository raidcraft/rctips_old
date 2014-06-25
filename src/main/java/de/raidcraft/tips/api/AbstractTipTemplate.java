package de.raidcraft.tips.api;

import de.raidcraft.api.action.requirement.Requirement;
import de.raidcraft.api.action.trigger.TriggerFactory;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mdoering
 */
@Data
@EqualsAndHashCode(of = "identifier")
@Setter(AccessLevel.PROTECTED)
public abstract class AbstractTipTemplate implements TipTemplate {

    private final String identifier;
    private String name;
    private String description;
    private boolean persistant;
    private boolean repeating;
    private long cooldown;
    private final Collection<Requirement<?>> requirements;
    private final Collection<TriggerFactory> trigger;
    private Collection<TipDisplay> displays = new ArrayList<>();

    public AbstractTipTemplate(String identifier) {

        this.identifier = identifier;
        this.requirements = loadRequirements();
        this.trigger = loadTrigger();
    }

    protected abstract Collection<Requirement<?>> loadRequirements();

    protected abstract Collection<TriggerFactory> loadTrigger();

    @Override
    public void processTrigger() {


    }

    @Override
    public <T> Tip<T> display(T entity) {

        Tip<T> tip = createTip(entity);
        tip.display();
        return tip;
    }
}
