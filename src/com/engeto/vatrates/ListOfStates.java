package com.engeto.vatrates;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListOfStates {

    private static final String DELIMITER = "\t";
    private List<State> listOfStates = new ArrayList<>();

    public static ListOfStates importFromFile(String filename) throws StateException {

        ListOfStates list = new ListOfStates();
        String line;
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

    public String printWithFullRate() {

        String str = new String("");

        for (State state:listOfStates) {
            str += (state.getName()+" ("+state.getAbreviation()+"): "+state.getFullRate()+
                    " %\n");
        }
        return str;
    }

}
