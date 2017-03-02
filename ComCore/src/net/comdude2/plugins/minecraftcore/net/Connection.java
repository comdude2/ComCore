package net.comdude2.plugins.minecraftcore.net;

import java.util.UUID;

public class Connection {
	
	private boolean connected = false;
	
	private UUID playerId = null;
	private String address = null;
	private String hostName = null;
	
	public Connection(UUID playerId, String address, String hostName){
		this.playerId = playerId;
		this.address = address;
		this.hostName = hostName;
	}
	
	public UUID getPlayerId(){
		return this.playerId;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public String getHostNameUsed(){
		return this.hostName;
	}
	
	public boolean isConnected(){
		return this.connected;
	}
	
	public void setConnected(boolean value){
		this.connected = value;
	}
	
	@Override
	public String toString(){
		return "UUID: " + this.playerId + " IPV4 Address: " + this.address + " Hostname used: " + this.hostName + " Connected: " + String.valueOf(this.connected).toUpperCase();
	}
	
}
