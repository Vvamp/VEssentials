package com.vincentvansetten.vessentials;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import java.util.HashMap;
public class VEventListener implements Listener{
	private static HashMap<Inventory, Inventory> virtualChests = new HashMap<Inventory, Inventory>();
	
	@EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
		//TODO: Make sure UID is a user(not null). If it's null, it's the console
    	if (e.getInventory().getHolder() instanceof Chest ){
            String UID = e.getPlayer().getUniqueId().toString();
            if(VEssentials.vanishedPlayers.contains(UID)) {
            	// If player is vanished
            	// Cancel open chest
            	e.setCancelled(true);
            	// Open silently
	            e.getPlayer().sendMessage(ChatColor.GRAY + "> Opening chest silently");
	            Chest chest = (Chest) e.getInventory().getHolder(); // Get the inventory holder
	            Inventory i = chest.getBlockInventory();	// Get the chest inventory
	            int chestSize = 27;
	            
	            Inventory j = Bukkit.createInventory(null, chestSize, "Virtual Chest: (" + chest.getX() + ", " + chest.getY() + ", " + chest.getZ() + ")"); // Create a new(virtual) chest with the same inventory

	            ItemStack[] items = i.getContents();
	            j.setContents(items);
	            virtualChests.put(j, e.getInventory());
            	e.getPlayer().openInventory(j); // Open the virtual chest
       
            }
        }else if(e.getInventory().getHolder() instanceof DoubleChest) {
        	String UID = e.getPlayer().getUniqueId().toString();
            if(VEssentials.vanishedPlayers.contains(UID)) {
            	// If player is vanished
            	// Cancel open chest
            	e.setCancelled(true);
            	// Open silently
	            e.getPlayer().sendMessage(ChatColor.GRAY + "> Opening chest silently");
	            DoubleChest chest = (DoubleChest) e.getInventory().getHolder(); // Get the inventory holder
	            Inventory i = chest.getInventory();	// Get the chest inventory
	            int chestSize = 54;
	            
	            Inventory j = Bukkit.createInventory(null, chestSize, "Virtual Chest: (" + chest.getX() + ", " + chest.getY() + ", " + chest.getZ() + ")"); // Create a new(virtual) chest with the same inventory

	            ItemStack[] items = i.getContents();
	            j.setContents(items);
	            virtualChests.put(j, e.getInventory());
            	e.getPlayer().openInventory(j); // Open the virtual chest
       
            }
        }


    }
	

	@EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
		if (virtualChests.containsKey(e.getInventory())){
            // Save virtual items to the actual chest
			if(virtualChests.get(e.getInventory()).getHolder() instanceof Chest) {
		        Chest chest = (Chest)virtualChests.get(e.getInventory()).getHolder(); 
		        Inventory j = e.getInventory();
		        ItemStack[] items = j.getContents();
		        Inventory ce = chest.getInventory();
		        ce.setContents(items);
			}else {
				DoubleChest chest = (DoubleChest)virtualChests.get(e.getInventory()).getHolder(); 
			    Inventory j = e.getInventory();
			    ItemStack[] items = j.getContents();
			    Inventory ce = chest.getInventory();
			    ce.setContents(items);
			}
	       
	        
       
            
        }
	}
	
    
}
