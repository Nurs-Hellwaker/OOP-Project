package Models;

import Enums.LetterGrade;

import java.io.Serializable;


public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;

    private double att1;
    private double att2;
    private double finalExam;

    public Mark() {
        this.att1 = 0;
        this.att2 = 0;
        this.finalExam = 0;
    }

    public Mark(double att1, double att2, double finalExam) {
        this.att1      = att1;
        this.att2      = att2;
        this.finalExam = finalExam;
    }


    public double getAtt1()                 { return att1; }
    public void   setAtt1(double att1)      { this.att1 = att1; }

    public double getAtt2()                 { return att2; }
    public void   setAtt2(double att2)      { this.att2 = att2; }

    public double getFinalExam()                    { return finalExam; }
    public void   setFinalExam(double finalExam)    { this.finalExam = finalExam; }


    public double getTotalScore() {
        return att1 + att2 + finalExam;
    }

    public double getGradePoints() {
        double total = getTotalScore();
        if (total >= 95) return 4.0;
        if (total >= 90) return 3.67;
        if (total >= 85) return 3.33;
        if (total >= 80) return 3.0;
        if (total >= 75) return 2.67;
        if (total >= 70) return 2.33;
        if (total >= 65) return 2.0;
        if (total >= 60) return 1.67;
        if (total >= 55) return 1.33;
        if (total >= 50) return 1.0;
        return 0.0;
    }

    public LetterGrade getLetterGrade() {
        double total = getTotalScore();
        if (total >= 95) return LetterGrade.A;
        if (total >= 90) return LetterGrade.A_MINUS;
        if (total >= 85) return LetterGrade.B_PLUS;
        if (total >= 80) return LetterGrade.B;
        if (total >= 75) return LetterGrade.B_MINUS;
        if (total >= 70) return LetterGrade.C_PLUS;
        if (total >= 65) return LetterGrade.C;
        if (total >= 60) return LetterGrade.C_MINUS;
        if (total >= 55) return LetterGrade.D_PLUS;
        if (total >= 50) return LetterGrade.D;
        return LetterGrade.F;
    }

    public boolean isPassed() {
        return getTotalScore() >= 50;
    }

    @Override
    public String toString() {
        return String.format("ATT1=%.1f | ATT2=%.1f | Final=%.1f | Total=%.1f (%s)",
                att1, att2, finalExam, getTotalScore(), getLetterGrade());
    }
}
