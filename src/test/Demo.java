/**
 * @author VerpHen
 * @date 2013年9月5日  下午2:29:21
 */

package test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Demo {
	public static void main(String[] args) {

		File file = new File("E:\\qt-sdk-4.8.3\\qmake");

		for (String s : file.list()) {
			System.out.println(s);
		}

		String os = System.getProperty("os.name");
		System.out.println(os);

		System.out.println("------------ "
				+ os.toLowerCase().indexOf("windows"));

		String[] f = { "windows", "linux", "vxworks" };

		String[] windows = { "w1", "w2", "w3" };
		String[] linux = { "l1", "l2", "l3" };
		String[] vxworks = { "v1", "v2", "v3" };

		Map map = new HashMap();
		map.put(f[0], windows);
		map.put(f[1], linux);
		map.put(f[2], vxworks);

	}
}