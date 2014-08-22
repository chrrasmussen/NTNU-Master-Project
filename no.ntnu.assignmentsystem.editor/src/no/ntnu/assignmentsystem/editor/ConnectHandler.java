package no.ntnu.assignmentsystem.editor;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ConnectHandler extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		String title = "Connect to remote actor";
		String question = "What is the address of the remote actor?";
		
		InputDialog dialog = new InputDialog(shell, title, question, null, null);
		dialog.open();
		
		if (dialog.getReturnCode() == Window.OK) {
			String path = dialog.getValue();
			
			try {
				PluginHelper.start(path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
