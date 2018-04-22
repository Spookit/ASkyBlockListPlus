package org.spookit.bukkit.addon.askyblocklistplus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.spookit.bukkit.playerlistplus.PlayerListData;
import org.spookit.bukkit.playerlistplus.SlotList;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class SkyBlockTopIsland extends SlotList {

	ASkyBlockAPI api = ASkyBlockAPI.getInstance();
	public SkyBlockTopIsland(Plugin plugin) {
		super(plugin);
	}

	@Override
	public String getType() {
		return "SKYBLOCK_TOP_ISLAND";
	}

	@Override
	public SlotListHandler createNewHandler(ConfigurationSection sec) {
		return new SkyBlockTopIslandHandler();
	}
	
	public class SkyBlockTopIslandHandler extends SlotListHandler {

		@SuppressWarnings("unchecked")
		@Override
		public void replace(PlayerListData data, Object p) {
			String format = getFormat();
			if (p instanceof Entry) {
				Entry<UUID,Integer> owner = (Entry<UUID,Integer>)p;
				OfflinePlayer pl = Bukkit.getOfflinePlayer(owner.getKey());
				Integer level = owner.getValue();
				format = format.replace("$player", pl.getName());
				format = format.replace("$level", level+"");
				data.setCurrentText(format);
			}
		}

		@Override
		public Iterator<Object> iterator(Player p) {
			ArrayList<Object> obj = new ArrayList<>();
			Map<UUID,Integer> owners = api.getTopTen();
			if (owners != null) for (Entry<UUID,Integer> own : owners.entrySet()) {
				obj.add(own);
			}
			return obj.iterator();
		}
		
	}

}
