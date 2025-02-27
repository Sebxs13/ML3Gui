package org.example.milestone3mlgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//import static java.sql.Types.NULL;


public class Machine { //TODO ALL THIS STUFF NEEDS TO BE ACCESSIBLE TO THE MLGUI CONTROLLER.
    //constructor: builds memory
    Memory memory;
    int accumulator = 0;
    int index;
    String returnValue;

    boolean awaitingRead;

    public Machine(){
        memory = new Memory();
    } //todo Accumulator and index implementation

    //run
    public String run(MLGuiController controller)  { //todo revamped run that goes step by step and returns strings
        //while loop

        boolean finished = false;
        awaitingRead = false;
        accumulator = 0;
        index = 0;
        returnValue = "";
        while(!finished){
            int command = memory.getWordSingle(index);
            int argument = command % 100;
            if (command / 100 == 10) {//read
                awaitingRead = true;
                returnValue+=" Enter a word (Max 4-digit number). Press Enter to continue:\n";
                finished = true;
            } else if (command / 100 == 11) {//write
                returnValue += write(argument)+"\n";
            } else if (command / 100 == 20) {//load
                returnValue += load(argument)+"\n";
            } else if (command / 100 == 21) {//store
                returnValue += store(argument)+"\n";
            } else if (command / 100 == 30) {//add
                returnValue += add(argument)+"\n";
            } else if (command / 100 == 31) {//subtract
                returnValue += subtract(argument)+"\n";
            } else if (command / 100 == 32) {//divide
                returnValue += divide(argument)+"\n";
            } else if (command / 100 == 33) {//multiply
                returnValue += multiply(argument)+"\n";
            } else if (command / 100 == 40) {//branch
                index = branch(argument);
                returnValue += "branched to index:"+index+"\n";
            } else if (command / 100 == 41) {//branchneg
                if (branchneg(argument) > 0) {
                    index = branch(argument);
                    returnValue += "branched to index:"+index+"\n";
                }
            } else if (command / 100 == 42) {//branchzero
                if (branchzero(argument) > 0) {
                    index = branch(argument);
                    returnValue += "branched to index:"+index+"\n";
                }
            } else if (command / 100 == 43) {//halt
                System.out.println("Halt reached Successfully");
                finished = true;
            } else {
                System.out.println("Reached invalid line");
                finished = true;
                //invalid input
            }
            //finished = true; //for testing purposes I've closed this loop until we begin actually developing it (Austin Pendley 2/1/2025)
            index++;
        }
        return returnValue;
    }

    public String run2(){
        if(awaitingRead){
            awaitingRead = false;
            boolean finished = false;
            returnValue = "";
            while(!finished) {
                int command = memory.getWordSingle(index);
                int argument = command % 100;
                if (command / 100 == 10) {//read
                    awaitingRead = true;
                    returnValue+= " Enter a word (Max 4-digit number). Press Enter to continue:\n";
                    finished = true;
                } else if (command / 100 == 11) {//write
                    returnValue += write(argument)+"\n";
                } else if (command / 100 == 20) {//load
                    returnValue += load(argument)+"\n";
                } else if (command / 100 == 21) {//store
                    returnValue += store(argument)+"\n";
                } else if (command / 100 == 30) {//add
                    returnValue += add(argument)+"\n";
                } else if (command / 100 == 31) {//subtract
                    returnValue += subtract(argument)+"\n";
                } else if (command / 100 == 32) {//divide
                    returnValue += divide(argument)+"\n";
                } else if (command / 100 == 33) {//multiply
                    returnValue += multiply(argument)+"\n";
                } else if (command / 100 == 40) {//branch
                    index = branch(argument);
                    returnValue += "branched to index:"+index+"\n";
                } else if (command / 100 == 41) {//branchneg
                    if (branchneg(argument) > 0) {
                        index = branch(argument);
                        returnValue+= "branched to index:"+index+"\n";
                    }
                } else if (command / 100 == 42) {//branchzero
                    if (branchzero(argument) > 0) {
                        index = branch(argument);
                        returnValue = "branched to index:"+index+"\n";
                    }
                } else if (command / 100 == 43) {//halt
                    System.out.println("Halt reached Successfully");
                    finished = true;
                } else {
                    System.out.println("Reached invalid line");
                    finished = true;
                    //invalid input
                }
                index++;
            }
            return returnValue;
        }
        return "";
    }

    //load
    public String load(int i){
        //add the number to the accumulator
        accumulator = memory.getWordSingle(i);
        return accumulator +"loaded to accumulator";
    }

