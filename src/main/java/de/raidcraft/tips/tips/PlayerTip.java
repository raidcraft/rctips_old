package de.raidcraft.tips.tips;

import de.raidcraft.tips.api.AbstractTip;
import de.raidcraft.tips.api.TipTemplate;
import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * @author mdoering
 */
public class PlayerTip extends AbstractTip<Player> {

    public PlayerTip(@NonNull TipTemplate template, @NonNull Player entity) {

        super(template, entity);
    }
}
