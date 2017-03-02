package net.comdude2.plugins.minecraftcore.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TerminalConnection extends Thread{
	
	protected Socket connection = null;
	protected boolean halt = false;
	protected String address = null;
	
	protected BufferedReader in = null;
	protected PrintWriter out = null;
	
	protected TerminalLogin login = null;
	protected TerminalCredentialsManager tcm = null;
	
	public TerminalConnection(Socket connection, TerminalCredentialsManager tcm){
		this.connection = connection;
		this.tcm = tcm;
	}
	
	@Override
	public void run(){
		address = connection.getRemoteSocketAddress().toString();
		try{connection.setSoTimeout(300000);}catch(Exception e){e.printStackTrace();try{connection.close();}catch(Exception e1){}return;}
		in = null;
		out = null;
		try {
			InputStreamReader isr = new InputStreamReader(this.connection.getInputStream());
			in = new BufferedReader(isr);
			out = new PrintWriter(this.connection.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (in == null){return;}else if (out == null){halt = true;return;}
		
		while(!halt){
			try{
				//Handle
				this.login();//This before reading input
				String line = in.readLine();
				if (line == null){halt = true;}
				if (!halt){this.interpret(line);}
				//Should I continue to loop?
				boolean stop = this.shouldStop();
				if (stop == true){
					halt = true;
				}
			}catch(Exception e){}
		}
		//Clean up connection
		try{this.connection.close();}catch(Exception e){}
		System.out.println("[ComCore] End terminal connection from: " + address);
	}
	
	public void halt(){
		this.halt = true;
	}
	
	public void forceHalt(){
		try{this.interrupt();}catch(Exception e){}
	}
	
	protected boolean shouldStop(){
		if (this.connection == null){return true;}
		if (!this.connection.isConnected()){return true;}
		if (this.connection.isClosed()){return true;}
		if (this.connection.isInputShutdown()){return true;}
		if (this.connection.isOutputShutdown()){return true;}
		return false;
	}
	
	/**
	 * Used to allow a class to extend and change the interpretation of a connection line (To change the way a connection is handled)
	 * @param connection
	 */
	protected void interpret(String line){
		if (line.equalsIgnoreCase("pie")){
			out.println("Ooohh this works!");
			out.flush();
		}else if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("close") || line.equalsIgnoreCase("disconnect")){
			out.println("Goodbye.");
			out.flush();
			try{this.connection.close();}catch(Exception e){}
			this.halt = true;
		}else{
			out.println("Unknown command.");
			out.flush();
		}
	}
	
	protected void login(){
		if (this.login == null){
			try{this.login = new TerminalLogin(this.address, this);}catch(Exception e){halt = true;out.println("Failed to start encryption.");out.flush();e.printStackTrace();return;}
		}
	}
	
}
