package com.engeto.vatrates;

public class Main {

    public static final String FILENAME = "vat-eu.csv";
    public static final double TRESHOLD = 20;
    public static void main(String[] args) {

        ListOfStates list = null;
        try {
            list = ListOfStates.importFromFile(FILENAME);
        } catch (StateException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Celkový seznam států:\n"+list.printWithFullRate());

        System.out.println("Seznam států se základní sazbou DPH vyšší než "+TRESHOLD+" % a bez speciální sazby daně:\n"
                +list.printOverTresholdWithFullRate(TRESHOLD));

        list.sortByFullRateValue(list.getListOfStatesOverTreshold(TRESHOLD));

        System.out.println("Seznam států se základní sazbou DPH vyšší než "+TRESHOLD+" % a bez speciální sazby daně:\n"
                +list.printOverTresholdWithFullRateAndReducedRate(TRESHOLD));

    }
}