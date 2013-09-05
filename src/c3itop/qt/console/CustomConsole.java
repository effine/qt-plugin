/**
 * @author VerpHen
 * @date 2013年8月27日  上午10:05:43
 */

package c3itop.qt.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class CustomConsole {
	public static String consoleInfo = ""; // 存储出现在console面板的信息

	private CustomConsole() {
	}

	public static void getConsole() {
		MessageConsole mc = new MessageConsole("c3itop console", null);

		/* 通过ConsolePlugin获得Console的管理器 */
		IConsoleManager cmanager = ConsolePlugin.getDefault()
				.getConsoleManager();

		/* 添加console到console管理器 */
		cmanager.addConsoles(new IConsole[] { mc });

		/* 新建一个MessageConsoleStream，用于接收需要显示的信息 */
		MessageConsoleStream consoleStream = mc.newMessageStream();
		/* 设置输出的字符编码 */
		consoleStream.setEncoding("utf-8");

		/* 运行的时候，如console视图没有打开则打开Console视图 */
		cmanager.showConsoleView(mc);

		/* 打印信息 */
		consoleStream.println(consoleInfo);
	}
}
