package com.fullwall.Citizens.Interfaces;

import com.fullwall.Citizens.Properties.PropertyManager;
import com.fullwall.Citizens.Properties.PropertyManager.PropertyType;
import com.fullwall.resources.redecouverte.NPClib.HumanNPC;

public abstract class Saveable {

	public abstract void saveFiles();

	public abstract void saveState(HumanNPC npc);

	public abstract void loadState(HumanNPC npc);

	public abstract void removeFromFiles(HumanNPC npc);

	public abstract void register(HumanNPC npc);

	public abstract void setEnabled(HumanNPC npc, boolean value);

	public abstract boolean getEnabled(HumanNPC npc);

	public Storage getFile(String name) {
		return PropertyManager.getHandler(this.getClass(), name, this);
	}

	public abstract boolean exists(HumanNPC npc);

	public abstract PropertyType type();

	public abstract void copy(int UID, int nextUID);
}