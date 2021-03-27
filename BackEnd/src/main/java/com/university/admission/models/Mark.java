package com.university.admission.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "marks")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "student")
    private Long student;

    @Column(name = "regional_exam_mark")
    private double regionalExamMark;

    @Column(name = "bac1_year")
    private String bac1Year;

    @Column(name = "national_exam_mark")
    private double nationalExamMark;

    @Column(name = "bac2_year")
    private String bac2Year;

    @Column(name = "semester1_mark")
    private double semester1ExamMark;

    @Column(name = "semester1_year")
    private String semester1Year;

    @Column(name = "semester2_mark")
    private double semester2ExamMark;

    @Column(name = "semester2_year")
    private String semester2Year;

    @Column(name = "semester3_mark")
    private double semester3ExamMark;

    @Column(name = "semester3_year")
    private String semester3Year;

    @Column(name = "semester4_mark")
    private double semester4ExamMark;

    @Column(name = "semester4_year")
    private String semester4Year;

    @Column(name = "semester5_mark")
    private double semester5ExamMark;

    @Column(name = "semester5_year")
    private String semester5Year;

    @Column(name = "semester6_mark")
    private double semester6ExamMark;

    @Column(name = "semester6_year")
    private String semester6Year;

    @Column(name = "semester7_mark")
    private double semester7ExamMark;

    @Column(name = "semester7_year")
    private String semester7Year;

    @Column(name = "semester8_mark")
    private double semester8ExamMark;

    @Column(name = "semester8_year")
    private String semester8Year;

    @Column(name = "semester9_mark")
    private double semester9ExamMark;

    @Column(name = "semester9_year")
    private String semester9Year;

    @Column(name = "semester10_mark")
    private double semester10ExamMark;

    @Column(name = "semester10_year")
    private String semester10Year;

    public Mark() {
    }

    public Mark(Long id, Long student, double regionalExamMark, String bac1Year, double nationalExamMark, String bac2Year, double semester1ExamMark, String semester1Year, double semester2ExamMark, String semester2Year, double semester3ExamMark, String semester3Year, double semester4ExamMark, String semester4Year, double semester5ExamMark, String semester5Year, double semester6ExamMark, String semester6Year, double semester7ExamMark, String semester7Year, double semester8ExamMark, String semester8Year, double semester9ExamMark, String semester9Year, double semester10ExamMark, String semester10Year) {
        this.id = id;
        this.student = student;
        this.regionalExamMark = regionalExamMark;
        this.bac1Year = bac1Year;
        this.nationalExamMark = nationalExamMark;
        this.bac2Year = bac2Year;
        this.semester1ExamMark = semester1ExamMark;
        this.semester1Year = semester1Year;
        this.semester2ExamMark = semester2ExamMark;
        this.semester2Year = semester2Year;
        this.semester3ExamMark = semester3ExamMark;
        this.semester3Year = semester3Year;
        this.semester4ExamMark = semester4ExamMark;
        this.semester4Year = semester4Year;
        this.semester5ExamMark = semester5ExamMark;
        this.semester5Year = semester5Year;
        this.semester6ExamMark = semester6ExamMark;
        this.semester6Year = semester6Year;
        this.semester7ExamMark = semester7ExamMark;
        this.semester7Year = semester7Year;
        this.semester8ExamMark = semester8ExamMark;
        this.semester8Year = semester8Year;
        this.semester9ExamMark = semester9ExamMark;
        this.semester9Year = semester9Year;
        this.semester10ExamMark = semester10ExamMark;
        this.semester10Year = semester10Year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public double getRegionalExamMark() {
        return regionalExamMark;
    }

    public void setRegionalExamMark(double regionalExamMark) {
        this.regionalExamMark = regionalExamMark;
    }

    public double getNationalExamMark() {
        return nationalExamMark;
    }

    public void setNationalExamMark(double nationalExamMark) {
        this.nationalExamMark = nationalExamMark;
    }

    public double getSemester1ExamMark() {
        return semester1ExamMark;
    }

    public void setSemester1ExamMark(double semester1ExamMark) {
        this.semester1ExamMark = semester1ExamMark;
    }

    public double getSemester2ExamMark() {
        return semester2ExamMark;
    }

    public void setSemester2ExamMark(double semester2ExamMark) {
        this.semester2ExamMark = semester2ExamMark;
    }

    public double getSemester3ExamMark() {
        return semester3ExamMark;
    }

    public void setSemester3ExamMark(double semester3ExamMark) {
        this.semester3ExamMark = semester3ExamMark;
    }

    public double getSemester4ExamMark() {
        return semester4ExamMark;
    }

    public void setSemester4ExamMark(double semester4ExamMark) {
        this.semester4ExamMark = semester4ExamMark;
    }

    public double getSemester5ExamMark() {
        return semester5ExamMark;
    }

    public void setSemester5ExamMark(double semester5ExamMark) {
        this.semester5ExamMark = semester5ExamMark;
    }

    public double getSemester6ExamMark() {
        return semester6ExamMark;
    }

    public void setSemester6ExamMark(double semester6ExamMark) {
        this.semester6ExamMark = semester6ExamMark;
    }

    public double getSemester7ExamMark() {
        return semester7ExamMark;
    }

    public void setSemester7ExamMark(double semester7ExamMark) {
        this.semester7ExamMark = semester7ExamMark;
    }

    public double getSemester8ExamMark() {
        return semester8ExamMark;
    }

    public void setSemester8ExamMark(double semester8ExamMark) {
        this.semester8ExamMark = semester8ExamMark;
    }

    public double getSemester9ExamMark() {
        return semester9ExamMark;
    }

    public void setSemester9ExamMark(double semester9ExamMark) {
        this.semester9ExamMark = semester9ExamMark;
    }

    public double getSemester10ExamMark() {
        return semester10ExamMark;
    }

    public void setSemester10ExamMark(double semester10ExamMark) {
        this.semester10ExamMark = semester10ExamMark;
    }

    public String getSemester1Year() {
        return semester1Year;
    }

    public void setSemester1Year(String semester1Year) {
        this.semester1Year = semester1Year;
    }

    public String getSemester2Year() {
        return semester2Year;
    }

    public void setSemester2Year(String semester2Year) {
        this.semester2Year = semester2Year;
    }

    public String getSemester3Year() {
        return semester3Year;
    }

    public void setSemester3Year(String semester3Year) {
        this.semester3Year = semester3Year;
    }

    public String getSemester4Year() {
        return semester4Year;
    }

    public void setSemester4Year(String semester4Year) {
        this.semester4Year = semester4Year;
    }

    public String getSemester5Year() {
        return semester5Year;
    }

    public void setSemester5Year(String semester5Year) {
        this.semester5Year = semester5Year;
    }

    public String getSemester6Year() {
        return semester6Year;
    }

    public void setSemester6Year(String semester6Year) {
        this.semester6Year = semester6Year;
    }

    public String getSemester7Year() {
        return semester7Year;
    }

    public void setSemester7Year(String semester7Year) {
        this.semester7Year = semester7Year;
    }

    public String getSemester8Year() {
        return semester8Year;
    }

    public void setSemester8Year(String semester8Year) {
        this.semester8Year = semester8Year;
    }

    public String getSemester9Year() {
        return semester9Year;
    }

    public void setSemester9Year(String semester9Year) {
        this.semester9Year = semester9Year;
    }

    public String getSemester10Year() {
        return semester10Year;
    }

    public void setSemester10Year(String semester10Year) {
        this.semester10Year = semester10Year;
    }

    public String getBac1Year() {
        return bac1Year;
    }

    public void setBac1Year(String bac1Year) {
        this.bac1Year = bac1Year;
    }

    public String getBac2Year() {
        return bac2Year;
    }

    public void setBac2Year(String bac2Year) {
        this.bac2Year = bac2Year;
    }
}
