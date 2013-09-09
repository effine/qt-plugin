/**
 * @author VerpHen
 * @date 2013年9月5日  下午2:29:21
 */

package test;

import org.eclipse.jface.wizard.Wizard;

public class Demo extends Wizard {

	@Override
	public boolean performFinish() {

		System.out.println("---------------------------");

		return false;
	}

}
