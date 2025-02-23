## Objective:

#### Our goal is to create a program in java that works as a simple virtual machine. It will be capable of performing basicML programs. The purpose of this program is to teach students CS principles.

#### The program has five classes:  WordGui, MLGuiController, MLApplication, Machine, and Memory. WordGui and MLapplication work together to create the GUI. The MLGuiController bridges the gap between machine and the GUI, so that all the buttons work and the information displays. The two functions in Machine that pull everything together are Parse and Run. Parse organizes the file into a readable format for the run function. Run goes through each basicML command and calls a use case depending on the contents of the command. 

## How to Run Our program through Commandline:
1. check that you have java installed
2. navagate to the correct directory(the file name should be ML3Gui-project)
3. to manually run the program type in java - jar "filename".jar
4. A window should appear you can now either update the list of commands by clicking in the boxes with numbers or click the file button to upload a file.
5. Once the file is done uploading you can now click run the output text box will inform you of anything you have to enter in the input text box
6. If the file does not run after clicking the run button, check the discriptions next to the list of commands(The descriptions will say whether the command is valid or not)
7. The output field will keep you updated on when the program is finished
