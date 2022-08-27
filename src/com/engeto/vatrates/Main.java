package com.engeto.vatrates;

public class Main {

    public static final String FILENAME = "vat-eu.csv";
    public static final double LIMIT = 20;
    public static void main(String[] args) {

        ListOfStates list = null;
        try {
            list = ListOfStates.importFromFile(FILENAME);
        } catch (StateException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Celkový seznam států:\n"+list.printWithFullRate());

        System.out.println("Seznam států se základní sazbou VAT vyšší než "+ LIMIT +" % a bez speciální sazby daně:\n"
                +list.printOverLimitWithFullRate(LIMIT));

        list.sortByFullRateValue(list.getListOfStatesOverLimit(LIMIT));

        System.out.println("Seznam států se základní sazbou VAT vyšší než "+ LIMIT +" % a bez speciální sazby daně:\n"
                +list.printOverLimitWithFullRateAndReducedRate(LIMIT));

        System.out.println("====================");

        System.out.println("Sazba VAT 20 % nebo nižší nebo používají speciální sazbu: "
                +list.getAbreviationsStatesUnderLimit());
    }
}