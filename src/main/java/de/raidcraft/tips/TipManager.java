package de.raidcraft.tips;

import com.avaje.ebean.EbeanServer;
import de.raidcraft.RaidCraft;
import de.raidcraft.api.Component;
import de.raidcraft.api.config.SimpleConfiguration;
import de.raidcraft.tips.api.Tip;
import de.raidcraft.tips.api.TipDisplay;
import de.raidcraft.tips.api.TipTemplate;
import de.raidcraft.tips.displays.ChatDisplay;
import de.raidcraft.tips.tables.TTipPlayer;
import de.raidcraft.tips.templates.PlayerTipTemplate;
import de.raidcraft.tips.tips.PlayerTip;
import de.raidcraft.util.CaseInsensitiveMap;
import lombok.NonNull;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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
    private final Map<String, TipDisplay<?>> tipDisplays = new CaseInsensitiveMap<>();
    private final Map<String, TipTemplate<?>> loadedTemplates = new CaseInsensitiveMap<>();

    protected TipManager(TipsPlugin plugin) {

        this.plugin = plugin;
        registerTypes();
        RaidCraft.registerComponent(TipManager.class, this);
    }

    public void load() {

        loadFiles("", new File(plugin.getDataFolder(), "tips").listFiles());
        plugin.getLogger().info("Loaded " + loadedTemplates.size() + " tips...");
    }

    private void loadFiles(String base, File[] files) {

        if (files == null) return;
        List<File> fileList = Arrays.asList(files);
        // lets load all achievements
        fileList.stream()
                .filter(File::isFile)
                .forEach(file -> loadTip(base, file));
        // recurse over all sub directories
        fileList.stream()
                .filter(File::isDirectory)
                .forEach(dir -> loadFiles(base + (base.equals("") ? "" : ".") + dir.getName().toLowerCase(), dir.listFiles()));
    }

    private void loadTip(String identifier, File file) {

        if (!identifier.equals("")) identifier += ".";
        identifier += file.getName().toLowerCase();
        identifier = identifier.replace(".yml", "");
        PlayerTipTemplate template = new PlayerTipTemplate(identifier, plugin.configure(new SimpleConfiguration<>(plugin, file)));
        loadedTemplates.put(identifier, template);
        // register all trigger
        if (template.isEnabled()) template.getTrigger().forEach(trigger -> trigger.registerListener(template));
    }

    public void unload() {

        loadedTemplates.clear();
    }

    public void reload() {

        unload();
        load();
    }

    private void registerTypes() {

        registerTipType(Player.class, PlayerTip.class);
        registerDisplayType(new ChatDisplay());
    }

    public TTipPlayer loadDatabasePlayer(Player entity) {

        EbeanServer database = plugin.getDatabase();
        TTipPlayer player = database.find(TTipPlayer.class).where()
                .eq("uuid", entity.getUniqueId()).findUnique();
        if (player == null) {
            player = new TTipPlayer();
            player.setUuid(entity.getUniqueId());
            player.setPlayer(entity.getName());
            player.setEnabled(true);
            database.save(player);
        }
        return player;
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
    public <T> Tip<T> createTip(@NonNull TipTemplate<T> template, @NonNull T entity) {

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

    public <T> void registerDisplayType(@NonNull TipDisplay<T> display) {

        tipDisplays.put(display.getName(), display);
    }

    public TipDisplay<?> getTipDisplay(@NonNull String name) {

        return tipDisplays.getOrDefault(name, new ChatDisplay());
    }

    @SuppressWarnings("unchecked")
    public <T> List<TipDisplay<T>> getTipDisplays(@NonNull T entity) {

        return tipDisplays.values().stream()
                .filter(display -> display.matchesType(entity))
                .map(display -> (TipDisplay<T>) display)
                .collect(Collectors.toList());
    }
}
