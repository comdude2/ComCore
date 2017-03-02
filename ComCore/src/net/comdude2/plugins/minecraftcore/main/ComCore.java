package net.comdude2.plugins.minecraftcore.main;

import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import net.comdude2.plugins.minecraftcore.encryption.AES;
import net.comdude2.plugins.minecraftcore.encryption.Base64Tools;
import net.comdude2.plugins.minecraftcore.encryption.EncryptedString;
import net.comdude2.plugins.minecraftcore.net.ConnectionManager;
import net.comdude2.plugins.minecraftcore.net.TerminalCore;
import net.comdude2.plugins.minecraftcore.util.ObjectManager;

import org.bukkit.plugin.java.JavaPlugin;

public class ComCore extends JavaPlugin{
	
	/*
	 * System Variables
	 */
	
	protected boolean loaded_before = false;
	protected boolean run_by_server = false;
	
	/*
	 * Plugin Variables
	 */
	
	public static ComCore inst = null;
	
	/*
	 * Plugin Objects
	 */
	
	private Listeners listeners = null;
	private ConnectionManager cm = null;
	
	/*
	 * Methods
	 */
	
	public ComCore(){
		inst = this;
	}
	
	public static ComCore getInstance(){
		return inst;
	}
	
	public static void main(String[] args) throws Exception{
		File salt = new File("E:/test.salt");
		if (!salt.exists()){
			AES aes = new AES("pieisnice", 128);
			ObjectManager.writeObject(salt, aes.getSalt());
		}
		TerminalCore tc = new TerminalCore("localhost", 4043);
		tc.start();
		System.out.println("Running...");
	}
	
	public void onEnable(){
		this.run_by_server = true;
		if (this.loaded_before){this.onReload();}else{
			this.getLogger().info(this.getName() + " version: " + this.getDescription().getVersion() + " enabling...");
			
			/** Method calls **/
			
			this.listeners = new Listeners(this);
			this.listeners.register();
			this.cm = new ConnectionManager(this);
			
			/** End of Method calls **/
			
			this.loaded_before = true;
			this.getLogger().info(this.getName() + " version: " + this.getDescription().getVersion() + " enabled!");
		}
	}
	
	public void onDisable(){
		this.getLogger().info(this.getName() + " version: " + this.getDescription().getVersion() + " disabling...");
		
		/** Method calls **/
		
		this.listeners.unregister();
		
		/** End of Method calls **/
		
		this.getLogger().info(this.getName() + " version: " + this.getDescription().getVersion() + " disabled!");
	}
	
	public void onReload(){
		
		/** Method calls **/
		
		this.listeners.register();
		
		/** End of Method calls **/
		
	}
	
	/*
	 * Get and Set
	 */
	
	public Listeners getListeners(){
		return this.listeners;
	}
	
	public ConnectionManager getConnectionManager(){
		return this.cm;
	}
	
}
