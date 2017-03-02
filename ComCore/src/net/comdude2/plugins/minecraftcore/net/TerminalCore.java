package net.comdude2.plugins.minecraftcore.net;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TerminalCore extends Thread{
	
	private InetSocketAddress serverAddress = null;
	private ServerSocketChannel socketChannel = null;
	private ServerSocket socket = null;
	private Selector selector = null;
	private TerminalCredentialsManager tcm = null;
	
	private boolean halt = false;
	
	protected ConcurrentLinkedQueue <TerminalConnection> connections = new ConcurrentLinkedQueue <TerminalConnection> ();
	
	public TerminalCore(String address, int port) throws Exception{
		this.serverAddress = new InetSocketAddress(address, port);
		tcm = new TerminalCredentialsManager();
		boolean created = tcm.createNewCredentials("matt", "pieisnice");
		//Get channel
		socketChannel = ServerSocketChannel.open();
		socketChannel.configureBlocking(false);
		socket = socketChannel.socket();
		socket.bind(serverAddress);
		selector = Selector.open();
	}
	
	@Override
	public void run(){
		try{
			socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		}catch(Exception e){e.printStackTrace();return;}
		if (selector == null){return;}
		while(!halt){
			try{
				//Self explanatory
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> it = keys.iterator();
				
				//Loop through all connection attempts
				while(it.hasNext()){
					SelectionKey selKey = it.next();
					it.remove();
					if (selKey.isAcceptable()){
						ServerSocketChannel selChannel = (ServerSocketChannel)selKey.channel();
						ServerSocket selSocket = selChannel.socket();
						Socket connection = selSocket.accept();
						System.out.println("[ComCore] New terminal connection from: " + connection.getRemoteSocketAddress().toString());
						this.handleConnection(connection);
					}
				}
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Used to allow a class to extend and change the connection object (To change the way a connection is handled)
	 * @param connection
	 */
	public void handleConnection(Socket connection){
		TerminalConnection ServerCon = new TerminalConnection(connection, tcm);
		ServerCon.start();
		connections.add(ServerCon);
	}
	
	public void halt(){
		this.halt = true;
	}
	
}
