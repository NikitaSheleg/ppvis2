package controller;

import entity.Student;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PageController {
    private final JTable table;
    private int pageNumber;
    private AddNote addNote;
    private boolean last = false;
    private List<Student> students;

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public void setAddNote(AddNote addNote) {
        this.addNote = addNote;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageController(JTable table) {
        this.table = table;
    }

    public void scroll( int left, int right) {
        table.updateUI();
        ((DefaultTableModel) table.getModel()).setColumnCount(4);
        List<Student> subStudents;
        if (right >= students.size()) {
            subStudents = students.subList(left, students.size());
        } else {
            subStudents = students.subList(left, right);
        }
        int row = 0;
        ((DefaultTableModel) table.getModel()).setRowCount(1);
        for (Student student : subStudents) {
            addNote.addNewStudentInTable(table, row, student);
            row++;
        }
    }

    public void lastPage() {
        ((DefaultTableModel) table.getModel()).setRowCount(1);
        List<Student> subStudents = students.subList(students.size() - students.size() % 10, students.size());
        int row = 0;

        for (Student student : subStudents) {
            addNote.addNewStudentInTable(table, row, student);
            row++;
        }
        last = true;
    }

    public boolean isLast() {
        return last;
    }
}
