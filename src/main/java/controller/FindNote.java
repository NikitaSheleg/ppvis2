package controller;

import entity.Exam;
import entity.Student;
import util.Sax;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FindNote {

    List<Student> students;
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Student findByName(String name) {
        for (Student student : students) {
            if (student.getFullName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> findByGroup(int group) {
        List<Student> foundStudents = new ArrayList<>();
        for (Student student : students) {
            System.out.println("discharge0: " + student.getFullName());
            if (student.getGroup() == group) {
                foundStudents.add(student);
            }
        }
        return foundStudents;
    }

    public List<Student> findByAverageMark(double min, double max) {
        List<Student> foundStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getAverageMark() <= max && student.getAverageMark() >= min) {
                foundStudents.add(student);
            }
        }
        return foundStudents;
    }

    public List<Student> findByMarkAndExamName(double min, double max, String examName) {
        List<Student> foundStudents = new ArrayList<>();
        for (Student student : students) {
            for (Exam exam1 : student.getExams()) {
                if (exam1.getName().equals(examName) && exam1.getMark() > min && exam1.getMark() < max) {
                    foundStudents.add(student);
                }
            }
        }
        return foundStudents;
    }

    public List<Student> getStudents() {
        return students;
    }
}
