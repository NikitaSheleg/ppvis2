package view;

import controller.AddNote;
import entity.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentFound {

    public StudentFound(Student student,JTable table) {


       ((DefaultTableModel) table.getModel()).setRowCount(1);
        AddNote addNote = new AddNote();
        addNote.addInStudentsList(student);
        addNote.addInTable(table);

    }

    public StudentFound(List<Student> students, JTable table) {
        ((DefaultTableModel) table.getModel()).setRowCount(1);
        AddNote addNote = new AddNote();
        addNote.setStudents(students);
        addNote.addInTable(table);

    }


}
