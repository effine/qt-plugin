/**
 * @author VerpHen
 * @date 2013年8月22日  上午9:33:59
 */

package c3itop.qt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * 针对本地文件的处理类
 */
public class FileHandle {
	private IWizardContainer container = null;

	/**
	 * 新建文件
	 * 
	 * @param pathNmae
	 *            文件所在路径及文件名
	 * @param context
	 *            往文件中写入的内容
	 */
	public File createBatFile(String pathNmae, String context) {
		File file = new File(pathNmae);
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(context);
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return file;
	}

	/**
	 * 删除文件
	 * 
	 * @param pathName
	 *            删除文件的完整文件名（包含路径）
	 */
	public void delBatFile(File file) {
		if (file.isFile() && file.exists()) {
			if (file.delete())
				System.out.println("Bat文件删除成功");
			else
				System.out.println("Bat文件删除失败");
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param file
	 *            需要读取的文件名
	 */
	public void readFileByLines(String file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/** create file with not use Java package */
	public void creadFile(String filePath, String fileName,
			IProgressMonitor monitor) {
		monitor.beginTask("Creating " + fileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(filePath));
		/*
		 * if (!resource.exists() || !(resource instanceof IContainer)) { throw
		 * CoreException(filePath + "\\" + fileName + "  does not exist."); }
		 */
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		try {
			InputStream stream = openContentStream();
			if (file.exists()) {
				file.setContents(stream, true, true, monitor);
			} else {
				file.create(stream, true, monitor);
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}

	public Shell getShell() {
		if (container == null)
			return null;
		return container.getShell();
	}

	/** initialize file contents with template file */
	public InputStream openContentStream() {
		return this.getClass().getResourceAsStream(
				"/c3itop/qt/template/CppTemplate.cpp");
	}
}
