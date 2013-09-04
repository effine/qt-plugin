/**
 * @author VerpHen
 * @date 2013��8��13��  ����1:56:41
 */

package c3itop.qt.wizards.pages;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ProjectNameWizardPageBackup extends WizardPage {

	private Text projectName;
	private Button isws;
	private Text projectDir;
	private Label lblDir;
	private Button btnBrowse;

	/* 返回输入的工程名 */
	public String getProjectNmae() {
		return projectName.getText();
	}

	public ProjectNameWizardPageBackup(ISelection selection) {
		super("Wizardpage");
		setTitle("Qt Code Project");
		setDescription("Create a new Qt Code Application Project .");
	}

	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 21, 82, 24);
		lblNewLabel.setText("&Project name:");

		projectName = new Text(container, SWT.BORDER);
		projectName.setBounds(98, 18, 374, 23);

		/*
		 * 暂时不能实现：输入工程名的时候 path及时更新
		 * 
		 * projectName.addModifyListener(new ModifyListener() {
		 * 
		 * @Override public void modifyText(ModifyEvent e) {
		 * projectDir.setText(projectDir.getText() + "\\" +
		 * projectName.getText()); } });
		 */

		isws = new Button(container, SWT.CHECK);
		isws.setSelection(true);
		isws.setText("Create Project In Workspace");
		isws.setBounds(10, 60, 201, 17);

		isws.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setExternalWsEnable();
			}
		});

		lblDir = new Label(container, SWT.NONE);
		lblDir.setEnabled(false);
		lblDir.setBounds(10, 89, 58, 20);
		lblDir.setText("&Location:");

		projectDir = new Text(container, SWT.BORDER);
		projectDir.setEnabled(false);
		projectDir.setBounds(81, 89, 299, 20);
		projectDir.setText(Platform.getLocation().toOSString());

		btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.setEnabled(false);
		btnBrowse.setBounds(386, 83, 86, 28);
		btnBrowse.setText("B&rowse...");
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				DirectoryDialog dir = new DirectoryDialog(parent.getShell());
				dir.setText("Project Directory");
				dir.setFilterPath(Platform.getLocation().toOSString());
				String path = dir.open();
				if (path != null) {
					projectDir.setText(path);
				}
			}
		});
	}

	/*---------------------set Listener ---------------------------*/

	/* 返回界面填写的工程名 */
	public String getProjectName() {
		if (null != projectName.getText())
			return projectName.getText();
		return "";
	}

	/* 返回工程目录 */
	public String getProjectDir() {
		return projectDir.getText();
	}

	/* 向导页面创建工程到外部选项可见 */
	public void setExternalWsEnable() {
		if (isws.getSelection()) {
			lblDir.setEnabled(false);
			projectDir.setEnabled(false);
			btnBrowse.setEnabled(false);
		} else {
			lblDir.setEnabled(true);
			projectDir.setEnabled(true);
			btnBrowse.setEnabled(true);
		}
	}
}
