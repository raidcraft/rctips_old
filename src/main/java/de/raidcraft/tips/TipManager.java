package de.raidcraft.tips;

import de.raidcraft.RaidCraft;
import de.raidcraft.api.Component;
import de.raidcraft.tips.api.Tip;
import de.raidcraft.tips.api.TipDisplay;
import de.raidcraft.tips.api.TipTemplate;
import de.raidcraft.tips.tips.PlayerTip;
import lombok.NonNull;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mdoering
 */
public final class TipManager implements Component {

    private final TipsPlugin plugin;
    private final Map<Class<?>, Constructor<? extends Tip<?>>> tipClasses = new HashMap<>();
    private final Map<Class<?>, List<TipDisplay<?>>> tipDisplays = new HashMap<>();

    protected TipManager(TipsPlugin plugin) {

        this.plugin = plugin;
        registerTypes();
        RaidCraft.registerComponent(TipManager.class, this);
    }

    private void registerTypes() {

        registerTipType(Player.class, PlayerTip.class);
    }

    public <T> void registerTipType(Class<T> typeClass, Class<? extends Tip<T>> tipClass) {

        try {
            if (tipClasses.containsKey(typeClass)) {
                return;
            }
            Constructor<? extends Tip<T>> constructor = tipClass.getConstructor(TipTemplate.class, typeClass);
            tipClasses.put(typeClass, constructor);
        } catch (NoSuchMethodException e) {
            plugin.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> Tip<T> createTip(@NonNull TipTemplate template, @NonNull T entity) {

        try {
            Class<?> aClass = tipClasses.keySet().stream().filter(key -> key.isAssignableFrom(entity.getClass())).findFirst().get();
            if (aClass != null) {
                Constructor<? extends Tip<?>> constructor = tipClasses.get(aClass);
                return (Tip<T>) constructor.newInstance(template, entity);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            plugin.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }
        plugin.getLogger().warning("Could not find matching constructor for entity type: " + entity.getClass().getCanonicalName());
        return null;
    }

    public <T> void registerDisplayType(Class<T> typeClass, TipDisplay<T> display) {

        if (!tipDisplays.containsKey(typeClass)) {
            tipDisplays.put(typeClass, new ArrayList<>());
        }
        tipDisplays.get(typeClass).add(display);
    }

    @SuppressWarnings("unchecked")
    public <T> List<TipDisplay<T>> getTipDisplays(T entity) {

        Class<?> aClass = tipDisplays.keySet().stream().filter(typeClass -> typeClass.isAssignableFrom(entity.getClass())).findFirst().get();
        if (aClass != null) {
            return tipDisplays.get(aClass).stream().map(entry -> (TipDisplay<T>) entry).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
