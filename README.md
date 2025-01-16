# ClearLag24 (The Author Has Stopped Updating Plugins)

![GitHub release (latest by date)](https://img.shields.io/github/v/release/quangdev05/ClearLag24)
![GitHub license](https://img.shields.io/github/license/quangdev05/ClearLag24)
![Supported server version](https://img.shields.io/badge/minecraft-1.12x%20--_Latest-green)
[![Discord](https://img.shields.io/discord/1247029974154612828.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/HsSUVGSc3c)

ClearLag24 is a server plugin designed to reduce lag on Bukkit/Spigot servers by automatically removing ground items (such as dropped items and experience orbs). It helps optimize server performance by periodically clearing these items. The plugin offers several features:

- **Automatic Item Clearing:** The plugin periodically removes items on the ground to prevent them from causing lag.
- **Customizable Settings:** You can configure the interval between item clears, specify which worlds to clear items in, and blacklist specific worlds or item types.
- **Reload Configuration:** Use `/clagg reload` to reload the plugin's configuration from the `config.yml` file.
- **World-Specific Clearing:** Use `/clagg clear <world>` to clear items in a specific world. Alternatively, `/clagg clear all` clears items in all worlds.
- **Help Command:** Type `/clagg help` to display a list of available commands and their usage instructions.

Remember that only Admins have permission to use these commands. The plugin aims to enhance server performance and maintain a smoother gameplay experience.

## Permissions

- Please add `clearlag24.admin` permission to be able to use plugin commands for Minecraft server admin.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
