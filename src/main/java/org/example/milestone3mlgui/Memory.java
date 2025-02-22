package org.example.milestone3mlgui;

public class Memory {
    //array(int)
    private int[] wordlist;
    public Memory() {
        wordlist = new int[100];
    }

    //set
    public int[] getWordlist() {
        return wordlist;
    }

    //get
    public void setWordlist(int[] wordlist) {
        this.wordlist = wordlist;
    }

    //get single word
    public int getWordSingle(int index){
        return wordlist[index];
    }

    //set single word
    public void setWordSingle(int index, int value){
        wordlist[index] = value;
        System.out.println();
    }

    public void printMemory(){
        for(int i : wordlist){
            System.out.println(i);
        }
    }
}
