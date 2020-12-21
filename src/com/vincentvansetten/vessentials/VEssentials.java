package com.vincentvansetten.vessentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public class VEssentials extends JavaPlugin {
	
	@Override
	public void onEnable() {
		System.out.println("[VEssentials] Loading Plugin");
	}
	

	
	@Override
	public void onDisable() {
		System.out.println("[VEssentials] Disabling Plugin");

	}

	
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
        return false;
    }
    
    
    private final String CONST_HELP = "/vessentials [help]    - This Command\n/kiss <user>\n/hug <user>";
    
    private boolean command_Hug(CommandSender sender, String[] args) {
    	if(args.length != 1) {
    		sender.sendMessage("/hug: Invalid player!");
    		return false;
    	}
    	String player_str = args[0];
    	Player player = getServer().getPlayer(player_str);
    	if(player == null) {
    		sender.sendMessage("/hug: Invalid player!");
    		return false;
    	}
    	player.sendMessage(ChatColor.LIGHT_PURPLE + "Hug: " + ChatColor.GREEN + "" + sender.getName() + ChatColor.LIGHT_PURPLE + " hugs you!" + ChatColor.RED + " <3");
    	return true;
    }
    
    private boolean command_Kiss(CommandSender sender, String[] args) {
    	if(args.length != 1) {
    		sender.sendMessage("/hug: Invalid player!");
    		return false;
    	}
    	String player_str = args[0];
    	Player player = getServer().getPlayer(player_str);
    	if(player == null) {
    		sender.sendMessage("/hug: Invalid player!");
    		return false;
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
	    		return false;
	    	}
    	}
    	
    	return true;
    }
	
}
