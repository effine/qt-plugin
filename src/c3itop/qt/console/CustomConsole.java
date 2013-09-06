/**
 * @author VerpHen
 * @date 2013年8月27日  上午10:05:43
 */

package c3itop.qt.console;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class CustomConsole {
	public static String consoleInfo = ""; // 存储出现在console面板的信息
	private static MessageConsole mc = null;
	private static IConsoleManager cmanager = null;
	private static MessageConsoleStream consoleStream = null;

	private CustomConsole() {
	}

	public static void printConsole(String info) {

		/* 创建一个MessageConsole */
		if (mc == null) {
			mc = new MessageConsole("c3itop console", null);
			FontData fontData = new FontData();
			fontData.setStyle(SWT.NULL);
			Font font = new Font(Display.getDefault(), fontData);
			mc.setFont(font);
		}

		/* 通过ConsolePlugin获得Console的管理器 */
		if (cmanager == null) {
			cmanager = ConsolePlugin.getDefault().getConsoleManager();
		}

		/* 添加console到console管理器 */
		cmanager.addConsoles(new IConsole[] { mc });

		/* 新建一个MessageConsoleStream，用于接收需要显示的信息 */
		if (consoleStream == null) {
			consoleStream = mc.newMessageStream();
		}
		/* 设置输出的字符编码 */
		consoleStream.setEncoding("utf-8");
		consoleStream.setFontStyle(11);

		/* 运行的时候，如console视图没有打开则打开Console视图 */
		cmanager.showConsoleView(mc);

		/* 打印信息 */
		consoleStream.println(info);
	}

	/* 清空console message */
	public static void cleanConsoleInfo() {
		mc.clearConsole();
	}

}
