/**
 * @author VerpHen
 * @date 2013年8月22日  上午9:33:59
 */

package c3itop.qt.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

/*针对本地文件的处理类*/
public class FileHandle {

	/* create file with not use Java package */
	public void creadFile(String filePath, String fileName, String suffix) {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(filePath));

		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));

		/* 截取字符串：".".equals(suffix.substring(0, 1)) */
		try {
			InputStream stream = openContentStream(suffix);

			/* 如果文件存在，则往文件中写入内容；如不存在则创建文件，并写入内容 */
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

	/* 不使用Java包读取文件 */
	public String readFile(String filePath) {
		try {
			InputStream is = this.getClass().getResourceAsStream(filePath);
			StringBuffer sb = new StringBuffer();
			byte[] b = new byte[4096];

			for (int n; (n = is.read(b)) != -1;) {
				sb.append(new String(b, 0, n));
			}
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

		/*
		 * 方法一：但是在InputStreamReader时有错
		 * 
		 * InputStream is = this.getClass().getResourceAsStream(filePath);
		 * InputStreamReader isr = new InputStreamReader(is); BufferedReader br
		 * = new BufferedReader(isr); StringBuffer buffer = new StringBuffer();
		 * 
		 * String line = ""; try { while ((line = br.readLine()) != null) {
		 * buffer.append(line); }
		 * 
		 * } catch (IOException e) { e.printStackTrace(); } return
		 * buffer.toString();
		 */
	}

	/**
	 * initialize file contents with template file
	 * 
	 * @param suffix
	 *            后缀名
	 */
	public InputStream openContentStream(String suffix) {

		if (".cpp".equals(suffix)) {
			return this.getClass().getResourceAsStream(
					"/c3itop/qt/template/Cpp.template");
		} else if (".pro".equals(suffix)) {
			return this.getClass().getResourceAsStream(
					"/c3itop/qt/template/Pro.template");
		} else if (".ui".equals(suffix)) {
			return this.getClass().getResourceAsStream(
					"/c3itop/qt/template/UI.template");
		} else {

			/* 将字符串作为一个InputStream返回 */
			return new ByteArrayInputStream(suffix.getBytes());
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
	public String readFileByLines(String file) {
		BufferedReader reader = null;
		String context = "";
		String temp = "";
		try {
			reader = new BufferedReader(new FileReader(file));

			/* 一次读入一行，直到读入null为文件结束 */
			while ((temp = reader.readLine()) != null) {
				context += temp;
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
		return context;
	}

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
}
