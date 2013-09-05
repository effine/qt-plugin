/**
 * @author VerpHen
 * @date 2013年8月22日  下午5:38:20
 */

package c3itop.qt.context.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import c3itop.qt.console.ConsoleMessage;
import c3itop.qt.util.FileHandle;
import c3itop.qt.util.ProjectHandle;

public class QtNmakeContextAction implements IObjectActionDelegate,
		IWorkbenchWindowActionDelegate {
	private ISelection selection;

	private IProject project;

	public void run(IAction action) {

		ProjectHandle projectHandle = new ProjectHandle();
		FileHandle fileHandle = new FileHandle();

		project = projectHandle.getCurrentProject();

		String qtBatPath = projectHandle.getProjectPath() + "/"
				+ project.getName();

		String qtBatName = qtBatPath + "/temp.bat"; // 临时的Bat文件

		/* 临时Bat文件中的内容 */
		String qtBatContext = "f: \n cd "
				+ qtBatPath
				+ "\n  call \"C:\\Program Files\\Microsoft Visual Studio 10.0\\VC\\vcvarsall.bat\"  \n  nmake \n  goto :eof";

		Runtime runtime = Runtime.getRuntime(); // 获得JVM的运行环境

		MessageConsole mc = new MessageConsole("c3itop console", null);
		ConsolePlugin.getDefault().getConsoleManager()
				.addConsoles(new IConsole[] { mc });
		MessageConsoleStream consoleStream = mc.newMessageStream();
		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(mc);

		/* 使用dos命令echo创建临时bat文件 */
		/*
		 * try { runtime.exec("cmd /c  echo  " + qtBatContext + "> temp.bat"); }
		 * catch (IOException e1) { e1.printStackTrace(); }
		 */

		/* Java新建一个bat文件，写入需要执行的命令 */
		File file = new FileHandle().createBatFile(qtBatName, qtBatContext);
		try {
			Process proc = runtime.exec(qtBatName);

			/* 使用exec（）方法的参数设置在指定目录下运行 */
			// Process proc = runtime.exec(qtBatName, null, new
			// File("qtBatPath"));

			InputStream ips = proc.getInputStream();
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);

			String line = null;
			while ((line = br.readLine()) != null) {
				ConsoleMessage.consoleInfo += "\n" + br.readLine();
			}

			if (proc.waitFor() != 0) {
				if (proc.exitValue() == 1)
					// prop.exitValue()表示退出返回值;(0:表示正常结束，1：非正常结束)
					ConsoleMessage.consoleInfo += "\n" + "命令执行失败!";
			}
			consoleStream.println(ConsoleMessage.consoleInfo); // 将得到的信息输出到Console
			br.close();
			ips.close();

			/* Java删除暂存temp.bat文件 */
			fileHandle.delBatFile(file);

			// fileHandle.delBatFile(new File(qtBatName)); //dos测试删除temp.bat文件
			project.refreshLocal(IResource.DEPTH_INFINITE, null);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}
}
