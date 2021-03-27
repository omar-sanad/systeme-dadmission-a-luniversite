package com.university.admission.models;

public class SemesterMark {
    private String semester;
    private float mark;
    private String year;
    private String diplome;

    public SemesterMark(String semester, float mark, String year, String diplome) {
        this.semester = semester;
        this.mark = mark;
        this.year = year;
        this.diplome = diplome;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }
}
