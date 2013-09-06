/**
 * @author VerpHen
 * @date 2013年8月26日  下午5:03:40
 */

package c3itop.qt.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;

public class ProjectHandle {
	private IProject project = null;

	/* 获取当前工程路径 */
	public IPath getProjectPath() {
		return Platform.getLocation();
	}

	/* 通过工程下的文件 获得工程名 */
	public IProject getProjectByFileNmae(IFile file) {
		return file.getProject();
	}

	/* 获取当前工程名 */
	public IProject getCurrentProject() {
		/* 取得当前的编辑器,通过当前编辑器获得工程 */
		IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		if (part != null) {
			Object object = part.getEditorInput().getAdapter(IFile.class);
			if (object != null) {
				project = ((IFile) object).getProject();
			}
		}

		ISelectionService selectionService = Workbench.getInstance()
				.getActiveWorkbenchWindow().getSelectionService();

		ISelection selection = selectionService.getSelection();

		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection)
					.getFirstElement();
			if (element instanceof IResource) {
				project = ((IResource) element).getProject();
			}
			/*
			 * else if (element instanceof PackageFragmentRootContainer) {
			 * IJavaProject jProject = ((PackageFragmentRootContainer) element)
			 * .getJavaProject(); project = jProject.getProject(); } else if
			 * (element instanceof IJavaElement) { IJavaProject jProject =
			 * ((IJavaElement) element) .getJavaProject(); project =
			 * jProject.getProject(); } else if (element instanceof EditPart) {
			 * IFile file = (IFile) ((DefaultEditDomain) ((EditPart) element)
			 * .getViewer().getEditDomain()).getEditorPart()
			 * .getEditorInput().getAdapter(IFile.class); project =
			 * file.getProject(); }
			 */
		}

		/*
		 * 如果是在action中遇到这种需求，就将event调用getSelection()，然后转换为IStructuredSelection，
		 * 后续操作相同。
		 */

		/*
		 * IViewPart viewPart = AuditPlugin.getDefault().getWorkbench()
		 * .getActiveWorkbenchWindow().getActivePage()
		 * .findView("org.eclipse.ui.navigator.ProjectExplorer");
		 * StructuredSelection sl = (StructuredSelection) viewPart.getSite()
		 * .getSelectionProvider().getSelection(); Object obj =
		 * sl.getFirstElement();
		 */

		/*
		 * 这一段是用在action里面，通过寻找视图来得到选择项的。注意获取视图的方法。
		 * 其中的AuditPlugin是建立插件的时候系统帮我们建立的activator class，用它来控制插件的生命周期
		 */
		/* 当在由event触发的事件处理代码中时，可以这样做： */
		/*
		 * IStructuredSelection structureSel = (IStructuredSelection) event
		 * .getSelection();
		 */

		/* 当我们在一些控件中是，就方便多了，例如在TableViewer中： */

		/*
		 * ISelection selection = viewer.getSelection(); Object obj =
		 * ((IStructuredSelection) selection).getFirstElement();
		 */

		return project;
	}

	/* 测试方法 */
	public IProject getCusProject() {
		IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IEditorInput input = editor.getEditorInput();

		/*
		 * if (input instanceof IFileEditorInput) { IFile file =
		 * ((IFileEditorInput) input).getFile(); project = file.getProject(); }
		 */
		return null;
	}

	/* 获取工程，没有则创建 */
	public static IProject createOrGetProject(String projectName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		if (project.exists()) {
			return project;
		} else {
			IWorkspace workspace = ResourcesPlugin.getWorkspace().getRoot()
					.getWorkspace();
			NullProgressMonitor monitor = new NullProgressMonitor();

			IProjectDescription description = workspace
					.newProjectDescription(projectName);
			try {
				project.create(description, monitor);
				project.open(IResource.BACKGROUND_REFRESH,
						new SubProgressMonitor(monitor, 500));
			} catch (CoreException e) {
				e.printStackTrace();
			}
			return project;
		}
	}
}
