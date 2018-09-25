package com.company;

/*
* A Discrete Math book uses sets, where each set is made of numbers and other sets.  For instance { { 1 , 2 } , 3 }
* would be the set whose first member is the set {1, 2} and whose second member is the number 3.
* The following CFG defines the appropriate syntax:
*   set -> { list }
*   list -> head tail | ε
*   head -> number | set
*   tail -> , head tail | ε
*
* 1.  Construct a parse tree for { { 1 , 2 } , 3 }.
* 2.  Write a recursive descent parser for this CFG.
* */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //User input
        Scanner sn = new Scanner(System.in);
        System.out.println("Check Checker!");
        System.out.println("Enter a set || \"q\" to quit || \"rsc\"to run system check");
        String userInput = sn.nextLine();

        //while loop to keep getting user input
        while (!userInput.equals("q")) {

            if (userInput.equals("rsc"))
                systemCheck();//method calls the systemCheck with some default test cases

            System.out.println(userInput + ": is " + parser(userInput) + "!");

            System.out.println("Enter a set || \"q\" to quit || \"rsc\"to run system check");
            userInput = sn.nextLine();
        }
    }

    private static boolean parser(String value) {
        if (set(value)) {
            value = value.substring(1, value.length() - 1);
            return list(value);
        }
        return false;
    }

    /**
     * Method checks if the set proved as a string is valid. --> set -> { list }
     * @param value String representing a set
     * @return Boolean true if the set is valid else false
     */
    private static boolean set(String value) { return value.charAt(0) == '{' && value.charAt(value.length() - 1) == '}'; }

    /**
     * Method checks if the list is valid. --> list -> head tail | ε
     * @param value String representing a list
     * @return Boolean true if its valid, else it returns false
     */
    private static boolean list(String value) {

        if (value.length() == 0 ) // checking for a empty string
            return true;
        else { //Checking if its a valid head and tail
            boolean lastComma = true; //used to insure the proper comma is looked at when breaking up the string
            boolean headValid; //represents if list has a proper head
            boolean tailValid; // represents if list has a proper tail

            for (int i = 0; i < value.length(); i++) { // going through the value and testing each chunk between commas
                //making sure the comma parsed at isn't between  { }
                if (value.charAt(i) == '{')
                    lastComma = false;
                else if (value.charAt(i) == '}')
                    lastComma = true;
                //if its not between two { }
                if (lastComma && value.charAt(i) == ',') {
                    String head = value.substring(0, i); //head value
                    value = value.substring(i); //tail value
                    headValid = head(head); //checking to see if head is valid (method is recursive)
                    if (!headValid)// if head is false then the whole set is invalid
                        return false;
                    tailValid = tail(value);
                    if (tailValid)// if tail is true then this chuck is also true
                        return true;
                }
            }
            return false;
        }
    }

    /**
     * Method checks if head is valid (Recursive method) --> head -> number | set
     * @param headValue String representing head value
     * @return Boolean true if its valid, else it returns false
     */
    private static Boolean head(String headValue) {
        //Checking to see if its empty or a "number" value
        if (headValue.length() == 0 || !headValue.contains(",") && !headValue.contains("{") && !headValue.contains("}"))
            return true;
        if (set(headValue))// checking to see if its a set
            return parser(headValue);
        return false;
    }

    /**
     * Method checks to see if the tail is valid --> tail -> , head tail | ε
     * @param tailValue String representing the tail
     * @return Boolean true if its valid, else it returns false
     */
    private static Boolean tail(String tailValue) {

        if (tailValue.length() == 0) //checking a empty string
            return true;

        if (tailValue.length() >= 2) {//check its not to small to continue
            tailValue = tailValue.substring(1);

            if (tailValue.length() >= 3) // checking to see if its heads and tails
                return list(tailValue) || head(tailValue);

            if (!tailValue.contains(",") && !tailValue.contains("{") && !tailValue.contains("}"))
                return true;
        }
        return false;
    }

    private static void systemCheck()
    {
        System.out.println("{}" + parser("{}"));
        System.out.println("{1}" + parser("{1}"));
        System.out.println("{{},{},{}}"+ parser("{{},{},{}}"));
        System.out.println("{{1,2},{1,2}}" + parser("{{1,2},{1,2}}"));
        System.out.println("{{1,2},3}" + parser("{{1,2},3}"));
        System.out.println("{{1,1},1,1,1}" + parser("{{1,1},1,1,1}"));
        System.out.println("{{1,2},3,4,5,6}" + parser("{{1,2},3,4,5,6}"));
        System.out.println("{{1,2},3,4,{5,6}}" + parser("{{1,2},3,4,{5,6}}"));
        System.out.println("{{1,2},{1,2,3}}" + parser("{{1,2},{1,2,3}}"));

        System.out.println(" ");
        System.out.println("{" + parser("{"));
        System.out.println("{{}{}}" + parser("{{}{}}"));
        System.out.println("{{1,2,1,2,3}" + parser("{{1,2,1,2,3}"));
    }
}
