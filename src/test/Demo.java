/**
 * @author VerpHen
 * @date 2013年9月5日  下午2:29:21
 */

package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Demo {

	public static void main(String[] args) {
		new Demo()
				.readFileByLines("F:/runtime-EclipseApplication/hello/platform");
	}

	public void test() {
		InputStream is = this.getClass().getResourceAsStream(
				"F:/runtime-EclipseApplication/hello/platform");

		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[4096];

		try {
			for (int n; (n = is.read(b)) != -1;) {
				sb.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("----------------" + sb.toString());
	}

	public void test2() {
		InputStream is = this.getClass().getResourceAsStream(
				"F:/runtime-EclipseApplication/hello/hello.cpp");

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------" + line);
	}

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