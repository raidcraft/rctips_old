package de.raidcraft.tips;

import de.raidcraft.api.BasePlugin;
import de.raidcraft.tips.tables.TPlayerTip;
import de.raidcraft.tips.tables.TTipTemplate;
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
    }

    @Override
    public void disable() {


    }

    @Override
    public List<Class<?>> getDatabaseClasses() {

        List<Class<?>> tables = new ArrayList<>();
        tables.add(TPlayerTip.class);
        tables.add(TTipTemplate.class);
        return tables;
    }
}
