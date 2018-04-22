package org.spookit.bukkit.addon.askyblocklistplus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.spookit.bukkit.playerlistplus.PlayerListData;
import org.spookit.bukkit.playerlistplus.SlotList;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class SkyBlockChallangeTimes extends SlotList {

	static ASkyBlockAPI api = ASkyBlockAPI.getInstance();
	public SkyBlockChallangeTimes(Plugin plugin) {
		super(plugin);
	}

	@Override
	public String getType() {
		return "SKYBLOCK_CHALLANGES_TIMES";
	}

	@Override
	public SlotListHandler createNewHandler(ConfigurationSection sec) {
		return new SkyBlockChallangeTimesHandler();
	}
	
	public class SkyBlockChallangeTimesHandler extends SlotListHandler {

		@Override @SuppressWarnings("unchecked")
		public void replace(PlayerListData data, Object p) {
			String format = getFormat();
			if (p instanceof Entry) {
				Entry<String,Integer> c = (Entry<String,Integer>)p;
				format = format.replace("$challange", c.getKey()).replace("$times",c.getValue().toString());
				data.setCurrentText(format);
			}
		}

		@Override
		public Iterator<Object> iterator(Player p) {
			ArrayList<Object> obj = new ArrayList<>();
			Map<String,Integer> map = api.getChallengeTimes(p.getUniqueId());
			if (map != null) for (Entry<String,Integer> a : map.entrySet()) {
				obj.add(a);
			}
			return obj.iterator();
		}
		
	}

}
