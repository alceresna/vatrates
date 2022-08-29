package com.engeto.vatrates;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class ListOfStates {

    private static final String DELIMITER = "\t";
    public static final Locale locale = new Locale("cs","CZ");
    private List<State> listOfStates = new ArrayList<>();
    private List<State> listOfStatesOverLimit = new ArrayList<>();
    private List<State> listOfStatesUnderLimit = new ArrayList<>();

    private double limit = 0;

    // reads items from file and loads them to list
    public static ListOfStates importFromFile(String filename) throws StateException {

        ListOfStates list = new ListOfStates();
        String line = "";
        State state;
        String[] items;

        try(Scanner sc = new Scanner(new File(filename))){

            while(sc.hasNextLine()) {
                line = sc.nextLine();
                items = line.split(DELIMITER);
                String abreviation = items[0];
                String name = items[1];
                double fullRate = NumberFormat.getNumberInstance(locale).parse(items[2]).doubleValue();
                double reducedRate = NumberFormat.getNumberInstance(locale).parse(items[3]).doubleValue();
                boolean isSpecialRate = Boolean.parseBoolean(items[4]);

                state = new State(abreviation,name,fullRate,reducedRate,isSpecialRate);
                list.addState(state);
            }
        }
        catch(FileNotFoundException e){
            throw new StateException("Soubor "+filename+" nebyl nalezen"+e.getLocalizedMessage());
        }
        catch (ParseException e) {
            throw new StateException("Nesprávný formát hodnoty sazby v souboru "+filename+" "+e.getLocalizedMessage());
        }
        return list;
    }

    // creates lines from list and writes them to file
    public void exportToFile(double limit) throws StateException {

        DecimalFormat ft = new DecimalFormat("###.#");
        String filename = "vat-over-"+ft.format(limit)+".txt";

        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            writer.println("Seznam států se základní sazbou VAT vyšší než "+ft.format(limit)+" % a bez speciální sazby daně:\n");
            for (State state:listOfStatesOverLimit) {
                writer.println(state.toString(true));
            }
            writer.println("====================");
            writer.println("Sazba VAT "+ft.format(limit)+" % nebo nižší nebo používají speciální sazbu: "+getAbreviationsStatesUnderLimit());
        } catch (IOException e) {
            throw new StateException("Nepodařil se zápis do souboru "+filename+" "+e.getLocalizedMessage());
        }
    }
    public void addState(State state) {
        this.listOfStates.add(state);
    }

    public String printWithFullRate(List<State> list) {
        String str = "";
        DecimalFormat ft = new DecimalFormat("####");

        for (State state:list) {
            str += state.toString(false)+"\n";

        }
        return str;
    }
    public String printWithFullRate() {

       return printWithFullRate(listOfStates);
    }

    public String printWithFullRateAndReducedRate(List<State> list) {
        String str = "";
        DecimalFormat ft = new DecimalFormat("####");

        for (State state:list) {
            str += state.toString(true)+"\n";
        }
        return str;
    }

    private void generateListsUnderAndOver(double limit) {
        for (State state:listOfStates) {
            if(!state.isSpecialRate() && state.getFullRate() > limit) listOfStatesOverLimit.add(state);
            else listOfStatesUnderLimit.add(state);
            this.limit = limit;
        }
    }
    public List<State> getListOfStatesOverLimit(double limit) {
        if(this.limit != limit) generateListsUnderAndOver(limit);
        return listOfStatesOverLimit;
    }

    public List<State> getListOfStatesUnderLimit(double limit) {
        if(this.limit != limit) generateListsUnderAndOver(limit);
        return listOfStatesUnderLimit;
    }

    public String printOverLimitWithFullRate(double limit) {
       getListOfStatesOverLimit(limit);
       return printWithFullRate(listOfStatesOverLimit);
    }

    public String printOverLimitWithFullRateAndReducedRate(double limit) {
        getListOfStatesOverLimit(limit);
        return printWithFullRateAndReducedRate(listOfStatesOverLimit);
    }

    public String getAbreviationsStatesUnderLimit() {
        String str = "";
        for (State state:listOfStatesUnderLimit) {
            str += ", "+state.getAbreviation();
        }
        str = str.replaceFirst(", ","");
        return str;
    }

    // compares states by value of full rate (greater first)
    public void sortByFullRateValue(List<State> list) {
        Collections.sort(list);
    }
}
