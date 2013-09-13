/**
 * @author VerpHen
 * @date 2013年9月13日  下午1:42:31
 */

package test;

import org.eclipse.osgi.util.NLS;

public class TestProperties {
	public static String platform;

	static {
		NLS.initializeMessages("test.properties", TestProperties.class);
	}

	public static void main(String[] args) {

		System.out.println(platform);
	}

}
