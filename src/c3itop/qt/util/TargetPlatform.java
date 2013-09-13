/**
 * @author VerpHen
 * @date 2013年9月12日  下午3:06:24
 */

package c3itop.qt.util;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*Target Platform selection class*/
public class TargetPlatform {

	private String sdkPath = "E:\\qt-sdk-4.8.3";
	public String[] fatherPlatform = null;
	public String[] childPlatform = null;
	public Map<String, String[]> map = null;

	public String[] delArray = { "common", "features" };

	public TargetPlatform() {
		init();
	}

	/* Init father and child target platform */
	public void init() {

		fatherPlatform = getDirList(sdkPath + "\\qmake");
		map = new HashMap<String, String[]>();

		for (String str : fatherPlatform) {
			// TargetPlatformMemory.path = "qmake\\" + str + "\\mkspecs";
			String childPath = sdkPath + "\\qmake\\" + str + "\\mkspecs";

			if ("win32".equals(str)) {
				childPlatform = arrContrast(getDirList(childPath), delArray);
			} else if ("linux".equals(str)) {
				childPlatform = arrContrast(getDirList(childPath), delArray);
			} else if ("vxworks".equals(str)) {
				childPlatform = arrContrast(getDirList(childPath), delArray);
			}
			map.put(str, childPlatform);
		}
	}

	/* 返回指定目录下的文件及文件夹列表 */
	public String[] getDirList(String path) {
		File file = new File(path);
		String[] directory = file.list();
		if (directory == null) {
			throw new RuntimeException("没有可用的目标编译平台");
		}
		return directory;
	}

	/**
	 * 除去数组arr1中包含数组arr2中的元素
	 * 
	 * @param arr1
	 *            目标数组
	 * @param arr2
	 *            待除去的数组
	 * @return 操作完成剩余元素的数组
	 */
	private String[] arrContrast(String[] arr1, String[] arr2) {
		List<String> list = new LinkedList<String>();
		for (String str : arr1) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : arr2) {
			if (list.contains(str)) {
				list.remove(str);
			}
		}
		String[] result = {};
		return list.toArray(result);
	}

	public String getCurrentOS() {

		String os = System.getProperty("os.name").toLowerCase();

		if (!"".equals(os) && os != null) {
			if (os.indexOf("windows") > -1) {
				return "win32";
			} else if (os.indexOf("linux") > -1) {
				return "linux";
			} else if (os.indexOf("vxworks") > -1) {
				return "vxworks";
			}
		}
		throw new RuntimeException("不支持当前操作系统");
	}
}
