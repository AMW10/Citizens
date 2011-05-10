package com.fullwall.Citizens.Properties;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.fullwall.Citizens.Citizens;
import com.fullwall.Citizens.PropertyHandler;
import com.fullwall.Citizens.Interfaces.Saveable;
import com.fullwall.Citizens.NPCs.NPCManager;
import com.fullwall.Citizens.Properties.PropertyManager.PropertyType;
import com.fullwall.resources.redecouverte.NPClib.HumanNPC;

public class BasicProperties extends Saveable {
	public PropertyHandler colours;
	public PropertyHandler economy;
	public PropertyHandler itemlookups;
	public PropertyHandler items;
	public PropertyHandler locations;
	public PropertyHandler lookat;
	public PropertyHandler owners;
	public PropertyHandler settings;
	public PropertyHandler talkwhenclose;
	public PropertyHandler texts;
	public PropertyHandler counts;
	public PropertyHandler balances;

	public BasicProperties() {
		colours = new PropertyHandler(
				"plugins/Citizens/Basic NPCs/Citizens.colours");
		economy = new PropertyHandler("plugins/Citizens/Citizens.economy");
		itemlookups = new PropertyHandler(
				"plugins/Citizens/Citizens.itemlookup");
		items = new PropertyHandler(
				"plugins/Citizens/Basic NPCs/Citizens.items");
		locations = new PropertyHandler(
				"plugins/Citizens/Basic NPCs/Citizens.locations");
		lookat = new PropertyHandler(
				"plugins/Citizens/Basic NPCs/Citizens.lookat");
		owners = new PropertyHandler(
				"plugins/Citizens/Basic NPCs/Citizens.owners");
		settings = new PropertyHandler("plugins/Citizens/Citizens.settings");
		talkwhenclose = new PropertyHandler(
				"plugins/Citizens/Basic NPCs/Citizens.talkWhenClose");
		texts = new PropertyHandler(
				"plugins/Citizens/Basic NPCs/Citizens.texts");
		counts = new PropertyHandler(
				"plugins/Citizens/Basic NPCs/Citizens.counts");
		balances = new PropertyHandler(
				"plugins/Citizens/Traders/Citizens.balances");
	}

	public int getMaxNPCsPerPlayer() {
		return settings.getInt("max-NPCs-per-player");
	}

	public int getNPCAmountPerPlayer(String name) {
		return counts.getInt(name);
	}

	public void saveNPCAmountPerPlayer(String name, int totalNPCs) {
		counts.setInt(name, totalNPCs);
	}

	public Location getActualLocationFromName(int UID) {
		String[] values = locations.getString(UID).split(",");
		if (values.length != 6) {
			Citizens.log
					.info("getLocationFromName didn't have 6 values in values variable! Length: "
							+ values.length);
			return null;
		} else {
			Location loc = new Location(Bukkit.getServer().getWorld(values[0]),
					NPCManager.getNPC(UID).getX(), NPCManager.getNPC(UID)
							.getY(), NPCManager.getNPC(UID).getZ(),
					Float.parseFloat(values[4]), Float.parseFloat(values[5]));
			return loc;
		}
	}

	public Location getLocationFromID(int UID) {
		String[] values = locations.getString(UID).split(",");
		if (values.length != 6) {
			Citizens.log
					.info("getLocationFromName didn't have 6 values in values variable! Length: "
							+ values.length);
			return null;
		} else {
			Location loc = new Location(Bukkit.getServer().getWorld(values[0]),
					Double.parseDouble(values[1]),
					Double.parseDouble(values[2]),
					Double.parseDouble(values[3]), Float.parseFloat(values[4]),
					Float.parseFloat(values[5]));
			return loc;
		}
	}

	public void saveLocation(String name, Location loc, int UID) {
		String location = loc.getWorld().getName() + "," + loc.getX() + ","
				+ loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + ","
				+ loc.getPitch();
		locations.setString(UID, location);
		if (!locations.getString("list").contains("" + UID + "_" + name))
			locations.setString("list", locations.getString("list") + "" + UID
					+ "_" + name + ",");
	}

