package view;

import controller.AddNote;
import controller.PageController;
import util.Sax;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StudentsAddView extends JFrame {


    public StudentsAddView(File file, JTable table, JComboBox<String> comboBox, PageController pageController) {
        super("students");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        JTextField studentsFullName = new JTextField(20);
        JTextField studentsGroup = new JTextField(6);
        DefaultTableModel tableModel = new DefaultTableModel(1,2);
        JTable examsTable = new JTable(tableModel);
        JButton addRowInTable = new JButton("+");
        JButton addStudentInfo = new JButton("add new student");

        Sax sax = new Sax();
        sax.parse(file);

        addStudentInfo.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                AddNote addNote = new AddNote();
                pageController.lastPage();
                List<String> data = new ArrayList<>();
                data.add(studentsFullName.getText());
                data.add(studentsGroup.getText());
                for (int i = 0; i < examsTable.getRowCount(); i++) {
                    data.add(examsTable.getValueAt(i, 0).toString());
                    data.add(examsTable.getValueAt(i, 1).toString());
                }
                addNote.setStudents(sax.getStudents());
                addNote.addNotes(data, file, table, comboBox);
                sax.parse(file);
                pageController.setStudents(sax.getStudents());

            }
        });


        addRowInTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Vector<String>());
            }
        });
        studentsFullName.setPreferredSize(new Dimension(300, 40));
        panel.add(new Label("exam name and mark"));
        panel.add(examsTable);
        panel.add(addRowInTable);
        panel.add(new Label("full name"));
        panel.add(studentsFullName);
        panel.add(new Label("group"));
        panel.add(studentsGroup);
        panel.add(addStudentInfo);
        add(panel);
        setSize(1500, 500);
        setVisible(true);
        setResizable(true);
    }
}
