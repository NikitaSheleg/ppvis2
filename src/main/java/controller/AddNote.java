package controller;

import entity.Exam;
import entity.Student;
import util.Dom;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AddNote {
    private List<Student> students = new ArrayList<>();
    private int row = 0;
    private PageController pageController;

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

    public void updateComboBox(JComboBox<String> comboBox, String name) {

        boolean isEquals = false;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(name)) {
                isEquals = true;
            }
        }
        if (!isEquals) {
            comboBox.addItem(name);
        }
    }



    public void addNewStudentInTable(JTable table, int row, Student student) {
        student.setAverageMark();
        int column = 2;
        table.setValueAt(student.getFullName(), row, 0);
        table.setValueAt(student.getGroup(), row, 1);
        for (Exam exam :student.getExams()) {
            if (column >= 2 && table.getModel().getColumnCount() <= student.getExams().size() * 2) {
                ((DefaultTableModel) table.getModel()).addColumn(122);
                ((DefaultTableModel) table.getModel()).addColumn(12);

            }
            table.setValueAt(exam.getName(), row, column);

            table.setValueAt(exam.getMark(), row, column + 1);
            column += 2;

        }
        if (table.getRowCount() <= students.size()) {
            ((DefaultTableModel) table.getModel()).addRow(new Vector());
        }

    }

    public void addInTable(JTable table) {

        for (Student student : students) {
            student.setAverageMark();
            int column = 2;
            table.setValueAt(student.getFullName(), row, 0);
            table.setValueAt(student.getGroup(), row, 1);
            for (Exam exam : student.getExams()) {
                if (column >= 2 && table.getModel().getColumnCount() <= student.getExams().size() * 2) {
                    ((DefaultTableModel) table.getModel()).addColumn(122);
                    ((DefaultTableModel) table.getModel()).addColumn(12);

                }
                table.setValueAt(exam.getName(), row, column);

                table.setValueAt(exam.getMark(), row, column + 1);
                column += 2;

            }

            row++;
            if (row == 10) {
                return;
            }
            if (table.getRowCount() < students.size()) {
                ((DefaultTableModel) table.getModel()).addRow(new Vector());
            }


        }
    }

    public void addNotes(List<String> strings, File file, JTable table, JComboBox<String> comboBox) {

        Student student = new Student(strings.get(0));
        student.setGroup(Integer.parseInt(strings.get(1)));
        List<Exam> exams = new ArrayList<>();
        for (int i = 2; i < strings.size() - 1; i += 2) {
            Exam exam = new Exam(strings.get(i), Integer.parseInt(strings.get(i + 1)));
            exams.add(exam);
            updateComboBox(comboBox, strings.get(i));
        }
        student.setExams(exams);
        students.add(student);
        Dom.setStudents(students, file);
        addNewStudentInTable(table, table.getRowCount() - 1, students.get(students.size()-1));

    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
