/**
 * @author VerpHen
 * @date 2013年8月27日  下午4:43:26
 */

package c3itop.qt.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
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
		/* 获得向导One输入的工程名 */
		final String projectName = pageOne.getProjectName();

		/* 获得向导Two输入的cpp、pro文件名 */
		final String cppName = pageTwo.getCppName("cpp");
		final String proNmae = pageTwo.getCppName("pro");

		/* 获得向导选择的工程位置 */
		final String projectDir = pageOne.getProjectHandle().getName();
		fileHandle = new FileHandle();

		/* 获取工作区 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);

		IWorkspace workspace = root.getWorkspace();
		IProjectDescription description = workspace
				.newProjectDescription(project.getName());

		/* 参数为空，表示在默认工作空间下创建工程 */
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
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		/* 调用createFile方法创建文件 */
		fileHandle.creadFile(projectDir, cppName, "cpp");
		fileHandle.creadFile(projectDir, proNmae, "pro");

		return true;
	}

	/** 向导页没有到最后也的时候是否能完成 */
	@Override
	public boolean canFinish() {
		return true;
	}

	/** 清理本地资源，如图像、剪切板等有该类创造的资源，该方法遵循谁创建谁销毁的Eclipse主题 */
	@Override
	public void dispose() {
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	/* 重载它来指定进度条部件是否可视，点击Finish调用doFinish方法，进度条就会出现.测试为显示 */
	@Override
	public boolean needsProgressMonitor() {
		return true;
	}
}
