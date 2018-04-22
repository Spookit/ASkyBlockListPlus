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

public class SkyBlockChallangeStatus extends SlotList {

	static ASkyBlockAPI api = ASkyBlockAPI.getInstance();
	public SkyBlockChallangeStatus(Plugin plugin) {
		super(plugin);
	}

	@Override
	public String getType() {
		return "SKYBLOCK_CHALLANGES_STATUS";
	}

	@Override
	public SlotListHandler createNewHandler(ConfigurationSection sec) {
		return new SkyBlockChallangeStatusHandler(sec);
	}
	
	public class SkyBlockChallangeStatusHandler extends SlotListHandler {

		String finished;
		String unfinished;
		public SkyBlockChallangeStatusHandler(ConfigurationSection sec) {
			finished = sec.getString("finished-text","&a✔");
			unfinished = sec.getString("unfinished-text","&c✖");
		}
		@Override @SuppressWarnings("unchecked")
		public void replace(PlayerListData data, Object p) {
			String format = getFormat();
			if (p instanceof Entry) {
				Entry<String,Boolean> c = (Entry<String,Boolean>)p;
				format = format.replace("$challange", c.getKey()).replace("$status",c.getValue()?finished:unfinished);
				data.setCurrentText(format);
			}
		}

		@Override
		public Iterator<Object> iterator(Player p) {
			ArrayList<Object> obj = new ArrayList<>();
			Map<String,Boolean> map = api.getChallengeStatus(p.getUniqueId());
			if (map != null) for (Entry<String,Boolean> a : map.entrySet()) {
				obj.add(a);
			}
			return obj.iterator();
		}
		
	}

}
