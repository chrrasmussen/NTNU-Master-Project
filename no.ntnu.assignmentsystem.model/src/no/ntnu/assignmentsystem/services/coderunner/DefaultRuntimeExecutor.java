package no.ntnu.assignmentsystem.services.coderunner;

import java.io.IOException;

public class DefaultRuntimeExecutor implements RuntimeExecutor {
	@Override
	public Process exec(String command) throws IOException {
		System.out.println(String.format("SYSCALL: %s", command));
		return Runtime.getRuntime().exec(command);
	}
}
