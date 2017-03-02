package net.comdude2.plugins.minecraftcore.encryption;

import java.io.Serializable;

public class EncryptedString implements Serializable{
	
	private static final long serialVersionUID = -546003241267601360L;
	private byte[] cipherText = null;
	private byte[] iv = null;
	
	public EncryptedString(byte[] cipherText, byte[] iv){
		this.cipherText = cipherText;
		this.iv = iv;
	}
	
	public byte[] getCipherText(){
		return this.cipherText;
	}
	
	public byte[] getIV(){
		return this.iv;
	}
	
}
