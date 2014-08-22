package no.ntnu.assignmentsystem.editor;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class BackgroundApplication implements IApplication {
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("Application started");
		
		String[] arguments = (String[])context.getArguments().get("application.args");
		if (arguments.length == 1) {
			String path = arguments[0];

			PluginHelper.start(path);
		}
		
		return null;
	}

	@Override
	public void stop() {
	}
}
