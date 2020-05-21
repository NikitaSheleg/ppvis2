package view;

import controller.AddNote;
import controller.FindNote;
import entity.Exam;
import entity.Student;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
