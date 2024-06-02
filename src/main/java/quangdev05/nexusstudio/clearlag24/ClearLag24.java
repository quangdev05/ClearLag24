package quangdev05.nexusstudio.clearlag24;

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
    private final String version = "1.1.0";
    // Tác giả của plugin
    private final String author = "QuangDev05";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        long clearInterval = getConfig().getLong("settings.clear-interval") * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::clearGroundItems, 0L, clearInterval);
        getLogger().info("Plugin ClearLag24 đã được kích hoạt!");
        this.getCommand("clearlag24").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin ClearLag24 đã bị tắt!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("clearlag24")) {
            if (!sender.hasPermission("clearlag24.admin")) {
                sender.sendMessage("Bạn không có quyền sử dụng lệnh này.");
                return true;
            }
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "reload":
                        reloadConfig();
                        sender.sendMessage("Cấu hình plugin đã được tải lại!");
                        return true;
                    case "clear":
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("all")) {
                                clearAllWorlds();
                                sender.sendMessage("Tất cả vật phẩm trên mặt đất đã được xóa!");
                            } else {
                                World world = Bukkit.getWorld(args[1]);
                                if (world != null) {
                                    clearItemsInWorld(world);
                                    sender.sendMessage("Vật phẩm trên mặt đất trong world " + args[1] + " đã được xóa!");
                                } else {
                                    sender.sendMessage("World " + args[1] + " không tồn tại!");
                                }
                            }
                        }
                        return true;
                    case "help":
                        displayHelp(sender);
                        return true;
                    default:
                        sender.sendMessage("Lệnh không hợp lệ. Sử dụng /clearlag24 help để xem danh sách lệnh.");
                        return true;
                }
            } else {
                sender.sendMessage("Sử dụng /clearlag24 help để xem danh sách lệnh.");
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
        sender.sendMessage("§6§lClearLag24 §e- Danh sách lệnh:");
        sender.sendMessage("§b/clearlag24 reload §f- Tải lại cấu hình plugin.");
        sender.sendMessage("§b/clearlag24 clear <world> §f- Xóa vật phẩm trên mặt đất trong world chỉ định.");
        sender.sendMessage("§b/clearlag24 clear all §f- Xóa vật phẩm trên mặt đất trong tất cả các thế giới.");
        sender.sendMessage("§b/clearlag24 help §f- Hiển thị danh sách lệnh và hướng dẫn sử dụng.");
        sender.sendMessage("§eTác giả: " + author);
        sender.sendMessage("§ePhiên bản: " + version);
        sender.sendMessage("§eGhi chú: Chỉ Admin mới có thể sử dụng các lệnh này.");
    }
}
