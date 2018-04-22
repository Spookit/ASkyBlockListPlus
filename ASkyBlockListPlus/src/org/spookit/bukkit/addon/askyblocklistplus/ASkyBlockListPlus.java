package org.spookit.bukkit.addon.askyblocklistplus;

import java.io.File;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.spookit.bukkit.playerlistplus.PlayerListPlugin;
import org.spookit.bukkit.playerlistplus.SlotList;

public class ASkyBlockListPlus extends JavaPlugin {

	SkyBlockTeam team;
	SkyBlockChallangeTimes chtimes;
	SkyBlockChallangeStatus chstatus;
	SkyBlockTopIsland topisland;
	public void onEnable() {
		if (!new File(getDataFolder(),"config.yml").exists()) saveDefaultConfig();
		reloadConfig();
		this.saveResource("pre-made-config.yml", true);
		PlayerListPlugin.getInstance().getTablist().handlers.register(team = new SkyBlockTeam(this));
		PlayerListPlugin.getInstance().getTablist().handlers.register(topisland = new SkyBlockTopIsland(this));
		PlayerListPlugin.getInstance().getTablist().handlers.register(chtimes = new SkyBlockChallangeTimes(this));
		PlayerListPlugin.getInstance().getTablist().handlers.register(chstatus=new SkyBlockChallangeStatus(this));
		PlayerListPlugin.getInstance().getTablist().handlers.options.add(new OnlyHasIslandOption());
		PlayerListPlugin.getInstance().getTablist().handlers.optionHandlers.add(new OnlyHasIslandOptionHandler());
		PlayerListPlugin.placeholders.add(new ASkyBlockPlaceholder());
		for (String s : getConfig().getConfigurationSection("slot-items").getKeys(false)) {
			for (SlotList sl : Arrays.asList(team,chtimes,chstatus,topisland)) {
				sl.registerNewHandler(getConfig().getConfigurationSection("slot-items."+s));
			}
		}
	}
	public String color(String a) {
		return ChatColor.translateAlternateColorCodes('&', a);
	}
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[]args) {
		String prefix = color("&8[&aASkyBlockList&2+&8] &7");
		try {
			if (!sender.hasPermission("askyblocklistplus.admin")) {
				sender.sendMessage(prefix+"You dont have permission to do this");
				return true;
			}
			if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
				reloadConfig();
				PlayerListPlugin.getInstance().getTablist().handlers.unloadAll(this);
				for (String s : getConfig().getConfigurationSection("slot-items").getKeys(false)) {
					for (SlotList sl : Arrays.asList(team,chtimes,chstatus,topisland)) {
						sl.registerNewHandler(getConfig().getConfigurationSection("slot-items."+s));
					}
				}
				sender.sendMessage(prefix+"Reloaded!");
				return true;
			}
			sender.sendMessage(prefix+"ASkyBlockListPlus plugin by BlueObsidian <3 Do /aslp reload to reload the configuration");
		} catch (Throwable t) {
			sender.sendMessage(prefix+"An error occured :( "+t);
		}
		return true;
	}
}
