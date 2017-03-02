package net.comdude2.plugins.minecraftcore.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ComEvent extends Event {
	protected static final HandlerList handlers = new HandlerList();
    
	public ComEvent() {
    	
	}

	public HandlerList getHandlers() {
    	return handlers;
	}
    
	public static HandlerList getHandlerList() {
    	return handlers;
	}
	
}
