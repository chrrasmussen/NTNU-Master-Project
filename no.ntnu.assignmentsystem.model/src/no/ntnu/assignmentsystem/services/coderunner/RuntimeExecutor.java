package no.ntnu.assignmentsystem.services.coderunner;

import java.io.IOException;

public interface RuntimeExecutor {
	Process exec(String command) throws IOException;
}
