package org.spookit.bukkit.addon.askyblocklistplus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.spookit.bukkit.playerlistplus.PlayerListData;
import org.spookit.bukkit.playerlistplus.PlayerListPlugin;
import org.spookit.bukkit.playerlistplus.SlotList;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class SkyBlockTeam extends SlotList {

	ASkyBlockAPI api = ASkyBlockAPI.getInstance();
	public SkyBlockTeam(Plugin plugin) {
		super(plugin);
	}

	@Override
	public String getType() {
		return "SKYBLOCK_TEAM_MEMBERS";
	}

	@Override
	public SlotListHandler createNewHandler(ConfigurationSection sec) {
		return new SkyBlockTeamHandler(sec);
	}

	public class SkyBlockTeamHandler extends SlotListHandler {

		boolean ping;
		SkyBlockTeamHandler(ConfigurationSection sec) {
			ping = sec.getBoolean("ping",false);
		}
		@Override
		public void replace(PlayerListData data, Object p) {
			String format = getFormat();
			if (p instanceof UUID) {
				UUID u = (UUID)p;
				if (Bukkit.getPlayer(u) == null || !Bukkit.getPlayer(u).isOnline()) return;
				format.replace("$player", Bukkit.getPlayer(u).getName());
				format = PlayerListPlugin.replace(Bukkit.getPlayer(u), format);
				data.setCurrentText(format);
				if (ping) {
					data.setPing(PlayerListPlugin.getPing(Bukkit.getPlayer(u)));
				}
			}
		}

		@Override
		public Iterator<Object> iterator(Player p) {
			ArrayList<Object> obj = new ArrayList<>();
			List<UUID> online = api.getTeamMembers(p.getUniqueId());
			if (online != null) for (UUID u : online) {
				obj.add(u);
			}
			if (!online.contains(p.getUniqueId())) {
				obj.add(p.getUniqueId());
			}
			return obj.iterator();
		}
		
	}
}
