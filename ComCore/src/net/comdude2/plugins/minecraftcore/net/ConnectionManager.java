package net.comdude2.plugins.minecraftcore.net;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import net.comdude2.plugins.minecraftcore.listeners.ComLoginEvent;
import net.comdude2.plugins.minecraftcore.main.ComCore;
import net.comdude2.plugins.minecraftcore.util.ObjectManager;

public class ConnectionManager {
	
	//Change to config value
	private final boolean ALLOW_TERMINAL = true;
	private final String address = "127.0.0.1";
	private final int PORT = 28953;
	
	private ComCore core = null;
	private String connectionsFolderPath = null;
	private TerminalCore terminal = null;
	
	private ConcurrentLinkedQueue <Connection> connections = new ConcurrentLinkedQueue <Connection> ();
	
	/* Ban Variables */
	private ConcurrentLinkedQueue <UUID> banned_players = new ConcurrentLinkedQueue <UUID> ();
	private ConcurrentLinkedQueue <String> banned_addresses = new ConcurrentLinkedQueue <String> ();
	
	public ConnectionManager(ComCore core){
		this.core = core;
		this.connectionsFolderPath = core.getDataFolder() + "/connections/";
		File f = new File(this.connectionsFolderPath);
		if (!f.exists()){f.mkdir();}
		
		if (this.ALLOW_TERMINAL){this.launchTerminalConnection(address, PORT);}
	}
	
	/*
	 * Save and Load
	 */
	
	public boolean save(){
		boolean error = false;
		try{ObjectManager.writeObject(new File(this.connectionsFolderPath + "banned_players.obj"), this.banned_players);}catch(Exception e){error = true;}
		try{ObjectManager.writeObject(new File(this.connectionsFolderPath + "banned_addresses.obj"), this.banned_addresses);}catch(Exception e){error = true;}
		return !error;
	}
	
	public boolean load(){
		return false;
	}
	
	/*
	 * Methods
	 */
	
	public boolean playerPreConnectionAttempt(AsyncPlayerPreLoginEvent event){
		final Connection c = new Connection(event.getUniqueId(), event.getAddress().getHostAddress().toString(), event.getAddress().getHostName());
		ComLoginEvent cle = new ComLoginEvent(c);
		this.core.getServer().getPluginManager().callEvent(cle);
		if (!cle.isConnectionAllowed()){
			event.disallow(Result.KICK_OTHER, cle.getReason());
		}
		return false;
	}
	
	public boolean isAllowedToConnect(UUID playerId, String address, String hostName){
		return false;
	}
	
	public boolean isBannedPlayer(UUID player){
		ConcurrentLinkedQueue <UUID> banned_players = this.banned_players;
		Iterator<UUID> it = banned_players.iterator();
		while (it.hasNext()){
			UUID banned_player = it.next();
			if (banned_player.toString().equals(player.toString())){
				return true;
			}
		}
		return false;
	}
	
	public boolean isBannedAddress(String address){
		ConcurrentLinkedQueue <String> banned_addresses = this.banned_addresses;
		Iterator<String> it = banned_addresses.iterator();
		while (it.hasNext()){
			String banned_address = it.next();
			if (banned_address.toString().equals(address.toString())){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Get and Set
	 */
	
	public ConcurrentLinkedQueue <Connection> getConnections(){
		ConcurrentLinkedQueue <Connection> cnts = connections;
		return cnts;
	}
	
	public ConcurrentLinkedQueue <UUID> getBannedPlayers(){
		ConcurrentLinkedQueue <UUID> bnd = banned_players;
		return bnd;
	}
	
	public ConcurrentLinkedQueue <String> getBannedAddresses(){
		ConcurrentLinkedQueue <String> bnd = banned_addresses;
		return bnd;
	}
	
	public TerminalCore getTerminal(){
		return this.terminal;
	}
	
	/*
	 * Private Methods
	 */
	
	private void launchTerminalConnection(String address, int port){
		try {
			this.terminal = new TerminalCore(address, port);
			this.terminal.start();
		} catch (Exception e) {
			e.printStackTrace();
			this.core.getLogger().warning("Failed to launch terminal.");
		}
	}
	
}
