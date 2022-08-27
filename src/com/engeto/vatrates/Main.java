package com.engeto.vatrates;

public class Main {

    public static final String filename = "vat-eu.csv";
    public static void main(String[] args) {

        ListOfStates list = null;
        try {
            list = ListOfStates.importFromFile(filename);
        } catch (StateException e) {
            throw new RuntimeException(e);
        }

        System.out.println(list.printWithFullRate());


    }
}