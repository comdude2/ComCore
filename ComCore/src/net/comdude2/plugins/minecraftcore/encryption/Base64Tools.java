package net.comdude2.plugins.minecraftcore.encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Base64Tools {
	
	public static String encode(byte[] value) throws Exception {
		return Base64.getEncoder().encodeToString(value);
	}
	
	public static byte[] decode(String value) throws Exception {
		return Base64.getDecoder().decode(value);  // Basic Base64 decoding
	}
	
	 /** Read the object from Base64 string. */
	   public static Object fromString( String s ) throws IOException, ClassNotFoundException {
	        byte [] data = Base64.getDecoder().decode( s );
	        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
	        Object o  = ois.readObject();
	        ois.close();
	        return o;
	   }

	    /** Write the object to a Base64 string. */
	    public static String toString( Serializable o ) throws IOException {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream( baos );
	        oos.writeObject( o );
	        oos.close();
	        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
	    }
	
}
