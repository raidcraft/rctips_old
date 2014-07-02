package de.raidcraft.tips.tips;

import com.avaje.ebean.EbeanServer;
import de.raidcraft.RaidCraft;
import de.raidcraft.tips.TipManager;
import de.raidcraft.tips.TipsPlugin;
import de.raidcraft.tips.api.AbstractTip;
import de.raidcraft.tips.api.TipTemplate;
import de.raidcraft.tips.tables.TPlayerTip;
import de.raidcraft.tips.tables.TTipPlayer;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * @author mdoering
 */
public class PlayerTip extends AbstractTip<Player> {

    public PlayerTip(@NonNull TipTemplate<Player> template, @NonNull Player entity) {

        super(template, entity);
        TTipPlayer player = RaidCraft.getComponent(TipManager.class).loadDatabasePlayer(entity);
        setEntityEnabled(player.isEnabled());
        Optional<TPlayerTip> first = player.getTips().stream()
                .filter(tip -> tip.getTemplate().equalsIgnoreCase(getTemplate().getIdentifier()))
                .findFirst();
        if (first.isPresent()) {
            setDisplayed(first.get().getDisplayed());
        }
    }

    @Override
    public void save() {

        EbeanServer database = RaidCraft.getComponent(TipsPlugin.class).getDatabase();
        TTipPlayer player = RaidCraft.getComponent(TipManager.class).loadDatabasePlayer(getEntity());
        TPlayerTip tip = player.getTips().stream()
                .filter(entry -> entry.getTemplate().equalsIgnoreCase(getTemplate().getIdentifier()))
                .findFirst().orElseGet(TPlayerTip::new);
        tip.setPlayer(player);
        tip.setTemplate(getTemplate().getIdentifier());
        tip.setDisplayed(getDisplayed());
        database.save(tip);
    }
}
