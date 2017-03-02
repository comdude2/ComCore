package net.comdude2.plugins.minecraftcore.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import net.comdude2.plugins.minecraftcore.encryption.AES;
import net.comdude2.plugins.minecraftcore.encryption.EncryptedString;
import net.comdude2.plugins.minecraftcore.util.ObjectManager;

public class TerminalLogin {
	
	/*
	 * WARNING: This is actually not very secure due to sending the salt across an unencrypted connection (Can be brute-forced by cycling strings etc)
	 * 
	 * This works by having the client send the username they're logging in with, the server then finds the specified user and loads their salt into an AES object
	 * which uses the user's password hash as the password, the server then sends the salt to the client over the connection. The client initiates their AES object
	 * with their password hash (provided by the actual user) and the salt provided by the server, if a test string can be decrypted by the server then the login is
	 * successful, every time the client tries to interface with the server, the server should check if the client's login credentials still exist and if the password
	 * hash is still the same else it ends the connection.
	 */
	
	private static final String SEPARATOR = "::##SEP##::";
	
	/*
	 * Objects
	 */
	
	private TerminalConnection tc = null;
	private Socket connection = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private AES encryptor = null;
	
	/*
	 * User data
	 */
	
	private String address = null;
	
	/*
	 * Status
	 */
	
	private int login_level = 0;
	private byte[] iv = null;
	
	public TerminalLogin(String address, TerminalConnection tc) throws Exception{
		this.tc = tc;
		this.address = address;
		this.connection = tc.connection;
		this.in = tc.in;
		this.out = tc.out;
		//this.encryptor = new AES(AES.generateString(), 128);
		//this.encryptor.createSecretKey();
		this.exchangeKey(null);
		while(connection.isConnected()){
			String line = in.readLine();
			if (line == null){connection.close();return;}
			this.exchangeKey(line);
		}
	}
	
	public void exchangeKey(String s) throws Exception{
		if (login_level == 0){
			out.println("USER");
			out.flush();
			login_level = 1;
		}else if (login_level == 1){
			if (s != null){
				if (tc.tcm == null){System.out.println("pewp");}
				TerminalCredentials creds = tc.tcm.getCredentials(s);
				if (creds == null){
					out.println("Unknown user.");
					out.flush();
				}else{
					try{
						this.encryptor = new AES(creds.getPasswordHash(), creds.getSalt(), 128);
						this.encryptor.createSecretKey();
						out.println("SALT");
						out.flush();
						tc.connection.getOutputStream().write(creds.getSalt());
						tc.connection.getOutputStream().flush();
						login_level = 2;
					}catch(Exception e){out.println("Internal Server error.");out.flush();e.printStackTrace();connection.close();return;}
				}
			}else{
				out.println("USER");
				out.flush();
			}
		}else if (login_level == 2){
			
		}
	}
	
	
	
	/*
	 * Communication
	 */
	
}