	public ArrayList<Integer> getItems(int UID) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		String current = items.getString(UID);
		if (current.isEmpty()) {
			current = "0,0,0,0,0,";
			items.setString(UID, current);
		}
		for (String s : current.split(",")) {
			array.add(Integer.parseInt(s));
		}
		return array;
	}

	public void saveItems(int UID, ArrayList<Integer> items2) {
		String toSave = "";
		for (Integer i : items2) {
			toSave += "" + i + ",";
		}
		items.setString(UID, toSave);
	}

	public String getColour(int UID) {
		return colours.getString(UID);
	}

	public void saveColour(int UID, String colour) {
		colours.setString(UID, "" + colour);
	}

	public String getDefaultText() {
		String[] split = settings.getString("default-text").split(";");
		String text;
		if (split != null) {
			text = split[new Random(System.currentTimeMillis())
					.nextInt(split.length)];
			if (text == null)
				text = "";
		} else
			text = "";
		return text.replace('&', '�');
	}

	public ArrayList<String> getText(int UID) {
		String current = texts.getString(UID);
		if (!current.isEmpty()) {
			ArrayList<String> text = new ArrayList<String>();
			for (String string : current.split(";")) {
				text.add(string);
			}
			return text;
		} else
			return null;
	}

	public void getSetText(int UID) {
		String current = texts.getString(UID);
		if (!current.isEmpty()) {
			ArrayList<String> text = new ArrayList<String>();
			for (String string : current.split(";")) {
				text.add(string);
			}
			NPCManager.setBasicNPCText(UID, text);
		}
	}

	public void saveText(int UID, ArrayList<String> text) {
		String adding = "";
		if (text != null) {
			for (String string : text) {
				adding += string + ";";
			}
		}
		texts.setString(UID, adding);
	}

	public boolean getLookWhenClose(int UID) {
		return lookat.getBoolean(UID);
	}

	public void setLookWhenClose(int UID, boolean look) {
		lookat.setBoolean(UID, look);
	}

	public void saveLookWhenClose(int UID, boolean value) {
		lookat.setBoolean(UID, value);
	}

	public boolean getTalkWhenClose(int UID) {
		return talkwhenclose.getBoolean(UID);
	}

	public void setTalkWhenClose(int UID, boolean talk) {
		talkwhenclose.setBoolean(UID, talk);
	}

	public void saveTalkWhenClose(int UID, boolean value) {
		talkwhenclose.setBoolean(UID, value);
	}

	public String getOwner(int UID) {
		return owners.getString(UID);
	}

	public void setOwner(int UID, String name) {
		owners.setString(UID, name);
	}

	public void addOwner(int UID, String name, Player p) {
		String[] npcOwners = getOwner(UID).split(",");
		for (int i = 0; i < npcOwners.length; i++) {
			if (npcOwners[i].equals(name) == true) {
				p.sendMessage(ChatColor.RED
						+ "This NPC already is owned by that player.");
				return;
			}
		}
		owners.setString(UID, getOwner(UID) + "," + name);
	}

	public int getNewNpcID() {
		if (locations.getString("currentID").isEmpty()) {
			locations.setInt("currentID", 0);
		}
		int returnResult = Integer.valueOf(locations.getString("currentID"));
		locations.setInt("currentID", (returnResult + 1));
		return returnResult;
	}

	public double getPrice(String operation) {
		return economy.getDouble(operation);
	}

	public int getCurrencyID(String string) {
		int ID = economy.getInt(string);
		return ID == -1 ? 1 : ID;
	}

	public void saveBalance(int UID, double balance) {
		balances.setDouble(UID, balance);
	}

	public double getBalance(int UID) {
		return balances.getDouble(UID);
	}

	public boolean checkEconomyEnabled() {
		return economy.getBoolean("use-economy");
	}

	public boolean checkiConomyEnabled() {
		return economy.getBoolean("use-iconomy");
	}

	public void changeName(int UID, String changeFrom, String changeTo) {
		// IDs Remain the same, no need to save other settings.
		locations.setString(
				"list",
				locations.getString("list").replace(
						(UID + "_" + changeFrom + ","), ""));
	}

	public void deleteNameFromList(String name) {
		locations.setString("list",
				locations.getString("list").replace(name + ",", ""));
	}

	@Override
	public void saveFiles() {
		texts.save();
		locations.save();
		colours.save();
		owners.save();
		items.save();
		talkwhenclose.save();
		lookat.save();
		counts.save();
		balances.save();
	}

	@Override
	public void saveState(HumanNPC npc) {
		saveBalance(npc.getUID(), npc.getNPCData().getBalance());
		saveLocation(npc.getStrippedName(), npc.getNPCData().getLocation(),
				npc.getUID());
		saveColour(npc.getUID(), npc.getNPCData().getColour());
		saveItems(npc.getUID(), npc.getNPCData().getItems());
		saveText(npc.getUID(), npc.getNPCData().getTexts());
		saveLookWhenClose(npc.getUID(), npc.getNPCData().isLookClose());
		saveTalkWhenClose(npc.getUID(), npc.getNPCData().isTalkClose());
		setOwner(npc.getUID(), npc.getNPCData().getOwner());
	}

	@Override
	public void removeFromFiles(HumanNPC npc) {
		colours.removeKey(npc.getUID());
		items.removeKey(npc.getUID());
		locations.removeKey(npc.getUID());
		locations.setString(
				"list",
				locations.getString("list").replace(
						"" + npc.getUID() + "_" + npc.getStrippedName() + ",",
						""));
		owners.removeKey(npc.getUID());
		lookat.removeKey(npc.getUID());
		talkwhenclose.removeKey(npc.getUID());
		texts.removeKey(npc.getUID());
		if (counts.keyExists(npc.getOwner()))
			counts.setInt(npc.getOwner(), counts.getInt(npc.getOwner()) - 1);
		balances.removeKey(npc.getUID());
	}

	@Override
	public void set(HumanNPC npc) {
	}

	@Override
	public void set(HumanNPC npc, boolean value) {
	}

	@Override
	public boolean exists(HumanNPC npc) {
		return true;
	}

	@Override
	public PropertyType type() {
		return PropertyType.BASIC;
	}

	@Override
	public void copy(int UID, int nextUID) {
		if (texts.keyExists(UID))
			texts.setString(nextUID, texts.getString(UID));
		if (locations.keyExists(UID))
			locations.setString(nextUID, locations.getString(UID));
		if (colours.keyExists(UID))
			colours.setString(nextUID, colours.getString(UID));
		if (owners.keyExists(UID))
			owners.setString(nextUID, owners.getString(UID));
		if (items.keyExists(UID))
			items.setString(nextUID, items.getString(UID));
		if (talkwhenclose.keyExists(UID))
			talkwhenclose.setString(nextUID, talkwhenclose.getString(UID));
		if (lookat.keyExists(UID))
			lookat.setString(nextUID, lookat.getString(UID));
		if (counts.keyExists(UID))
			counts.setString(nextUID, counts.getString(UID));
		if (balances.keyExists(UID))
			balances.setString(nextUID, balances.getString(UID));
		saveFiles();
	}
}