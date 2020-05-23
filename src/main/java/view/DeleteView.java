package view;

import controller.DeleteNote;
import controller.FindNote;
import controller.PageController;
import entity.Student;
import util.Sax;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DeleteView extends JFrame {
    public DeleteView(PageController pageController, JTable table, File file) {
        super("delete");
        JPanel panel = new JPanel();
        Sax sax = new Sax();
        FindNote findNote = new FindNote();
        sax.parse(file);
        findNote.setStudents(sax.getStudents());

        List<JRadioButton> buttonList = new ArrayList<>();
        JButton deleteButton = new JButton("delete");

        JTextField minMark = new JTextField(4);
        JTextField maxMark = new JTextField(4);
        JTextField data = new JTextField(15);


        DeleteNote deleteNote = new DeleteNote(findNote);
        deleteNote.setStudents();
        JRadioButton byName = new JRadioButton("by name");
        JRadioButton byAverageMark = new JRadioButton("by average mark");
        JRadioButton byMarkAndExamName = new JRadioButton("by mark and exam name");
        JRadioButton byGroup = new JRadioButton("by group");
        buttonList.add(byName);
        buttonList.add(byAverageMark);
        buttonList.add(byMarkAndExamName);
        buttonList.add(byGroup);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (JRadioButton radioButton : buttonList) {
                    if (radioButton.isSelected()) {
                        switch (radioButton.getText()) {
                            case "by name": {
                                JOptionPane.showMessageDialog(DeleteView.this,
                                        1);
                                deleteNote.deleteStudentByName(data.getText(), file, table);
                                break;
                            }
                            case "by group": {
                                JOptionPane.showMessageDialog(DeleteView.this,
                                        deleteNote.deleteByGroup(file, table, Integer.parseInt(data.getText())));

                                break;
                            }
                            case "by mark": {
                                JOptionPane.showMessageDialog(DeleteView.this,
                                        deleteNote.deleteStudentByAverageMark(file, table, Double.parseDouble(minMark.getText()), Double.parseDouble(maxMark.getText())));
                                break;
                            }
                            case "by mark and exam name": {
                                JOptionPane.showMessageDialog(DeleteView.this,
                                        deleteNote.deleteByMarkAndExamName(file, table, Double.parseDouble(minMark.getText()), Double.parseDouble(maxMark.getText()), data.getText()));
                                break;
                            }
                            default: {
                                JOptionPane.showMessageDialog(DeleteView.this,
                                        "такого нет");
                            }
                        }

                        pageController.setStudents(deleteNote.getStudents());
                    }
                }


            }
        });
        for (JRadioButton button : buttonList) {
            panel.add(button);
        }
//        sax.parse(file);
//        pageController.setStudents(sax.getStudents());
        panel.add(deleteButton);
        panel.add(new Label("min mark"));
        panel.add(minMark);
        panel.add(new Label("max mark"));
        panel.add(maxMark);
        //panel.add(examName);
        panel.add(new Label("any student data"));
        panel.add(data);

        add(panel);

        setSize(1500, 500);

        setVisible(true);

        setResizable(true);

    }
}
