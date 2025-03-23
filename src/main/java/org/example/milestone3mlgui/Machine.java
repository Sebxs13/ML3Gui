package org.example.milestone3mlgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//import static java.sql.Types.NULL;


public class Machine {
    //constructor: builds memory
    Memory memory;
    int accumulator = 0;
    int index;
    String returnValue;

    boolean awaitingRead;

    public Machine(){
        memory = new Memory();
    }

    //run
    public String run(MLGuiController controller)  {
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
                returnValue+="Enter a word (Max 4-digit number). Press Enter to continue:\n";
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
                returnValue += "Halted Successfully!\n";
                finished = true;
            } else {
                returnValue += "Reached an Invalid Input.\n";
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
                        returnValue += "branched to index:"+index+"\n";
                    }
                } else if (command / 100 == 43) {//halt
                    returnValue += "Halted Successfully!\n";
                    finished = true;
                } else {
                    returnValue += "Reached an Invalid Input.\n";
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
        return accumulator + " loaded to accumulator.";
    }

    //read
    public void read(int word){
        memory.setWordSingle(memory.getWordSingle(index - 1) % 100,word);
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
        StringBuilder errorMessages = new StringBuilder();

        try {
            while (scanner.hasNextLine() && index < 100) {
                int line = Integer.parseInt(scanner.nextLine().trim());
                boolean isValid = true;

                try {
                    // Try to parse the line as an integer
                    int value = Integer.parseInt(String.valueOf(line));

                    // Validate if it's within the valid range
                    if (value < min_value || value > max_value) {
                        errorMessages.append("Line ").append(index + 1).append(": ")
                                .append(value).append(" (Out of range)\n");
                        isValid = false;
                    }

                    if (isValid) {
                        // Store valid integer in memory
                        memory.setWordSingle(index, value);
                    }

                } catch (NumberFormatException e) {
                    // Handle non-numeric input and store it as a string
                    errorMessages.append("Line ").append(index + 1).append(": '")
                            .append(line).append("' (Invalid input)\n");

                    // Store the word or non-numeric string as-is in memory
                    memory.setWordSingle(index, line);  // Store as string in memory
                }

                index++;
            }
        } finally {
            scanner.close();
        }

        // Display errors if any
        if (!errorMessages.isEmpty()) {
            System.out.println("Errors encountered:\n" + errorMessages.toString());
        }
    }

    //store
    public String store(int location){
        memory.setWordSingle(location, accumulator);
        return accumulator +" stored at "+ location;
    }


    public String add(int mem_index){
        //add - adds word from location in memory with accumulator
        // leaves result in accumulator
        //accumulator += mem_index
        int temp = accumulator;

        accumulator += memory.getWordSingle(mem_index); // updated just now
        return temp + " and "+ memory.getWordSingle(mem_index)+" added. Added result: "+ accumulator;
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
        return temp+" and "+ memory.getWordSingle(mem_index)+" divided. Divided result:"+ accumulator;
    }

    public String multiply(int mem_index){
        //multiply - multiplies word from location in memory with accumulator
        // leaves result in accumulator
        int temp = accumulator;
        accumulator *= memory.getWordSingle(mem_index);
        return temp+" and "+ memory.getWordSingle(mem_index)+" multiplied. Multiplied result: "+ accumulator;
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

}
