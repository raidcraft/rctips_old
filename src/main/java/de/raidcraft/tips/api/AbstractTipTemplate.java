package de.raidcraft.tips.api;

import de.raidcraft.api.action.requirement.Requirement;
import de.raidcraft.api.action.TriggerFactory;
import de.raidcraft.api.action.trigger.TriggerListenerConfigWrapper;
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
public abstract class AbstractTipTemplate<T> implements TipTemplate<T> {

    private final String identifier;
    private String name;
    private String description;
    private boolean enabled;
    private long cooldown;
    private Collection<Requirement<T>> requirements = new ArrayList<>();
    private Collection<TriggerFactory> trigger = new ArrayList<>();
    private Collection<TipDisplay<T>> displays = new ArrayList<>();

    public AbstractTipTemplate(String identifier) {

        this.identifier = identifier;
    }

    public Collection<Requirement<?>> getRequirements() {

        return new ArrayList<>(requirements);
    }

    @Override
    public boolean isRepeating() {

        return getCooldown() > 0;
    }

    @Override
    public boolean processTrigger(T entity, TriggerListenerConfigWrapper trigger) {

        if (!isEnabled()) return false;
        if (requirements.stream().allMatch(requirement -> requirement.test(entity))) {
            // the tip display tracks if it already displayed so we dont need to wory
            display(entity);
        }
        // never execute actions
        return false;
    }

    @Override
    public Tip<T> display(T entity) {

        Tip<T> tip = createTip(entity);
        tip.display();
        return tip;
    }
}
