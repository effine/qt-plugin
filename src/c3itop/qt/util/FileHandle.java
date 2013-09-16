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
import org.eclipse.core.runtime.Path;

/*针对本地文件的处理类*/
public class FileHandle {

	/* 使用java包创建文件，传入参数：完整文件名、文件内容 */
	public File createFileForJava(String pathNmae, String context) {
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

	/* create file with not use Java package */
	public void creadFile(String filePath, String fileName, String suffix) {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(filePath));

		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		try {
			InputStream stream = openContentStream(suffix);
			if (file.exists()) {
				file.setContents(stream, true, true, null);
			} else {
				file.create(stream, true, null);
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * initialize file contents with template file
	 * 
	 * @param suffix
	 *            后缀名
	 */
	public InputStream openContentStream(String suffix) {

		if ("cpp".equals(suffix)) {
			return this.getClass().getResourceAsStream(
					"/c3itop/qt/template/Cpp.template");
		} else if ("pro".equals(suffix)) {
			return this.getClass().getResourceAsStream(
					"/c3itop/qt/template/Pro.template");
		} else if ("ui".equals(suffix)) {
			return this.getClass().getResourceAsStream(
					"/c3itop/qt/template/UI.template");
		} else {
			return null;
		}
	}

	/* 删除文件,参数：完整文件名 */
	public void delBatFile(File file) {
		if (file.isFile() && file.exists()) {
			if (file.delete())
				System.out.println("文件删除成功");
			else
				System.out.println("文件删除失败");
		}
	}

	/**
	 * 以 "行" 为单位读取文件，常用于读面向行的格式化文件
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
}
