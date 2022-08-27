package com.engeto.vatrates;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ListOfStates {

    private static final String DELIMITER = "\t";
    private List<State> listOfStates = new ArrayList<>();
    private List<State> listOfStatesOverTreshold = new ArrayList<>();
    private List<State> listOfStatesUnderTreshold = new ArrayList<>();

    private double treshold = 0;

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
                double fullRate = Double.parseDouble(items[2].replace(",","."));
                double reducedRate = Double.parseDouble(items[3].replace(",","."));
                boolean isSpecialRate = Boolean.parseBoolean(items[4]);

                state = new State(abreviation,name,fullRate,reducedRate,isSpecialRate);
                list.addState(state);
            }
        }
        catch(FileNotFoundException e){
            throw new StateException("Soubor "+filename+" nebyl nalezen"+e.getLocalizedMessage());
        }
        return list;
    }

    public void addState(State state) {
        this.listOfStates.add(state);
    }

    private String printWithFullRate(List<State> list) {
        String str = new String("");
        DecimalFormat ft = new DecimalFormat("####");

        for (State state:list) {
            str += (state.getName()+" ("+state.getAbreviation()+"): "+ft.format(state.getFullRate())+
                    " %\n");
        }
        return str;
    }
    public String printWithFullRate() {

       return printWithFullRate(listOfStates);
    }

    private void generateListsUnderAndOver(double treshold) {
        for (State state:listOfStates) {
            if(!state.isSpecialRate() && state.getFullRate() > treshold) listOfStatesOverTreshold.add(state);
            else listOfStatesUnderTreshold.add(state);
            this.treshold = treshold;
        }
    }
    public List<State> getListOfStatesOverTreshold(double treshold) {
        if(this.treshold != treshold) generateListsUnderAndOver(treshold);
        return listOfStatesOverTreshold;
    }

    public List<State> getListOfStatesUnderTreshold(double treshold) {
        if(this.treshold != treshold) generateListsUnderAndOver(treshold);
        return listOfStatesUnderTreshold;
    }

    public String printOverTresholdWithFullRate(double treshold) {
       getListOfStatesOverTreshold(treshold);
       return printWithFullRate(listOfStatesOverTreshold);
    }

}
