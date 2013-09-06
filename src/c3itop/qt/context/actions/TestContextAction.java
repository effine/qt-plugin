/**
 * @author VerpHen
 * @date 2013年9月6日  下午2:32:53
 */

package c3itop.qt.context.actions;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import c3itop.qt.console.CustomConsole;

public class TestContextAction implements IObjectActionDelegate,
		IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	public void run(IAction action) {
		CustomConsole.printConsole("工作空间1 :" + Platform.getLocation());
		CustomConsole.printConsole("工作空间3 :" + Platform.getInstanceLocation());

	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
