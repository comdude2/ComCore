package net.comdude2.plugins.minecraftcore.encryption;

public final class CaesarCipher {
	
	/**
	 * Shift the specified String's characters by the offset parameter
	 * @param plain
	 * @param offset
	 * @return String
	 */
	public static String encrypt(String plain, int offset){
		offset = offset % 26 + 26;
		StringBuilder encoded = new StringBuilder();
		for (char i : plain.toCharArray()){
			if (Character.isLetter(i)){
				if (Character.isUpperCase(i)){
					encoded.append((char) ('A' + (i - 'A' + offset) % 26));
				}else{
					encoded.append((char) ('a' + (i - 'a' + offset) % 26));
				}
			}else{
				encoded.append(i);
			}
		}
		return encoded.toString();
	}
	
	/**
	 * Shift the specified String's characters by the offset parameter
	 * @param cyphertext
	 * @param offset
	 * @return String
	 */
	public static String decrypt(String cyphertext, int offset){
		return encrypt(cyphertext, 26 - offset);
	}
	
}
