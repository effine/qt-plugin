/**
 * @author VerpHen
 * @date 2013年8月27日  下午4:43:26
 */

package c3itop.qt.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import c3itop.qt.util.FileHandle;
import c3itop.qt.wizards.pages.QtProjectWizardPageOne;
import c3itop.qt.wizards.pages.QtProjectWizardPageTwo;

public class QtProjectWizard extends Wizard implements INewWizard {

	private ISelection selection;
	private QtProjectWizardPageOne pageOne;
	private QtProjectWizardPageTwo pageTwo;
	private FileHandle fileHandle;

	public QtProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		if (pageOne == null)
			pageOne = new QtProjectWizardPageOne(selection);
		addPage(pageOne);

		if (pageTwo == null)
			pageTwo = new QtProjectWizardPageTwo(selection);
		addPage(pageTwo);

	}

	@Override
	public boolean performFinish() {
		fileHandle = new FileHandle();

		final String cppName = pageTwo.getCppName();
		final String proNmae = pageTwo.getProName();
		final String projectName = pageOne.getProjectName();
		final String projectDir = pageOne.getProjectHandle().getName();

		/* 获取工作区 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		IWorkspace workspace = root.getWorkspace();
		IProjectDescription description = workspace
				.newProjectDescription(project.getName());
		description.setLocation(null);

		try {
			NullProgressMonitor monitor = new NullProgressMonitor();
			project.create(description, monitor);
			project.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(
					monitor, 1000));
		} catch (CoreException e) {
			e.printStackTrace();
		}

		/* 刷新本地资源 */
		// project.refreshLocal(IResource.DEPTH_INFINITE, null);

		IRunnableWithProgress rp = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				try {
					fileHandle.creadFile(projectDir, cppName, monitor);
					fileHandle.creadFile(projectDir, proNmae, monitor);
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, false, rp);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 */
	private void doFinish() {

	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
