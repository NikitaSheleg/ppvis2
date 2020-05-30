package entity;

import java.util.ArrayList;
import java.util.List;

public class Student {


    private String fullName;
    private int group;
    private List<Exam> exams = new ArrayList<Exam>();
    private double averageMark;


    public Student(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public double getAverageMark() {
        return averageMark;

    }

    public void setAverageMark() {
        double sum = 0;
        for (Exam exam : exams) {
            sum += exam.getMark();
        }
        this.averageMark = sum / exams.size();
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
