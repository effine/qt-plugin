/**
 * @author VerpHen
 * @date 2013年8月29日  上午10:31:01
 */

package c3itop.qt.wizards.pages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class QtProjectFileWizardPage extends WizardPage {

	private Text proName;
	private Text cppName;
	private Text uiName;

	public QtProjectFileWizardPage(ISelection selection) {
		super("Wizardpage");
		setTitle("Qt Code Project");
		setDescription("show project default file names .");
		new QtProjectNameWizardPage(selection);
	}

	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 26, 105, 24);
		lblNewLabel.setText("Source Filename：");

		// cpp file name
		cppName = new Text(container, SWT.BORDER);
		cppName.setBounds(121, 23, 358, 27);

		// pro file name
		Label lblPro = new Label(container, SWT.NONE);
		lblPro.setBounds(10, 65, 105, 17);
		lblPro.setText("Pro Filename：");

		proName = new Text(container, SWT.BORDER);
		proName.setBounds(121, 62, 358, 23);

		Label lblUiFilename = new Label(container, SWT.NONE);
		lblUiFilename.setBounds(10, 101, 105, 17);
		lblUiFilename.setText("UI Filename：");
		uiName = new Text(container, SWT.BORDER);
		uiName.setBounds(121, 95, 358, 23);
	}

	/**
	 * 获得本向导页属性框值
	 * 
	 * @param suffix
	 *            具体的哪一属性框值，如cpp、pro
	 * @return
	 */
	public String getWizardFieldName(String suffix) {

		String cname = cppName.getText().trim();
		String pname = proName.getText().trim();
		String uname = uiName.getText().trim();

		if ("cpp".equals(suffix)) {
			if (null == cname || "".equals(cname))
				return null;
			return cname;
		}
		if ("pro".equals(suffix)) {

			if (null == pname || "".equals(pname))
				return null;
			return pname;
		}

		if ("ui".equals(suffix)) {

			if (null == pname || "".equals(uname))
				return null;
			return uname;
		}

		return null;
	}

	/* 设置cppName的值 */
	public void setFileName(String name) {
		cppName.setText(name + ".cpp");
		proName.setText(name + ".pro");
		uiName.setText(name + ".ui");
	}
}