    //read
    public void read(int word){
        memory.setWordSingle(memory.getWordSingle(index) % 100,word);
    }

    //write
    public String write(int location){ //not sure why this is giving problems when uncommented? made a super quick edit.
        System.out.println("location " + location + " in memory: " + memory.getWordSingle(Math.abs(location)));
        return "location: " + location + " in memory: " + memory.getWordSingle(location);
    }

    //parse
    public void parse(File file) throws FileNotFoundException {
        int min_value = -9999;
        int max_value = 9999;
        int index = 0;
        Scanner scanner = new Scanner(file);
        ArrayList<Integer> tempMemory = new ArrayList<>();

        try {
            while (scanner.hasNextLine() && index < 100) {
                String line = scanner.nextLine().trim(); //parse through each line
                try {
                    int value = Integer.parseInt(line); // convert a string in line as ints
                    if (value < min_value || value > max_value) {
                        throw new IllegalArgumentException("Invalid value at index " + index + ": " + value + ". Value must 4 numbers long.");
                    }
                    tempMemory.add(value);
                } catch (NumberFormatException e) {
                    // catches words that are not numbers
                    throw new IllegalArgumentException("Error parsing line " + index + ": " + line + " is not a valid word.");
                }
                index++;
            }
            // if validation is successful, then upload to memory
            for (int i = 0; i < tempMemory.size(); i++) {
                memory.setWordSingle(i, tempMemory.get(i));
            }

        } finally {
            scanner.close();
        }
    }

    //store
    public String store(int location){
        memory.setWordSingle(location, accumulator);
        return accumulator +"stored at"+ location;
    }


    public String add(int mem_index){
        //add - adds word from location in memory with accumulator
        // leaves result in accumulator
        //accumulator += mem_index
        int temp = accumulator;

        accumulator += memory.getWordSingle(mem_index); // updated just now
        return temp + " and "+ memory.getWordSingle(mem_index)+"added. Added result:"+ accumulator;
    }


    public String subtract(int mem_index){
        //subtract - subtracts word from location in memory with accumulator
        // leaves result in accumulator
        int temp = accumulator;
        accumulator -= memory.getWordSingle(mem_index);
        return temp+" and "+ memory.getWordSingle(mem_index)+"subtracted. Subtracted result:"+ accumulator;
    }


    public String divide(int mem_index){
        //divide - divides word from location in memory with accumulator
        // leaves result in accumulator
        int temp = accumulator;
        accumulator /= memory.getWordSingle(mem_index);
        return temp+" and "+ memory.getWordSingle(mem_index)+"divided. Divided result:"+ accumulator;
    }

    public String multiply(int mem_index){
        //multiply - multiplies word from location in memory with accumulator
        // leaves result in accumulator
        int temp = accumulator;
        accumulator *= memory.getWordSingle(mem_index);
        return temp+" and "+ memory.getWordSingle(mem_index)+"multiplied. Multiplied result:"+ accumulator;
    }

    //branch
    public int branch(int i){
        return (i % 100) - 1;
    }

    //branchneg
    public int branchneg(int i){
        return (accumulator < 0 ? i : -1) - 1;
    }

    //branchzero
    public int branchzero(int i){
        return (accumulator == 0 ? i : -1) - 1;
    }

    public String intToString(int word){
        //i have realized that error trapping for order may be pointless
        int command = word / 100;
        switch (command){
            case 10://read
                return "read word from screen in to a location in memory.\n";
            case 11://write
                return "write a word from memory into the screen\n";
            case 20://load
                return " store word from memory into the accumulator\n";
            case 21://store
                return "store word from accumulator into memory\n";
            case 30:// add
                return "add a word from the accumulator with a word from memory, and store the results in the accumulator\n";
            case 31://subtract
                return " subtract a word from the accumulator with a word from memory, and store the results in the accumulator\n";
            case 32://divide
                return " divide a word from the accumulator with a word from memory, and store the results in the accumulator\n";
            case 33://multiply
                return" multiply a word from the accumulator with a word from memory, and store the results in the accumulator\n";
            case 40://branch
                return " branch to a specific location in memory\n";
            case 41://branchneg
                return"Branch to a specific location in memory if the accumulator is negative";
            case 42://branchzero
                return"Branch to a specific location in memory if the accumulator is zero";
            case 43://hault
                return"hault: stops the program\n";
            default:
                return "invalid command";
        }
    }
}
