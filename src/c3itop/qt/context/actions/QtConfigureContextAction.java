/**
 * @author VerpHen
 * @date 2013年8月22日  下午5:38:20
 */

package c3itop.qt.context.actions;

import java.io.BufferedReader;
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

public class QtConfigureContextAction implements IObjectActionDelegate,
		IWorkbenchWindowActionDelegate {

	private IProject currProject;

	/* configure编译使用的完整路径 */
	private String compilePath;

	public void run(IAction action) {

		FileHandle fileHandle = new FileHandle();
		ProjectHandle projectHandle = new ProjectHandle();

		currProject = projectHandle.getCurrentProject();
		String projectPath = projectHandle.getProjectPath() + "/"
				+ currProject.getName();

		/* 读取到文件存储的目标平台 */
		String platform = fileHandle.readFileByLines(projectPath + "/platform");

		/* 构造出configure所需的mkspec */
		compilePath = "qmake\\win32\\mkspecs\\" + platform;

		/* configure命令数组 */
		String[] conf = { "configure", "-p", "-spec", compilePath, "-d",
				projectPath };

		/*
		 * 执行目标平台：vx660-qt483-pentium4-rtp-g++
		 * 
		 * String[] conf = { "configure", "-p", "-spec", platform, "-d",
		 * qtBatPath, "-mode", "debug", "-platform", "unix" };
		 */

		Runtime runtime = Runtime.getRuntime(); // 获得JVM的运行环境
		try {

			Process proc = runtime.exec(conf);// 另起一个进程,执行命令
			InputStream ips = proc.getInputStream(); // 获得一个输入流InputStream
			// 获得一个输入流InputSteamReader
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line = null;
			while ((line = br.readLine()) != null) {
				CustomConsole.printConsole(br.readLine());
			}

			if (proc.waitFor() != 0) {
				if (proc.exitValue() == 1) // prop.exitValue()表示退出返回值;(0:表示正常结束，1：非正常结束)
					CustomConsole.printConsole("命令执行失败!");
			}

			ips.close();
			ipsr.close();
			br.close();

			currProject.refreshLocal(IResource.DEPTH_INFINITE, null);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
