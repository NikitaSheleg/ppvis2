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
    private FindNote findNote;

    public DeleteNote(FindNote findNote) {
        this.findNote = findNote;
    }

    private void setStudents(File file) {
        Sax sax = new Sax();
        sax.parse(file);
        students = sax.getStudents();
    }

    public void deleteStudentByName(String name, File file, JTable table) {
        Student student = findNote.findByName(name);
        students.remove(student);
        deleteFromTable(table,student);
        Dom.setStudents(students,file);
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
        List<Student>studentsForRemove = findNote.findByAverageMark(minMark,maxMArk);
        students.removeAll(studentsForRemove);
        for(Student student:studentsForRemove){
            deleteFromTable(table,student);
        }
        Dom.setStudents(students, file);
    }

    public void deleteByMarkAndExamName(File file, JTable table, double min, double max, String examName) {
        List<Student>studentsForRemove = findNote.findByMarkAndExamName(min,max,examName);
        students.removeAll(studentsForRemove);
        for(Student student:studentsForRemove){
            deleteFromTable(table,student);
        }
        Dom.setStudents(students, file);
    }

    public void deleteByGroup(File file, JTable table, int group) {
        List<Student>studentsForRemove = findNote.findByGroup(group);
        students.removeAll(studentsForRemove);
        for(Student student:studentsForRemove){
            deleteFromTable(table,student);
        }
        Dom.setStudents(students, file);
    }

}
