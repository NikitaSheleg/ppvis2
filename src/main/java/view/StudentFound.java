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


        int column = 2;
        table.setValueAt(student.getFullName(), 0, 0);
        table.setValueAt(student.getGroup(), 0, 1);
        for (Exam exam : student.getExams()) {
            if (column >= 2) {
                ((DefaultTableModel) table.getModel()).addColumn(122);
                ((DefaultTableModel) table.getModel()).addColumn(12);
            }
            table.setValueAt(exam.getName(), 0, column);
            table.setValueAt(exam.getMark(), 0, column + 1);
            column += 2;

        }

    }

    public StudentFound(List<Student> students, JTable table) {
        ((DefaultTableModel) table.getModel()).setRowCount(1);
        AddNote addNote = new AddNote();
        addNote.setStudents(students);
        addNote.addInTable(table);

    }


}
