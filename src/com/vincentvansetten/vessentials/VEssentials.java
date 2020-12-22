package com.vincentvansetten.vessentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.TileEntityChest;
import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.Block;
//import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.*;
import java.util.ArrayList;
//import java.util.HashMap;
public class VEssentials extends JavaPlugin {
	
	@Override
	public void onEnable() {
		System.out.println("[VEssentials] Loading Plugin");
	    getServer().getPluginManager().registerEvents(new VEventListener(), this);
	}
	

	
	@Override
	public void onDisable() {
		System.out.println("[VEssentials] Disabling Plugin");

	}

	public static ArrayList<String> vanishedPlayers = new ArrayList<String>();
	
    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
        if (command.getName().equalsIgnoreCase("vessentials")) {
        	return command_VEssentials(sender, args);
        }
        else if(command.getName().equalsIgnoreCase("hug")) {
        	return command_Hug(sender, args);
        }
        else if(command.getName().equalsIgnoreCase("kiss")) {
        	return command_Kiss(sender, args);
        }
        else if(command.getName().equalsIgnoreCase("fakeleave")) {
        	return command_FakeLeave(sender, args);
        }
        else if(command.getName().equalsIgnoreCase("fakejoin")) {
        	return command_FakeJoin(sender, args);
        }
        else if(command.getName().equalsIgnoreCase("vanish")) {
        	return command_Vanish(sender, args);
        }
        return false;
    }
    

    
    private boolean command_Vanish(CommandSender sender, String[] args) {
    	Player player;
    	String call;
    	if(args.length > 1) {
			sender.sendMessage(ChatColor.RED + "You can only vanish one other user!");
    		return true;
    	}else if(args.length == 1) {
    		if(sender.hasPermission("vessentials.vanish.others")) {
        	player = getServer().getPlayer(args[0]);
        	call = args[0] + " is";
    		}else {
    			sender.sendMessage(ChatColor.RED + "You do not have permission to vanish others.");
    			return true;
    		}

    	}else {
    		player = getServer().getPlayer(sender.getName());
    		call = "You are";
    	}
    	
    	String player_UID = player.getUniqueId().toString();
    	Boolean doHide = true;
    	
    	// Check if player is hidden already
    	if(vanishedPlayers.contains(player_UID)) {
    		vanishedPlayers.remove(player_UID);
    		doHide = false;
    	}else {
    		vanishedPlayers.add(player_UID);
    		doHide = true;
    	}

    	player.setSilent(doHide);
    	player.setSleepingIgnored(doHide);
    	player.setCanPickupItems(!doHide);
    	player.setCollidable(!doHide);
    	
    	for(Player otherPlayer : Bukkit.getOnlinePlayers()) {
    		if(otherPlayer != player) {
    			if(doHide) {
    				otherPlayer.hidePlayer(this, player);
    			}else {
    				otherPlayer.showPlayer(this, player);
    			}
    		}
    	}
    	
    	if(doHide) {
    		sender.sendMessage(ChatColor.GRAY + call + " now hidden from other players.");
    	}else {
    		sender.sendMessage(ChatColor.GREEN + call + " now shown to other players.");
    	}
		return true;
	}


	private final String CONST_HELP = "/vessentials [help]    - This Command\n/kiss <user>\n/hug <user>";
    
    
    private boolean command_FakeLeave(CommandSender sender, String[] args) {
    	if(args.length > 0) {
    		for(String arg : args) {
    			Bukkit.broadcastMessage(ChatColor.YELLOW + "" + arg + " left the game.");
    		}
    		return true;
    	}else {
    		Bukkit.broadcastMessage(ChatColor.YELLOW + "" + sender.getName() + " left the game.");
    		return true;
    	}
    }
    
    private boolean command_FakeJoin(CommandSender sender, String[] args) {
    	if(args.length > 0) {
    		for(String arg : args) {
    			Bukkit.broadcastMessage(ChatColor.YELLOW + "" + arg + " joined the game.");
    		}
    		return true;
    	}else {
    		Bukkit.broadcastMessage(ChatColor.YELLOW + "" + sender.getName() + " joined the game.");
    		return true;
    	}
    }
    
    private boolean command_Hug(CommandSender sender, String[] args) {
    	if(args.length != 1) {
    		sender.sendMessage(ChatColor.RED + "You can only hug one player!");
    		return true;
    	}
    	String player_str = args[0];
    	Player player = getServer().getPlayer(player_str);
    	if(player == null) {
    		sender.sendMessage(ChatColor.RED + "Invalid player!");
    		return true;
    	}
    	player.sendMessage(ChatColor.LIGHT_PURPLE + "Hug: " + ChatColor.GREEN + "" + sender.getName() + ChatColor.LIGHT_PURPLE + " hugs you!" + ChatColor.RED + " <3");
    	return true;
    }
    
    private boolean command_Kiss(CommandSender sender, String[] args) {
    	if(args.length != 1) {
    		sender.sendMessage(ChatColor.RED + "You can only kiss one player!");
    		return true;
    	}
    	String player_str = args[0];
    	Player player = getServer().getPlayer(player_str);
    	if(player == null) {
    		sender.sendMessage(ChatColor.RED + "Invalid player!");
    		return true;
    	}
    	player.sendMessage(ChatColor.LIGHT_PURPLE + "Kiss: " + ChatColor.GREEN + "" + sender.getName() + ChatColor.LIGHT_PURPLE + " kisses you!" + ChatColor.RED + " <3");
    	return true;
    }
    private boolean command_VEssentials(CommandSender sender, String[] args) {
    	sender.sendMessage(ChatColor.BOLD +  "" + ChatColor.GOLD + "Welcome to VEssentials!");
    	
    	if(args.length >= 1){
	    	switch(args[0].toLowerCase()) {
	    	case "help":
	    		sender.sendMessage("Commands: ");
	    		sender.sendMessage(CONST_HELP);
	    		break;
	    	
	    	default:
	    		sender.sendMessage(ChatColor.RED + "Unknown option!");
	    		return true;
	    	}
    	}
    	
    	return true;
    }
	
}
