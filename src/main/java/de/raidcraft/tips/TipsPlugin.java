package de.raidcraft.tips;

import de.raidcraft.api.BasePlugin;
import de.raidcraft.tips.commands.BaseCommands;
import de.raidcraft.tips.tables.TPlayerTip;
import de.raidcraft.tips.tables.TTipPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mdoering
 */
@Getter
public class TipsPlugin extends BasePlugin {

    private TipManager tipManager;

    @Override
    public void enable() {

        this.tipManager = new TipManager(this);
        registerCommands(BaseCommands.class);
        Bukkit.getScheduler().runTaskLater(this, this.tipManager::load, 1L);
    }

    @Override
    public void disable() {

        this.tipManager.unload();
    }

    @Override
    public void reload() {

        this.tipManager.reload();
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {

        List<Class<?>> tables = new ArrayList<>();
        tables.add(TPlayerTip.class);
        tables.add(TTipPlayer.class);
        return tables;
    }
}
