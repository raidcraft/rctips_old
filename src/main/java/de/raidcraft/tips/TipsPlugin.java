package de.raidcraft.tips;

import de.raidcraft.api.BasePlugin;
import de.raidcraft.tips.commands.BaseCommands;
import de.raidcraft.tips.tables.TPlayerTip;
import lombok.Getter;

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
        this.tipManager.load();
        registerCommands(BaseCommands.class);
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
        return tables;
    }
}
