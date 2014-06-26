package de.raidcraft.tips.api;

import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author mdoering
 */
@Data
public abstract class AbstractTip<T> implements Tip<T> {

    private final TipTemplate<T> template;
    private final T entity;
    @Nullable
    private Timestamp displayed;
    private boolean entityEnabled = true;

    public AbstractTip(@NonNull TipTemplate<T> template, @NonNull T entity) {

        this.template = template;
        this.entity = entity;
    }

    @Override
    public boolean isDisplayed() {

        return getDisplayed() != null;
    }

    @Override
    public boolean isOnCooldown() {

        return getTemplate().isRepeating() && getDisplayed() != null
                && getDisplayed().before(Timestamp.from(Instant.now().minusMillis(getTemplate().getCooldown())));
    }

    @Override
    public void display() {

        if (!isEntityEnabled()) {
            return;
        }
        if (getTemplate().isRepeating() && isOnCooldown()) {
            return;
        }
        if (!getTemplate().isRepeating() && isDisplayed()) {
            return;
        }
        getTemplate().getDisplays().forEach(display -> display.display(getTemplate(), getEntity()));
        setDisplayed(Timestamp.from(Instant.now()));
        save();
    }
}
