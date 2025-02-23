## Objective:

#### Our goal is to create a program in java that works as a simple virtual machine. It will be capable of performing basicML programs. The purpose of this program is to teach students CS principles.

#### The program has three classes: Main, Machine, and Memory. The Main class creates a machine object, and uses JFileChooser to prompt the user to choose a file. Afterwhich Machine calls two of its methods parse and run. Parse organizes the file into a readable format for the run function. Run goes through each basicML command and calls a use case depending on the contents of the command. The function of each use case is described below

## How to Run Our program on Commandline:
1. check that you have java installed
2. navagate to the correct directory(the file name should be ML3Gui)
3. to manually run the program type in java - jar "filename".jar
4. Then the program will be running and prompt you for a file which should contain your basicML commands
