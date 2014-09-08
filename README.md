# Assignment System

Master's thesis by Christian Rasmussen and David Åse.

## Installation

### Download software

1. Download "Eclipse Modeling Tools v4.4.0 (64-bit)" from http://www.eclipse.org/downloads/
2. Download "Play 2.3.0 (Activator)" from https://www.playframework.com/download#older-versions (NOTE: Make sure to download this exact version)
3. Download "JExercise" from https://github.com/hallvard/jexercise/
4. Clone git repo (```git clone https://github.com/chrrasmussen/NTNU-Master-Project.git```)

### Setting up Eclipse

1. Start Eclipse
2. Set workspace to the root of project (the folder containing README.md)
3. Open "File" > "Import..."
4. Select "General" > "Existing Projects into Workspace"
5. Press "Next"
6. In the "Select root directory" field, browse to ```<projectDir>/no.ntnu.assignmentsystem.model```
7. Make sure the project is checked
8. Click "Finish"
9. Repeat for ```<projectDir>/no.ntnu.assignmentsystem.editor```

### Setting up JRE8 in Eclipse (if problems with Java 8)

1. Go to "Window" > "Preferences" > "Java" > "Installed JREs"
2. If jre8 is not in the list, click "Add"
3. Choose "Standard VM"
4. Set "JRE Home" to your jre8 path
5. Click "Finish"

### Installing Maven plugin

1. Go to "Help" in the menu bar
2. Open "Eclipse Marketplace"
3. Search for ```maven 1.5```
4. Install "Maven Integration for Eclipse (Luna and newer) 1.5" (Select the one with the most downloads)
5. Complete the wizard
6. Restart Eclipse when prompted

### Installing Akka dependencies into Eclipse

The Maven command line tool (```mvn```) can be installed using ```brew install maven``` on Mac OS X.

1. Open terminal and navigate to ```<projectDir>/Setup``` folder
2. Run ```mvn p2:site``` (https://github.com/reficio/p2-maven-plugin)
3. Open Eclipse
4. Go to "Help" > "Install new Software..." in menu bar
5. Click "Add..."
6. Click "Local..."
7. Navigate to ```<projectDir>/setup/target/repository/``` and click "Open"
8. Click "OK"
9. Check "Maven osgi-bundles" in table view
10. Click "Next >"
11. Click "Finish"
12. Restart Eclipse when prompted

### Generating model code

1. Navigate to ```no.ntnu.assignmentsystem.model/model/model.genmodel```
2. Right-click on "Model"
3. Click on "Generate Model Code"
4. Repeat for ```no.ntnu.assignmentsystem.model/model/services.genmodel```

These steps must be performed every time the ```.ecore``` model changes.

### Update path to Eclipse install

1. Open the source file ```no.ntnu.assignmentsystem.model/src/no.ntnu.assignmentsystem.services/EditorActor.java```
2. Go to line 45
3. Update the path to match the current installation: ```<eclipseInstall>/plugins/org.eclipse.equinox.launcher_1.3.0.<version>.jar```

### Exporting the editor plugin

1. Right-click the project ```no.ntnu.assignmentsystem.editor``` and select "Export..."
2. Select "Plug-in Development" > "Deployable plug-ins and fragments"
3. Click "Next"
4. Set directory to ```<eclipseInstall>/dropins/```
5. Click "Finish"

These steps must be performed every time the source code in ```no.ntnu.assignmentsystem.editor``` changes.

### Create a Run configuration

1. Open "Run" > "Run Configurations" in menu bar
2. Right-click "Java Application" and select "New"
3. Set name to ```Main```
4. Set project to ```no.ntnu.assignmentsystem.model```
5. Set main class to ```no.ntnu.assignmentsystem.services.Main```
6. Click "Run" and confirm that it compiles

### Exporting to JAR

1. Right-click the project ```no.ntnu.assignmentsystem.model``` and select "Export..."
2. Select "Java" > "Runnable JAR file"
3. Click "Next"
4. Set launch configuration to "Main"
5. Set export destination to ```<projectDir>/AssignmentModel/lib/no.ntnu.assignmentsystem.model.jar``` (Folder must be created)
6. Set library handling to "Copy required libraries into a sub-folder next to the generated JAR"
7. Click "Finish"
8. Move the JAR-files from ```<projectDir>/AssignmentModel/lib/no.ntnu.assignmentsystem.model_lib/``` to ```<projectDir>/AssignmentModel/lib/```

These steps must be performed every time the code in ```no.ntnu.assignmentsystem.model``` changes.

### Run the project

1. Move the TDT4100 problems from ```<downloads>/jexercise-master/no.hal.jex.collection/``` to ```<projectDir>/no.hal.jex.collection/```
2. Move the files from ```<downloads>/activator-1.2.10-minimal/``` to ```<projectDir>/AssignmentModel/```
3. Open terminal and navigate to ```<projectDir>/AssignmentModel/```
4. Run ```./activator run```
5. Open web browser and navigate to: http://localhost:9000

### Setting up IntelliJ (Optional)

1. Generate IDEA-files (```./activator idea```)
2. Start IntelliJ
3. Select "Open Project"
4. Navigate to ```<projectDir>/AssignmentSystem/```
