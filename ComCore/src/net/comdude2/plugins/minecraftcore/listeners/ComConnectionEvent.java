package net.comdude2.plugins.minecraftcore.listeners;

import net.comdude2.plugins.minecraftcore.net.Connection;

public class ComConnectionEvent extends ComEvent{
	
	private Connection con = null;
	
	public static enum ConnectionType{JOIN,QUIT,KICK};
	
	public ComConnectionEvent(Connection con) {
		this.con = con;
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
}
