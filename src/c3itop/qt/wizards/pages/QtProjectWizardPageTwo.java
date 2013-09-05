/**
 * @author VerpHen
 * @date 2013年8月29日  上午10:31:01
 */

package c3itop.qt.wizards.pages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class QtProjectWizardPageTwo extends WizardPage {

	private Text proName;
	private Text cppName;
	private QtProjectWizardPageOne onePage;

	public QtProjectWizardPageTwo(ISelection selection) {
		super("Wizardpage");
		setTitle("Qt Code Project");
		setDescription("show project default file names .");
		onePage = new QtProjectWizardPageOne(selection);
	}

	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 26, 105, 24);
		lblNewLabel.setText("Source Filename：");

		// cpp file name
		cppName = new Text(container, SWT.BORDER);
		cppName.setBounds(121, 23, 376, 27);

		// pro file name
		Label lblPro = new Label(container, SWT.NONE);
		lblPro.setBounds(10, 65, 105, 17);
		lblPro.setText("Pro Filename：");

		proName = new Text(container, SWT.BORDER);
		proName.setBounds(121, 62, 376, 23);

		proName.setText(onePage.getProjectName() + ".pro");
		cppName.setText(onePage.getProjectName() + ".cpp");
	}

	public String getCppName() {

		String cname = cppName.getText().trim();
		if (null == cname || "".equals(cname))
			return null;
		return cname;
	}

	public String getProName() {
		String pname = proName.getText().trim();
		if (null == pname || "".equals(pname))
			return null;
		return pname;
	}
}
