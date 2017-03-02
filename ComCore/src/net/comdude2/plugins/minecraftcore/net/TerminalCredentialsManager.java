package net.comdude2.plugins.minecraftcore.net;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.comdude2.plugins.minecraftcore.encryption.AES;
import net.comdude2.plugins.minecraftcore.encryption.MD5;
import net.comdude2.plugins.minecraftcore.util.ObjectManager;

public class TerminalCredentialsManager {
	
	private String path = null;
	private ConcurrentLinkedQueue <TerminalCredentials> credentials = new ConcurrentLinkedQueue <TerminalCredentials> ();
	
	public TerminalCredentialsManager(){
		File f = new File("");
		path = f.getAbsolutePath() + "/" + "plugins/ComCore/terminal/";
		f = new File(path);
		System.out.println("Path: " + path);
		if (!f.exists()){f.mkdirs();
			if (!f.exists()){
				System.out.println("Failed to make directory.");
				return;
			}
		}else{
			load();
		}
	}
	
	public synchronized void load(){
		File[] list = new File(path).listFiles();
		if (list != null){
			if (list.length > 0){
				for (File f : list){
					if (f.getName().endsWith(".user")){
						TerminalCredentials tc = null;
						try{tc = (TerminalCredentials)ObjectManager.readObject(f).readObject();}catch(Exception e){System.out.println("Failed to load credentials: " + f.getName());}
						if (tc != null){credentials.add(tc);}
					}
				}
			}
		}
	}
	
	public synchronized void save(){
		Iterator<TerminalCredentials> it = this.credentials.iterator();
		while(it.hasNext()){
			TerminalCredentials tc = it.next();
			try{ObjectManager.writeObject(new File(path + tc.getUsername() + ".user"), tc);}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public synchronized boolean createNewCredentials(String username, String password){
		String passwordHash = null;
		try{passwordHash = MD5.getMD5Checksum(new ByteArrayInputStream(password.getBytes(StandardCharsets.UTF_8)), 4096);}catch(Exception e){}
		if (passwordHash == null){return false;}
		AES aes = new AES(passwordHash, 128);
		File f = new File(path + username.toLowerCase() + ".user");
		if (f.exists()){return false;}
		TerminalCredentials tc = new TerminalCredentials(username.toLowerCase(), passwordHash, aes.getSalt());
		try{ObjectManager.writeObject(f, tc);credentials.add(tc);return true;}catch(Exception e){e.printStackTrace();return false;}
	}
	
	public synchronized TerminalCredentials getCredentials(String username){
		Iterator<TerminalCredentials> it = this.credentials.iterator();
		while(it.hasNext()){
			TerminalCredentials tc = it.next();
			System.out.println("Credentials: " + tc.getUsername());
			if (tc.getUsername().equalsIgnoreCase(username)){
				return tc;
			}
		}
		return null;
	}
	
	public synchronized ConcurrentLinkedQueue <TerminalCredentials> getCredentials(){
		return this.credentials;
	}
	
}
