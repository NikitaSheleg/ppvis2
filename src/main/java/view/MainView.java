package view;

import controller.AddNote;
import controller.PageController;
import entity.Exam;
import entity.Student;
import util.Sax;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MainView extends JFrame {
    private JFileChooser fileChooser;
    JPanel main;
    JMenuBar menuBar;

    JButton addStudentAndExams;

    JButton findStudents;
    JButton deleteButton;
    DefaultTableModel tableModel;


    public MainView() {
        super("second");
        this.main = new JPanel();
        setContent();
    }

    private void setContent() {
        if (main != null)
            main.removeAll();

        this.menuBar = new JMenuBar();
        this.addStudentAndExams = new JButton("open window to add");
        addStudentAndExams.setPreferredSize(new Dimension(300, 300));
        this.findStudents = new JButton("open window to find");
        findStudents.setPreferredSize(new Dimension(300, 300));
        this.deleteButton = new JButton("delete");
        this.tableModel = new DefaultTableModel(1, 2);
        menuBar.add(createFileMenu(tableModel, deleteButton, addStudentAndExams, findStudents, main));
        setJMenuBar(menuBar);
        setResizable(true);
        setSize(2000, 600);
        add(main);
        setVisible(true);
    }

    private void addInPanel(DefaultTableModel tableModel, JButton deleteButton, JButton addStudentAndExams, JButton findStudents, JPanel main) {
        main.removeAll();
        JComboBox<String> exams = new JComboBox<>();
        JComboBox<String> groups = new JComboBox<>();
        Sax sax = new Sax();
        JTable table = new JTable(tableModel);
        AddNote addNote = new AddNote();
        PageController pageController = new PageController(table);
        JButton right = new JButton("right");
        JButton left = new JButton("left");
        JButton last = new JButton("last");
        sax.parse(fileChooser.getSelectedFile());
        addNote.setStudents(sax.getStudents());
        deleteButton.addActionListener(e -> new DeleteView(pageController, table, fileChooser.getSelectedFile()));

        addStudentAndExams.addActionListener(e -> new StudentsAddView(fileChooser.getSelectedFile(), table, exams, pageController));

        findStudents.addActionListener(e -> new StudentsFindView(pageController, table, sax.getStudents()));


        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextField studentsFullName = new JTextField(25);
        studentsFullName.setPreferredSize(new Dimension(200, 100));


        for (Student student : sax.getStudents()) {
            for (Exam exam : student.getExams()) {
                addNote.updateComboBox(exams, exam.getName());
            }
            addNote.updateComboBox(groups, String.valueOf(student.getGroup()));
        }
        AtomicInteger pageNum = new AtomicInteger(0);
        addNote.addInTable(table);

        AtomicInteger rightIndex = new AtomicInteger(10);
        AtomicInteger leftIndex = new AtomicInteger(0);

        pageController.setAddNote(addNote);

        right.addActionListener(e -> {
            pageController.setStudents(sax.getStudents());
            if (pageNum.get() < sax.getStudents().size() / 10) {
                rightIndex.addAndGet(10);
                leftIndex.addAndGet(10);
                pageNum.getAndIncrement();
                pageController.setPageNumber(pageNum.get());
                pageController.scroll(leftIndex.get(), rightIndex.get());
            }
        });
        left.addActionListener(e -> {
            pageController.setStudents(sax.getStudents());
            if (pageController.isLast()) {
                leftIndex.set((sax.getStudents().size() - sax.getStudents().size() % 10) - 10);
                rightIndex.set((sax.getStudents().size() - sax.getStudents().size() % 10));
                pageNum.set((sax.getStudents().size() - sax.getStudents().size() % 10) / 10);
                pageController.setLast(false);
            } else if (pageNum.get() > 0 && !pageController.isLast()) {
                leftIndex.addAndGet(-10);
                rightIndex.addAndGet(-10);
            }
            if (pageNum.get() > 0) {
                pageController.scroll(leftIndex.get(), rightIndex.get());
                pageNum.getAndDecrement();
                pageController.setPageNumber(pageNum.get());
            }
        });
        last.addActionListener(e -> {
            pageController.setStudents(sax.getStudents());
            pageController.lastPage();
        });
        main.add(table);

        main.add(addStudentAndExams);
        main.add(findStudents);
        main.add(deleteButton);
        main.add(left);
        main.add(right);
        main.add(last);
        main.add(exams);
        main.add(groups);
        main.updateUI();

    }

    private JMenu createFileMenu(DefaultTableModel tableModel, JButton deleteButton, JButton addStudentAndExams, JButton findStudents, JPanel main) {
        JMenu file = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Открыть");
        JMenuItem create = new JMenuItem("Create");
        file.add(open);
        file.add(create);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Сохранение файла");
                fileChooser.showDialog(null, "onfa");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                // setContent();
                addInPanel(tableModel, deleteButton, addStudentAndExams, findStudents, main);


            }
        });

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Сохранение файла");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "XML", "xml");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                    JOptionPane.showMessageDialog(MainView.this,
                            "Файл '" + fileChooser.getSelectedFile() +
                                    " ) сохранен");
                String rootTagForXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<Students>\n" +
                        "    \n" +
                        "</Students>";
                try {
                    FileOutputStream outputStream = new FileOutputStream(fileChooser.getSelectedFile());
                    outputStream.write(rootTagForXml.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                setContent();
                addInPanel(tableModel, deleteButton, addStudentAndExams, findStudents, main);
            }
        });
        return file;
    }

    public static void main(String[] args) {
        new MainView();
    }
}
