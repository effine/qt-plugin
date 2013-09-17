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

import c3itop.qt.console.CustomConsole;
import c3itop.qt.util.FileHandle;
import c3itop.qt.util.ProjectHandle;
import c3itop.qt.util.SetVarsAll;

public class QtBuildContextAction implements IObjectActionDelegate,
		IWorkbenchWindowActionDelegate {

	private IProject project;
	private String compilerPath = "\"C:\\Program Files\\Microsoft Visual Studio 10.0\\VC\\vcvarsall.bat\"";

	public void run(IAction action) {

		FileHandle fileHandle = new FileHandle();

		ProjectHandle projectHandle = new ProjectHandle();
		project = projectHandle.getCurrentProject();

		/* 获得当前工程的完整路径 */
		String currProjectPath = projectHandle.getProjectPath() + "/"
				+ project.getName();

		/* 创建bat文件在当前工程跟目录 */
		String batName = currProjectPath + "/temp.bat";

		/* 临时Bat文件中的内容：使用VS */
		/*
		 * String qtBatContext = qtBatPath + "/make";
		 * 
		 * String qtBatContext = "cd qtBatPath +
		 * "\n  call \"C:\\Program Files\\Microsoft Visual Studio 10.0\\VC\\vcvarsall.bat\"  \n  nmake \n  goto :eof"
		 * ;
		 */

		/* 临时Bat文件中的内容 :使用的是风河的编译器 */
		/*
		 * String qtBatContext = "cd " + currProjectPath.substring(0, 2) +
		 * "  \n " + "cd  " + currProjectPath + "\n" +
		 * "    C:/WindRiver-GPPVE-3.6-IA-Eval/wrenv.exe -p vxworks-6.6  \n  make \n  goto :eof"
		 * ;
		 */
		/* make命令的使用 */
		/* String s[] = { "make", "-C", qtBatPath }; */

		/* 执行nmake命令 */
		String batContext = "cd " + currProjectPath.substring(0, 2) + "\r\n"
				+ "cd " + currProjectPath + "\r\n" + "call " + compilerPath
				+ "\r\n nmake";

		/* 获得JVM的运行环境 */
		Runtime runtime = Runtime.getRuntime();

		/* Java新建一个bat文件，写入需要执行的命令 */
		// new FileHandle().createFileForJava(batName, batContext);
		try {

			/* 设置WIN32_VS10的环境变量数组 */
			String WIN32_VS10 = SetVarsAll.WIN32_VS10;
			String[] setPath = { "path =" + WIN32_VS10 };

			/* 设置进程的工作目录 */
			File path = new File(currProjectPath);

			Process proc = runtime.exec("nmake", setPath, path);

			/* 使用exec（）方法的参数设置在指定目录下运行 */
			// Process proc = runtime.exec(qtBatName, null, new
			// File("qtBatPath"));

			InputStream ips = proc.getInputStream();
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			while ((br.readLine()) != null) {
				CustomConsole.printConsole(br.readLine());
			}

			if (proc.waitFor() != 0) {
				if (proc.exitValue() == 1)
					// prop.exitValue()表示退出返回值;(0:表示正常结束，1：非正常结束)
					CustomConsole.printConsole("命令执行失败!");
			}
			br.close();
			ips.close();

			/* Java删除暂存temp.bat文件 */
			// fileHandle.delBatFile(file);

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
