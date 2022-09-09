package io.github.igorcossta.files;

import io.github.igorcossta.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {
    private final Main plugin;
    private final String fileName;
    private FileConfiguration dataConfig;
    private File configFile;

    public DataManager(Main plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName + ".yml";
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), fileName);

        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null)
            reloadConfig();
        return dataConfig;
    }

    public void saveConfig() {
        if (dataConfig == null || configFile == null)
            return;

        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "could not save config to " + configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
    }
}
