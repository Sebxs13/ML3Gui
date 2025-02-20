package org.example.milestone3mlgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.Scanner;

//import static java.sql.Types.NULL;


public class Machine {
    //constructor: builds memory
    Memory memory;
    int accumulator = 0;
    Scanner scanner = new Scanner(System.in);
    public Machine(){
        memory = new Memory();
    }
    //run
    public void run(){
        //while loop
        boolean finished = false;
        accumulator = 0;
        int index = 0;
        while(!finished){
            int command = memory.getWordSingle(index);
            int argument = command % 100;
            switch(command / 100){
                case 10:
                    //read
                    read(argument, scanner);
                    break;
                case 11:
                    //write
                    write(argument);
                    break;
                case 20:
                    //load
                    load(argument);
                    break;
                case 21:
                    //store
                    store(argument);
                    break;
                case 30:
                    //add
                    add(argument);
                    break;
                case 31:
                    //subtract
                    subtract(argument);
                    break;
                case 32:
                    //divide
                    divide(argument);
                    break;
                case 33:
                    //multiply
                    multiply(argument);
                    break;
                case 40:
                    //branch
                    index = branch(argument);
                    break;
                case 41:
                    //branchneg
                    if(branchneg(argument) > 0){
                        index = branch(argument);
                    }
                    break;
                case 42:
                    //branchzero
                    if(branchzero(argument) > 0){
                        index = branch(argument);
                    }
                    break;
                case 43:
                    //halt
                    System.out.println("Halt reached Successfully");
                    finished = true;
                    break;
                default:
                    System.out.println("Reached invalid line");
                    finished = true;
                    //invalid input
            }
            //finished = true; //for testing purposes I've closed this loop until we begin actually developing it (Austin Pendley 2/1/2025)
            index++;
        }
    }

    //load
    public void load(int i){
        //add the number to the accumulator
        accumulator = memory.getWordSingle(i);
    }

    //read
    public void read(int i, Scanner s){
        boolean conintueloop = true;
        while(conintueloop) {
            System.out.print("Enter a word (Max 4-digit number):");
            int word = s.nextInt();
            //error trap var word
            if (!(String.valueOf(Math.abs(word)).length() <= 4)) {//if not valid input(less than length 4):
                System.out.println("Invalid word");
                conintueloop = true;//prompt another number
            } else {//if valid input:
                memory.setWordSingle(i, word);
                conintueloop = false;//send it to memory class
            }

        }

    }

    //write
    public boolean write(int location){ //not sure why this is giving problems when uncommented? made a super quick edit.
        /*
        if(location == NULL){//
            System.out.print("location in memory is NULL");
            return false;
        }else{
            System.out.println("location " + location + " in memory: " + memory.getWordSingle(Math.abs(location)));
            return true;
        }

         */
        System.out.println("location " + location + " in memory: " + memory.getWordSingle(Math.abs(location)));
        return true;
    }

    //parse
    public void parse(File file) throws FileNotFoundException {
        int word_size = 4;
        int min_value = -9999;
        int max_value = 9999;
        int index = 0;
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine() && index < 100) {
            String line = scanner.nextLine().trim(); //parse through each line
            try {
                int value = Integer.parseInt(line); // convert a string in line as ints
                if (value >= min_value && value <= max_value) {
                    memory.setWordSingle(index, value);
                } else {
                    // catches words longer than 4 numbers
                    System.err.println("Invalid word size (not " + word_size + ") at index " + index);
                }
            } catch (NumberFormatException e) {
                // catches words that are not numbers
                System.err.println("Error parsing line " + index + ": " + line);
            }
            index++;
        }
        scanner.close();
    }

    //store
    public void store(int location){
        memory.setWordSingle(location, accumulator);
    }


    public void add(int mem_index){
        //add - adds word from location in memory with accumulator
        // leaves result in accumulator
        //accumulator += mem_index
        accumulator += memory.getWordSingle(mem_index); // updated just now
    }


    public void subtract(int mem_index){
        //subtract - subtracts word from location in memory with accumulator
        // leaves result in accumulator
        accumulator -= memory.getWordSingle(mem_index);
    }


    public void divide(int mem_index){
        //divide - divides word from location in memory with accumulator
        // leaves result in accumulator
        accumulator /= memory.getWordSingle(mem_index);
    }

    public void multiply(int mem_index){
        //multiply - multiplies word from location in memory with accumulator
        // leaves result in accumulator
        accumulator *= memory.getWordSingle(mem_index);
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
