package com.engeto.vatrates;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;
import java.text.NumberFormat;
import java.text.ParseException;

public class Main {

    public static final String FILENAME = "vat-eu.csv";
    public static final Locale locale = new Locale("cs","CZ");

    public static double limit = 0;
    public static void main(String[] args) throws StateException {

        ListOfStates list;
        double limit;
        DecimalFormat ft = new DecimalFormat("###.#");

        try {
            list = ListOfStates.importFromFile(FILENAME);
        } catch (StateException e) {
            throw new StateException(e.getLocalizedMessage());
        }

        System.out.println("Celkový seznam států:\n"+list.printWithFullRate());

        System.out.println("Zadej limit VAT sazby: ");
        Scanner sc = new Scanner(System.in);
        String st = sc.nextLine();
        if(st == "") limit = 20;
        else { try { limit = NumberFormat.getNumberInstance(locale).parse(st).doubleValue(); }

        catch (NumberFormatException e) {
            throw new StateException("Špatně zadaná hodnota limitu "+e.getLocalizedMessage());
        } catch (ParseException e) {
            throw new StateException("Špatně zadaná hodnota limitu "+e.getLocalizedMessage());
        }
        }

        System.out.println("Seznam států se základní sazbou VAT vyšší než "+ ft.format(limit) +" % a bez speciální sazby daně:\n"
                +list.printOverLimitWithFullRate(limit));

        // compares states by value of full rate (greater first)
        list.sortByFullRateValue(list.getListOfStatesOverLimit(limit));

        System.out.println("Seznam států se základní sazbou VAT vyšší než "+ ft.format(limit) +" % a bez speciální sazby daně:\n"
                +list.printOverLimitWithFullRateAndReducedRate(limit));

        System.out.println("====================");

        System.out.println("Sazba VAT "+ft.format(limit)+" % nebo nižší nebo používají speciální sazbu: "
                +list.getAbreviationsStatesUnderLimit());

        try {
            list.exportToFile(limit);
        } catch (StateException e) {
            throw new StateException(e.getLocalizedMessage());
        }
    }
}