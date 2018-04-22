package org.spookit.bukkit.addon.askyblocklistplus;

import org.spookit.bukkit.playerlistplus.PlayerListData;
import org.spookit.bukkit.playerlistplus.PlayerListDataOption;

public class OnlyHasIslandOption implements PlayerListDataOption {

	@Override
	public void apply(PlayerListData target, String key, String value) {
		if (key.equalsIgnoreCase("ONLYHASISLAND") || key.equalsIgnoreCase("HASISLAND")) {
			target.options.put("ONLYHASISLAND", value);
		}
	}

}
