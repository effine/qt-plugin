/**
 * @author VerpHen
 * @date 2013年9月13日  上午9:50:53
 */

package c3itop.qt.util;

public class TargetPlatformMemory {

	public static String path = "";

	/* 向导页显示的目标平台 */
	public static String SelectedPlatform = "";

	// public static String compilePath = path + "\\" + currentSelectedPlatform;

	/* Configure时使用的目标平台完整路径 */
	public static String compilePlatform = "qmake\\win32\\mkspecs\\"
			+ SelectedPlatform;

}
