package de.raidcraft.tips.tips;

import com.avaje.ebean.EbeanServer;
import de.raidcraft.RaidCraft;
import de.raidcraft.tips.TipsPlugin;
import de.raidcraft.tips.api.AbstractTip;
import de.raidcraft.tips.api.TipTemplate;
import de.raidcraft.tips.tables.TPlayerTip;
import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * @author mdoering
 */
public class PlayerTip extends AbstractTip<Player> {

    public PlayerTip(@NonNull TipTemplate<Player> template, @NonNull Player entity) {

        super(template, entity);
        EbeanServer database = RaidCraft.getComponent(TipsPlugin.class).getDatabase();
        TPlayerTip playerTip = database.find(TPlayerTip.class).where()
                .eq("uuid", entity.getUniqueId())
                .eq("template", getTemplate().getIdentifier())
                .findUnique();
        setDisplayed(playerTip == null ? null : playerTip.getDisplayed());
    }

    @Override
    public void save() {

        EbeanServer database = RaidCraft.getComponent(TipsPlugin.class).getDatabase();
        TPlayerTip playerTip = database.find(TPlayerTip.class).where()
                .eq("uuid", getEntity().getUniqueId())
                .eq("template", getTemplate().getIdentifier())
                .findUnique();
        if (playerTip == null) {
            playerTip = new TPlayerTip();
            playerTip.setUuid(getEntity().getUniqueId());
            playerTip.setPlayer(getEntity().getName());
            playerTip.setTemplate(getTemplate().getIdentifier());
        }
        playerTip.setDisplayed(getDisplayed());
        database.save(playerTip);
    }
}
