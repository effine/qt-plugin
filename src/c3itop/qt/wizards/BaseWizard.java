/**
 * @author VerpHen
 * @date 2013年8月29日  下午1:46:44
 */

package c3itop.qt.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.RGB;

public class BaseWizard extends Wizard {

	public BaseWizard() {

		/* 设置窗口标题 */
		setWindowTitle("New Qt Code Project");

		/* 制定标题栏用什么颜色 */
		setTitleBarColor(new RGB(78, 45, 45));

		/* 用于提供显示在向导的所有页面右上方的图片 */
		// setDefaultPageImageDescriptor(ImageDescriptor image);
	}

	@Override
	public boolean performFinish() {
		return false;
	}

}
