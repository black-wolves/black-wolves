/**
 * 
 */
package com.blackwolves.mail.util;

import java.util.Comparator;

/**
 * @author gastondapice
 *
 */
public class SeedComparator<T> implements Comparator<String[]> {

	@Override
	public int compare(String[] seed1, String[] seed2) {
		if(Integer.valueOf(seed1[3]) < Integer.valueOf(seed2[3])){
			return -1;
		}else if(Integer.valueOf(seed1[3]) > Integer.valueOf(seed2[3])){
			return 1;
		}
		return 0;
	}

}
