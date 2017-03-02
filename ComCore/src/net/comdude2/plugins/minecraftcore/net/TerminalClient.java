package net.comdude2.plugins.minecraftcore.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TerminalClient extends Thread{
	
	protected String address = null;
	protected int port = -1;
	
	protected Socket connection = null;
	
	protected boolean halt = false;
	
	protected BufferedReader in = null;
	protected PrintWriter out = null;
	
	public TerminalClient(String address, int port){
		this.address = address;
		this.port = port;
	}
	
	@Override
	public void run(){
		try {
			connection = new Socket(address, port);
			in = null;
			out = null;
			try {
				InputStreamReader isr = new InputStreamReader(this.connection.getInputStream());
				in = new BufferedReader(isr);
				out = new PrintWriter(this.connection.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (in == null){return;}else if (out == null){return;}
			
			while(!halt){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void halt(){
		this.halt = true;
	}
	
}
