/**
 * @author VerpHen
 * @date 2013年9月6日  上午11:31:47
 */

package c3itop.qt.util;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

/*替换选中文本类*/
public class ReplaceText implements IEditorActionDelegate {

	public void run(IAction action) {
		/* 取得当前文本编辑器 */
		IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();

		/* 取得选中文本 */
		/*ITextSelection textSelection = (ITextSelection) getEditor()
				.getEditorSite().getSelectionProvider().getSelection();*/

		/* 替换选中文本 */
		/*AbstractTextEditor editor = (AbstractTextEditor) getEditor();
		editor.getDocumentProvider().getDocument(editor.getEditorInput());
		document.replace(offset, length, replaceText);*/

		/* 选中替换后的文本 */
		/*ITextSelection tsNew = new TextSelection(document, offset, length);
		getEditor().getEditorSite().getSelectionProvider().setSelection(tsNew);*/

	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}

}
