/**
 * @author VerpHen
 * @date 2013年9月12日  下午2:58:29
 */

package c3itop.qt.wizards.pages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import c3itop.qt.util.TargetPlatform;
import c3itop.qt.util.TargetPlatformMemory;

public class QtTargetWizardPage extends WizardPage {

	private Combo fatherCmb = null;
	private Combo childCmb = null;

	private String fatherTarget;
	TargetPlatform platform = new TargetPlatform();

	public QtTargetWizardPage(ISelection selection) {
		super("wizardpage");
		setTitle("Qt Target Platform");
		setDescription("Select compile target platform .");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		Label lblTargetsPlatform = new Label(container, SWT.NONE);
		lblTargetsPlatform.setBounds(37, 36, 113, 17);
		lblTargetsPlatform.setText("Targets Platform :");

		fatherCmb = new Combo(container, SWT.READ_ONLY);
		fatherCmb.setBounds(157, 33, 278, 25);

		childCmb = new Combo(container, SWT.READ_ONLY);
		childCmb.setBounds(157, 85, 278, 25);

		/* 填充目标编译向导页操作系统栏 */
		String[] item = platform.fatherPlatform;
		fatherCmb.setItems(item);

		/* 设置目标编译向导页 根据操作系统进行默认显示 */
		String currentOS = platform.getCurrentOS();
		for (int i = 0; i < item.length; i++) {
			if (currentOS.equals(item[i])) {
				fatherCmb.select(i);
				String[] childItem = (String[]) platform.map.get(item[i]);
				childCmb.setItems(childItem);
				childCmb.select(0);
			}
		}

		fatherCmb.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fatherTarget = fatherCmb.getText();
				for (String s : platform.fatherPlatform) {
					if (s.equals(fatherTarget)) {
						String[] childItem = (String[]) platform.map.get(s);
						childCmb.setItems(childItem);
						childCmb.select(0);
					}
				}
			}
		});

	}

	/* Select current target compile platform */
	public String getCurTargetPlatform() {
		return childCmb.getText();
	}
}
