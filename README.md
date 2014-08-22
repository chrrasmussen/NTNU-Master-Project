# Assignment System

Master's thesis by Christian Rasmussen and David Åse.

## Installation

### Setting up Eclipse

1. Start Eclipse
2. Set workspace to the root of project (the folder containing README.md)
3. Open "File" > "Import..."
4. Select "General" > "Existing Projects into Workspace"
5. Press "Next"
6. In the "Select root directory" field, browse to "<projectDir>/no.ntnu.assignmentsystem.model"
7. Make sure the project is checked
8. Click "Finish"

### Installing Java 8 plugin

1. Go to "Help" in the menu bar
2. Open "Eclipse Marketplace"
3. Search for ```java 8 kepler```
4. Install "Java 8 support for Eclipse Kepler SR2"
5. Complete the wizard.

### Setting up JRE8 in eclipse
1. Go to "Window" > "Preferences" > "Java" > "Installed JREs"
2. If jre8 is not in the list, click "Add"
3. Choose "Standard VM"
4. Set "JRE Home" to your jre8 path. 
5. Click "Finish"

### Installing Maven plugin

1. Go to "Help" in the menu bar
2. Open "Eclipse Marketplace"
3. Search for ```maven 1.4```
4. Install "Maven Integration for Eclipse (Juno or newer) 1.4"
5. Complete the wizard.

### Installing Akka dependencies into Eclipse

1. Open terminal and navigate to "<projectDir>/setup" folder
2. Run ```mvn p2:site``` (https://github.com/reficio/p2-maven-plugin)
3. Open Eclipse
4. Go to "Help" > "Install new Software..." in menu bar
5. Click "Add..."
6. Click "Local"
7. Navigate to "<projectDir>/setup/target/repository" and click "Open"
8. Click "OK"
9. Check "Maven osgi-bundles" in table view
10. Click "Next >"
11. Click "Finish"

### Generating model code

1. Navigate to "model/model.genmodel"
2. Right-click on "Model"
3. Click on "Generate Model Code"

### Create a Run configuration

1. Open "Run" > "Run Configurations" in menu bar
2. Right-click "Java Application" and select "New"
3. Set name to ```Main```
4. Set project to "no.ntnu.assignmentsystem.model"
5. Set main class to ```Main```
6. Click "Run" and confirm that it compiles

### Exporting to JAR

1. Right-click the project and select "Export"
2. Select "Java" > "Runnable JAR file"
3. Click "Next"
4. Set launch configuration to "Main"
5. Set export destination to "AssignmentModel/lib"
6. Set library handling to "Copy required libraries into a sub-folder next to the generated JAR"
7. Check "Save an ANT script"
8. Click "Finish"
9. Move the JAR-files from sub-folder to "AssignmentModel/lib"

### Set up automatic building

1. Right-click project and select "Properties"
2. Go to "Builders"
3. Click "New..."
4. Select "Ant Builder"
5. Click "OK"
6. Set name ```Model Builder```
7. Set buildfile to the generated ANT-file
8. Set base directory to the root folder (folder containing README.md)
9. Click "OK"

### Setting up IntelliJ

1. Generate IDEA-files (```play idea```)
2. Start IntelliJ
3. Select "Open Project"
4. Navigate to "<projectDir>/AssignmentSystem"
