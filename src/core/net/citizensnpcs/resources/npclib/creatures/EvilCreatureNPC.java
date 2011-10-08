package net.citizensnpcs.resources.npclib.creatures;

import net.citizensnpcs.SettingsManager;
import net.citizensnpcs.api.event.npc.NPCCreateEvent.NPCCreateReason;
import net.citizensnpcs.api.event.npc.NPCRemoveEvent.NPCRemoveReason;
import net.citizensnpcs.permissions.PermissionManager;
import net.citizensnpcs.properties.properties.UtilityProperties;
import net.citizensnpcs.resources.npclib.NPCManager;
import net.citizensnpcs.utils.InventoryUtils;
import net.citizensnpcs.utils.MessageUtils;
import net.citizensnpcs.utils.Messaging;
import net.citizensnpcs.utils.StringUtils;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.World;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EvilCreatureNPC extends CreatureNPC {
	private boolean isTame = false;

	public EvilCreatureNPC(MinecraftServer minecraftserver, World world,
			String s, ItemInWorldManager iteminworldmanager) {
		super(minecraftserver, world, s, iteminworldmanager);
	}

	@Override
	public void onSpawn() {
		npc.getInventory().setItemInHand(
				new ItemStack(weapons[this.random.nextInt(weapons.length)], 1));
		super.onSpawn();
	}

	@Override
	public void doTick() {
		if (!isTame) {
			EntityHuman closest = getClosestPlayer(this.range);
			if (!hasTarget() && closest != null) {
				if (!PermissionManager.hasPermission(
						(Player) closest.getBukkitEntity(),
						"citizens.evils.immune")) {
					targetClosestPlayer(true, this.range);
				}
			}
			super.doTick();
		}
	}

	@Override
	public void onDeath() {
		ItemStack item = UtilityProperties.getRandomDrop(SettingsManager
				.getString("EvilDrops"));
		if (item != null) {
			this.getEntity().getWorld()
					.dropItemNaturally(this.getLocation(), item);
		}
	}

	@Override
	public CreatureNPCType getType() {
		return CreatureNPCType.EVIL;
	}

	@Override
	public void onRightClick(Player player) {
		if (!PermissionManager.canCreate(player)) {
			Messaging
					.sendError(
							player,
							"You cannot tame this Evil NPC because you have reached the NPC creation limit.");
			return;
		}
		if (player.getItemInHand().getTypeId() == SettingsManager
				.getInt("EvilTameItem")) {
			if (random.nextInt(100) <= SettingsManager.getInt("EvilTameChance")) {
				InventoryUtils.decreaseItemInHand(player);
				isTame = true;
				CreatureTask.despawn(this, NPCRemoveReason.OTHER);
				NPCManager.register(npc.getName(), player.getLocation(),
						player.getName(), NPCCreateReason.RESPAWN);
				player.sendMessage(ChatColor.GREEN + "You have tamed "
						+ StringUtils.wrap(npc.getStrippedName())
						+ "! You can now toggle it to be any type.");
			} else {
				Messaging.send(
						player,
						this.npc,
						StringUtils.colourise(SettingsManager.getString(
								"ChatFormat").replace("%name%",
								npc.getStrippedName()))
								+ ChatColor.WHITE
								+ MessageUtils.getRandomMessage(SettingsManager
										.getString("EvilFailedTameMessages")));
			}
		}
	}

	@Override
	public void onLeftClick(Player player) {
	}
}