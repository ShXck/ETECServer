package org.etec.utilities;

import java.security.SecureRandom;

public class RandomIntegerGenerator {
	
	private static SecureRandom rd = new SecureRandom();
	
	/**
	 * @param lenght el largo del id.
	 * @return un id aleatorio.
	 */
	public static String generate_new_id(int lenght){
		
		final String OPTIONS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		StringBuilder sb = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++){
			sb.append(OPTIONS.charAt(rd.nextInt(OPTIONS.length())));
		}
		return sb.toString();	
	}
	
	/**
	 * @param lenght el largo del id.
	 * @return un id aleatorio.
	 */
	public static int generate_new_key(int lenght){
		
		final String OPTIONS = "0123456789";
		
		StringBuilder sb = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++){
			sb.append(OPTIONS.charAt(rd.nextInt(OPTIONS.length())));
		}
		return Integer.parseInt(sb.toString());	
	}
	
	/**
	 * Convierte un arreglo de strings a uno de enteros.
	 * @param s el arreglo de strings.
	 * @return el arreglo con enteros.
	 */
	public static int[] to_int(String[] s){
		
		int[] result = new int[s.length];
		
		for(int i = 0; i < s.length; i++){
			result[i] = Integer.parseInt(s[i]);
		}
		return result;
	}
	
}
