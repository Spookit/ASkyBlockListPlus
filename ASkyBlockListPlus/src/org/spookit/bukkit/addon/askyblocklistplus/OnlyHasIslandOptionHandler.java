package org.spookit.bukkit.addon.askyblocklistplus;

import org.bukkit.entity.Player;
import org.spookit.bukkit.playerlistplus.PlayerListData;
import org.spookit.bukkit.playerlistplus.PlayerListDataOptionHandler;
import org.spookit.bukkit.playerlistplus.PlayerListItem;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class OnlyHasIslandOptionHandler implements PlayerListDataOptionHandler {

	@Override
	public void handle(Player p, PlayerListItem item, PlayerListData data) {
		if (data.options.containsKey("ONLYHASISLAND")) {
			boolean has = ASkyBlockAPI.getInstance().hasIsland(p.getUniqueId());
			if (!has) {
				data.setReady(false);
			}
		}
	}

}
