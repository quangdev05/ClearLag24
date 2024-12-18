package quangdev05.clearlag24;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.List;

public class ClearLag24 extends JavaPlugin implements CommandExecutor {

    private final String version = "1.4.0";
    private final String author = "QuangDev05";

    @Override
    public void onEnable() {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        reloadConfig();
        long clearInterval = getConfig().getLong("settings.clear-interval") * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Bukkit.broadcastMessage(getConfig().getString("settings.broadcast-message"));
            clearGroundItems();
        }, 0L, clearInterval);
        getLogger().info("ClearLag24 plugin has been activated!");
        this.getCommand("clearlag24").setExecutor(this);
        this.getCommand("clagg").setExecutor(this); // Đăng ký lệnh viết tắt
    }

    @Override
    public void onDisable() {
        getLogger().info("ClearLag24 plugin has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("clearlag24") ||
                command.getName().equalsIgnoreCase("clagg")) {
            if (!sender.hasPermission("clearlag24.admin")) {
                sender.sendMessage("You do not have permission to use this command.");
                return true;
            }
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (!new File(getDataFolder(), "config.yml").exists()) {
                    saveDefaultConfig();
                }
                reloadConfig();
                updateClearSchedule();
                sender.sendMessage("Plugin configuration has been reloaded!");
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "clear":
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("allworld")) {
                            clearAllWorlds();
                            sender.sendMessage("All items on the ground have been removed!");
                        } else {
                            World world = Bukkit.getWorld(args[1]);
                            if (world != null) {
                                clearItemsInWorld(world);
                                sender.sendMessage("Items on the ground in world " + args[1] + " have been removed!");
                            } else {
                                sender.sendMessage("World " + args[1] + " doesn't exist!");
                            }
                        }
                    }
                    return true;
                case "help":
                    displayHelp(sender);
                    return true;
                default:
                    sender.sendMessage("Invalid order. Use /clearlag24 help to see a list of commands.");
                    return true;
            }
        }
        return false;
    }

    private void updateClearSchedule() {
        Bukkit.getScheduler().cancelTasks(this);
        long clearInterval = getConfig().getLong("settings.clear-interval") * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Bukkit.broadcastMessage(getConfig().getString("settings.broadcast-message"));
            clearGroundItems();
        }, 0L, clearInterval);
    }

    private void clearGroundItems() {
        List<String> blacklistWorlds = getConfig().getStringList("settings.blacklist.worlds");
        if (getConfig().getBoolean("settings.clear-all-worlds")) {
            for (World world : Bukkit.getWorlds()) {
                if (!blacklistWorlds.contains(world.getName())) {
                    clearItemsInWorld(world);
                }
            }
        } else {
            for (String worldName : getConfig().getStringList("settings.worlds")) {
                if (!blacklistWorlds.contains(worldName)) {
                    World world = Bukkit.getWorld(worldName);
                    if (world != null) {
                        clearItemsInWorld(world);
                    }
                }
            }
        }
    }

    private void clearItemsInWorld(World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Item && !isBlacklisted(entity)) {
                entity.remove();
            }
        }
    }

    private void clearAllWorlds() {
        for (World world : Bukkit.getWorlds()) {
            clearItemsInWorld(world);
        }
    }

    private boolean isBlacklisted(Entity entity) {
        Item item = (Item) entity;
        return getConfig().getStringList("settings.items").contains(item.getItemStack().getType().name());
    }

    private void displayHelp(CommandSender sender) {
        sender.sendMessage("§6§lClearLag24 §e- List of commands:");
        sender.sendMessage("§b/clagg reload §f- Reload plugin configuration.");
        sender.sendMessage("§b/clagg clear <world> §f- Removes items on the ground in the specified world.");
        sender.sendMessage("§b/clagg clear allworld §f- Clear items on the ground in all worlds.");
        sender.sendMessage("§b/clagg help §f- Displays a list of commands and instructions for use.");
        sender.sendMessage("§eAuthor: " + author);
        sender.sendMessage("§eVersion: " + version);
        sender.sendMessage("§eNote: Only Admin can use these commands.");
    }
}
