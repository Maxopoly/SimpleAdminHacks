package com.programmerdan.minecraft.simpleadminhacks.hacks.basic;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.programmerdan.minecraft.simpleadminhacks.BasicHack;
import com.programmerdan.minecraft.simpleadminhacks.BasicHackConfig;
import com.programmerdan.minecraft.simpleadminhacks.SimpleAdminHacks;
import com.programmerdan.minecraft.simpleadminhacks.autoload.AutoLoad;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class ItemMetaConverterHack extends BasicHack {
	
	@AutoLoad
	private boolean enabled;

	public ItemMetaConverterHack(SimpleAdminHacks plugin, BasicHackConfig config) {
		super(plugin, config);
	}
	
	public static BasicHackConfig generate(SimpleAdminHacks plugin, ConfigurationSection config) {
		return new BasicHackConfig(plugin, config);
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void invOpen(InventoryOpenEvent e) {
		if (e.getInventory().getHolder() == null) {
			return; //GUI
		}
		Inventory inv = e.getInventory();
		for(ItemStack is : inv.getStorageContents()) {
			if (!is.hasItemMeta()) {
				continue;
			}
			ItemMeta im = is.getItemMeta();
			if (im == null) {
				continue;
			}
			if (!im.hasLore()) {
				continue;
			}
			for(BaseComponent[] componentArray : im.getLoreComponents()) {
				for(BaseComponent component : componentArray) {
					if (!(component instanceof TextComponent)) {
						continue;
					}
					TextComponent text = (TextComponent) component;
					if (text.getText().length() == 0) {
						continue;
					}
					TextComponent copy = text.duplicate();
					copy.getExtra().clear();
					text.getExtra().add(0, copy);
				}
			}
		}
	}

}
