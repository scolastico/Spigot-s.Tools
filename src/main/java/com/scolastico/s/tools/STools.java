package com.scolastico.s.tools;
import com.scolastico.s.tools.api.WebAPI;
import com.scolastico.s.tools.internal.ErrorHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class STools extends JavaPlugin {

    private static Plugin plugin;
    private static final String github_api = "https://api.github.com/repos/scolastico/Spigot-s.Tools/releases/latest";
    private static final String github_release = "https://github.com/scolastico/Spigot-s.Tools/releases/latest/download/sTools.jar";

    @Override
    public void onEnable() {
        System.out.println("[s.Tools] scolastico.Tools - The tool plugin from scolastico!");
        System.out.println("[s.Tools] ");
        plugin = Bukkit.getPluginManager().getPlugin("sTools");
        ErrorHandler.getInstance().setName("s.Tools");
        try {
            WebAPI webAPI = new WebAPI();
            JSONObject object = new JSONObject(webAPI.sendSecureGet(github_api));
            if (!plugin.getDescription().getVersion().equalsIgnoreCase(object.getString("tag_name"))) {
                System.out.println("[s.Tools] Update found!");
                System.out.println("[s.Tools] Current version: '" + plugin.getDescription().getVersion() + "'");
                System.out.println("[s.Tools] Latest version: '" + object.getString("tag_name") + "'");
                System.out.println("[s.Tools] Start update! Please wait a few seconds while the server automatically updates this plugin. ");
                try {
                    URL location = plugin.getClass().getProtectionDomain().getCodeSource().getLocation();
                    String filename = URLDecoder.decode(location.getPath(), StandardCharsets.UTF_8.toString());
                    File file = new File(filename);
                    if (!file.exists()) throw new Exception();
                    webAPI.downloadUsingNIO(github_release, filename);
                    System.out.println("[s.Tools] Update successfully! The plugin should be updated after the next server restart!");
                } catch (Exception ignored) {
                    System.out.println("[s.Tools] Update failed! Please update the plugin manually. You can download the current version here:");
                    System.out.println("[s.Tools] " + github_release);
                }
                System.out.println("[s.Tools] ");
            }
        } catch (Exception ignored) {}
        System.out.println("[s.Tools] If you are wondering why you have this plugin:");
        System.out.println("[s.Tools] This plugin is a tool plugin for plugins from scolastico.");
        System.out.println("[s.Tools] Its important and should not be removed while you are");
        System.out.println("[s.Tools] using plugins from scolastico. If you dont have any plugins");
        System.out.println("[s.Tools] by scolastico you can delete this plugin safely!");
        System.out.println("[s.Tools] ");
        System.out.println("[s.Tools] s.Tools Enabled.");
    }

    @Override
    public void onDisable() {
        System.out.println("[s.Tools] s.Tools Disabled.");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static String getVersion() {
        return plugin.getDescription().getVersion();
    }

}
