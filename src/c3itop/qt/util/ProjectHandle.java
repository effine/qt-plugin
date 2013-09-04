/**
 * @author VerpHen
 * @date 2013年8月26日  下午5:03:40
 */

package c3itop.qt.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.internal.Workbench;

public class ProjectHandle {

	private IProject project = null;

	/* 获取当前工程路径 */
	public IPath getProjectPath() {
		return Platform.getLocation();
	}

	/* 获取当前工程名 */
	public IProject getCurrentProject() {
		ISelectionService selectionService = Workbench.getInstance()
				.getActiveWorkbenchWindow().getSelectionService();

		ISelection selection = selectionService.getSelection();

		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection)
					.getFirstElement();
			if (element instanceof IResource) {
				project = ((IResource) element).getProject();
			}
		}
		return project;
	}

	/* 通过工程下的文件 获得工程名 */
	public IProject getProjectByFileNmae(IFile file) {

		file.getProject().getName(); // 通过文件获取工程名 file.getProject().getName();
		return project;
	}
}
