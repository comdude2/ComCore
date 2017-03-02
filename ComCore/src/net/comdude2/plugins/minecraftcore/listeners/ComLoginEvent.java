package net.comdude2.plugins.minecraftcore.listeners;

import net.comdude2.plugins.minecraftcore.net.Connection;

public class ComLoginEvent extends ComEvent{
	
	private Connection con = null;
	private boolean allow = true;
	private String reason = null;
	
	public ComLoginEvent(Connection con) {
		this.con = con;
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
	public boolean isConnectionAllowed(){
		return this.allow;
	}
	
	public void disallow(String reason){
		this.allow = false;
	}
	
	public String getReason(){
		return this.reason;
	}
	
}
