package org.spookit.bukkit.addon.askyblocklistplus;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.spookit.bukkit.playerlistplus.PlaceholderHandler;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
	
public class ASkyBlockPlaceholder implements PlaceholderHandler{

	ASkyBlockAPI api = ASkyBlockAPI.getInstance();
	@Override
	public String replace(Player p, String s) {
		try {
			Island is = api.getIslandOwnedBy(p.getUniqueId());
			if (is != null) {
				s = s.replace("$island_owner", Bukkit.getPlayer(is.getOwner()).getName());
				if (is.getMembers() != null) {
					s = s.replace("$island_total_members", is.getMembers().size()+"");
					int total = 0;
					for (UUID u : is.getMembers()) {
						if (Bukkit.getPlayer(u) != null && Bukkit.getPlayer(u).isOnline()) total++;
					}
					s = s.replace("$island_online_members", total+"");
				}
				s = s.replace("$island_level", api.getIslandLevel(p.getUniqueId())+"");
			}
		} catch (NullPointerException e) {
		}
		return s;
	}

}
