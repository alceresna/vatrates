package com.engeto.vatrates;

import java.text.DecimalFormat;

public class State implements Comparable<State>{
    private String abreviation;
    private String name;
    private double fullRate;
    private double reducedRate;
    private boolean isSpecialRate;

    public State(String abreviation, String  name, double fullRate, double reducedRate, boolean isSpecialRate) {

        this.abreviation = abreviation;
        this.name =name;
        this.fullRate = fullRate;
        this.reducedRate = reducedRate;
        this.isSpecialRate = isSpecialRate;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFullRate() {
        return fullRate;
    }

    public void setFullRate(int fullRate) {
        this.fullRate = fullRate;
    }

    public double getReducedRate() {
        return reducedRate;
    }

    public void setReducedRate(int reducedRate) {
        this.reducedRate = reducedRate;
    }

    public boolean isSpecialRate() {
        return isSpecialRate;
    }

    public void setSpecialRate(boolean specialRate) {
        isSpecialRate = specialRate;
    }

    @Override
    public int compareTo(State o) {
        return (int)o.getFullRate() - (int)this.getFullRate();
    }

    public String toString(Boolean withReducedRate) {
        String str = "";
        DecimalFormat ft = new DecimalFormat("###.#");
        if(withReducedRate) {str += (getName()+" ("+getAbreviation()+"):   "+ft.format(getFullRate())+
                " % ("+ft.format(getReducedRate())+" %)");}
        else {
            str += (getName()+" ("+getAbreviation()+"): "+ft.format(getFullRate())+ " %");
        }
        return str;
    }
}
