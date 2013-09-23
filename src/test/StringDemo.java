/**
 * @author VerpHen
 * @date 2013年9月18日  下午3:49:31
 */

package test;

public class StringDemo {

	public static void main(String[] args) {

		String s = "hello";
		s = "java";
		String s1 = "java";
		String s2 = new String("java");

		System.out.println(s == s1);
		System.out.println(s == s2);
		System.out.println(s1 == s2);
		System.out.println("--------------------------");

		String t = "hello,java";
		String t1 = "hello,";
		String t2 = "java";

		String t3 = t1 + t2;
		System.out.println(t == t3);
		System.out.println(t.equals(t3));

	}

}
