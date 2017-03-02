package net.comdude2.plugins.minecraftcore.net;

import java.io.Serializable;

public class TerminalCredentials implements Serializable{
	
	private static final long serialVersionUID = -1086127299213837735L;
	
	private String username = null;
	private String passwordHash = null;
	private byte[] salt = null;
	
	public TerminalCredentials(String username, String passwordHash, byte[] salt){
		this.username = username;
		this.passwordHash = passwordHash;
		this.salt = salt;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPasswordHash(){
		return this.passwordHash;
	}
	
	public void setPasswordHash(String passwordHash){
		this.passwordHash = passwordHash;
	}
	
	public byte[] getSalt(){
		return this.salt;
	}
	
	public void setSalt(byte[] salt){
		this.salt = salt;
	}
	
}
