package quangdev05.clearlag24;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearLag24 extends JavaPlugin implements CommandExecutor {

    // Phiên bản plugin
    private final String version = "1.2.0";
    // Tác giả của plugin
    private final String author = "QuangDev05";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        long clearInterval = getConfig().getLong("settings.clear-interval") * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::clearGroundItems, 0L, clearInterval);
        getLogger().info("ClearLag24 plugin has been activated!");
        this.getCommand("clearlag24").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ClearLag24 plugin has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("clearlag24")) {
            if (!sender.hasPermission("clearlag24.admin")) {
                sender.sendMessage("You do not have permission to use this command.");
                return true;
            }
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "reload":
                        reloadConfig();
                        sender.sendMessage("Plugin configuration has been reloaded!");
                        return true;
                    case "clear":
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("all")) {
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
            } else {
                sender.sendMessage("Use /clearlag24 help to see a list of commands.");
                return true;
            }
        }
        return false;
    }

    private void clearGroundItems() {
        // Logic để xóa vật phẩm trên mặt đất...
    }

    private void clearItemsInWorld(World world) {
        // Logic để xóa vật phẩm trong một thế giới cụ thể...
    }

    private void clearAllWorlds() {
        // Logic để xóa vật phẩm trong tất cả các thế giới...
    }

    private void displayHelp(CommandSender sender) {
        sender.sendMessage("§6§lClearLag24 §e- List of commands:");
        sender.sendMessage("§b/clearlag24 reload §f- Reload plugin configuration.");
        sender.sendMessage("§b/clearlag24 clear <world> §f- Removes items on the ground in the specified world.");
        sender.sendMessage("§b/clearlag24 clear all §f- Clear items on the ground in all worlds.");
        sender.sendMessage("§b/clearlag24 help §f- Displays a list of commands and instructions for use.");
        sender.sendMessage("§eAuthor: " + author);
        sender.sendMessage("§eVersion: " + version);
        sender.sendMessage("§eNote: Only Admin can use these commands.");
    }
}