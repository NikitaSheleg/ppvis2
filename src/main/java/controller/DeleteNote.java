package controller;

import entity.Exam;
import entity.Student;
import util.Dom;
import util.Sax;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DeleteNote {
    List<Student> students = new ArrayList<>();

    private void setStudents(File file) {
        Sax sax = new Sax();
        sax.parse(file);
        students = sax.getStudents();
    }

    public void deleteStudentByName(Student studentForDelete, File file, JTable table) {
        setStudents(file);
        for (Student student : students) {
            if (student.getFullName().equals(studentForDelete.getFullName())) {
                students.remove(student);
                deleteFromTable(table, student);
                Dom.setStudents(students, file);
                return;
            }
        }
    }


    private void deleteFromTable(JTable table, Student student) {
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 0).equals(student.getFullName())) {
                ((DefaultTableModel) table.getModel()).removeRow(i);
                return;
            }
        }
    }

    public void deleteStudentByAverageMark(File file, JTable table, double minMark, double maxMArk) {
        setStudents(file);
        for (Student student : students) {
            if (student.getAverageMark() > minMark && student.getAverageMark() < maxMArk) {
                deleteFromTable(table, student);
                students.remove(student);
                break;
            }
        }
        Dom.setStudents(students, file);
    }

    public void deleteByMarkAndExamName(File file, JTable table, double min, double max, String examName) {
        setStudents(file);
        for (Student student : students) {
            for (Exam exam1 : student.getExams()) {
                if (exam1.getName().equals(examName) && exam1.getMark() > min && exam1.getMark() < max) {
                    deleteFromTable(table, student);
                    students.remove(student);
                    break;
                }
            }
            break;
        }
        Dom.setStudents(students, file);
    }

    public void deleteByGroup(File file, JTable table, int group) {
        setStudents(file);
        for (Student student : students) {
            if (student.getGroup() == group) {
                deleteFromTable(table, student);
                students.remove(student);
                break;
            }
        }
        Dom.setStudents(students, file);
    }

}
