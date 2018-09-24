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
* 2.  Write a recursive descent parser for this CFG.  Use Java and submit your answer as a zipped Netbeans project to the Parser dropbox on the BOLT site for this class.
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
                systemCheck();//method calls the runCurrencyChecker with some default test cases

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

    private static boolean set(String value) { return value.charAt(0) == '{' && value.charAt(value.length() - 1) == '}'; }

    private static boolean list(String value) {

        if (value.length() == 0)
            return true;
        else {
            boolean lastComma = true;
            boolean headValid;
            boolean tailValid;

            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) == '{')
                    lastComma = false;
                else if (value.charAt(i) == '}')
                    lastComma = true;
                if (lastComma && value.charAt(i) == ',') {
                    String head = value.substring(0, i);
                    value = value.substring(i);
                    headValid = head(head);
                    if (!headValid)
                        return false;
                    tailValid = tail(value);
                    if (tailValid)
                        return true;
                }
            }
            return false;
        }
    }

    private static Boolean head(String headValue) {

        if (headValue.length() == 0 || !headValue.contains("{") && !headValue.contains("}") && !headValue.contains(","))
            return true;
        if (set(headValue))
            return parser(headValue);

        return false;
    }

    private static Boolean tail(String tailValue) {

        if (tailValue.length() == 0)
            return true;

        if (tailValue.length() >= 2) {
            tailValue = tailValue.substring(1);

            if (tailValue.equals("{}"))
                return true;

            if (!tailValue.contains(",") && !tailValue.contains("{") && !tailValue.contains("}"))
                return true;

            if (tailValue.length() >= 3 && tailValue.contains(","))
                return list(tailValue) || head(tailValue);
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
        System.out.println("{{1,2},{1,2,3}}" + parser("{{1,2},{1,2,3}"));
    }
}
