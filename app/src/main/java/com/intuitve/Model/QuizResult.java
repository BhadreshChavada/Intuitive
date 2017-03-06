package com.intuitve.Model;

/**
 * Created by Bhadresh Chavada on 22-02-2017.
 */

public class QuizResult {

    String GSR2, GSR5, GAR10, GSR12, GSR15;
    int selectedPos, RandomPos;
    boolean result;

    public QuizResult() {
    }

    public String getGSR2() {
        return GSR2;
    }

    public void setGSR2(String GSR2) {
        this.GSR2 = GSR2;
    }

    public String getGSR5() {
        return GSR5;
    }

    public void setGSR5(String GSR5) {
        this.GSR5 = GSR5;
    }

    public String getGAR10() {
        return GAR10;
    }

    public void setGAR10(String GAR10) {
        this.GAR10 = GAR10;
    }

    public String getGSR12() {
        return GSR12;
    }

    public void setGSR12(String GSR12) {
        this.GSR12 = GSR12;
    }

    public String getGSR15() {
        return GSR15;
    }

    public void setGSR15(String GSR15) {
        this.GSR15 = GSR15;
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    public int getRandomPos() {
        return RandomPos;
    }

    public void setRandomPos(int randomPos) {
        RandomPos = randomPos;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
