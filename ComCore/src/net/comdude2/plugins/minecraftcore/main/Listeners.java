package net.comdude2.plugins.minecraftcore.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class Listeners implements Listener{
	
	private ComCore core = null;
	private boolean registered = false;
	
	public Listeners(ComCore core){
		this.core = core;
	}
	
	public void register(){
		if (registered){}else{
			this.core.getServer().getPluginManager().registerEvents(this, core);
			registered = true;
			this.core.getLogger().info("Events registered.");
		}
	}
	
	public void unregister(){
		if (registered){
			HandlerList.unregisterAll(this);
			registered = false;
			this.core.getLogger().info("Events unregistered.");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onPlayerConnectAttempt(org.bukkit.event.player.AsyncPlayerPreLoginEvent event){
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event){
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKick(org.bukkit.event.player.PlayerKickEvent event){
		
	}
	
}
