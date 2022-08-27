package com.engeto.vatrates;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static final String FILENAME = "vat-eu.csv";
    public static final double limit = 20;
    public static void main(String[] args) {

        ListOfStates list;
        double limit = 20;
        try {
            list = ListOfStates.importFromFile(FILENAME);
        } catch (StateException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Celkový seznam států:\n"+list.printWithFullRate());

        System.out.println("Zadej limit VAT sazby: ");

        Scanner sc = new Scanner(System.in);
        limit = sc.nextDouble();

        System.out.println("Seznam států se základní sazbou VAT vyšší než "+ limit +" % a bez speciální sazby daně:\n"
                +list.printOverLimitWithFullRate(limit));

        list.sortByFullRateValue(list.getListOfStatesOverLimit(limit));

        System.out.println("Seznam států se základní sazbou VAT vyšší než "+ limit +" % a bez speciální sazby daně:\n"
                +list.printOverLimitWithFullRateAndReducedRate(limit));

        System.out.println("====================");

        System.out.println("Sazba VAT 20 % nebo nižší nebo používají speciální sazbu: "
                +list.getAbreviationsStatesUnderLimit());

        try {
            list.exportToFile(limit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}