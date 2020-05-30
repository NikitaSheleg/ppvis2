package util;

import entity.Exam;
import entity.Student;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sax {
    private String fullName;
    private String examName;
    private int group;
    private int examMark;
    private String endEl;
    private final List<Student> students = new ArrayList<>();
    private final List<Exam> exams = new ArrayList<>();
    private Student student;

    public void parse(File file) {
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                boolean bfname = false;
                boolean bgroup = false;
                boolean bExamMark = false;
                boolean bExamName = false;

                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("FULLNAME")) {
                        bfname = true;
                    }
                    if (qName.equalsIgnoreCase("GROUP")) {
                        bgroup = true;
                    }
                    if (qName.equalsIgnoreCase("EXAMMARK")) {
                        bExamMark = true;
                    }
                    if (qName.equalsIgnoreCase("EXAMNAME")) {
                        bExamName = true;
                    }
                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {
                    endEl = qName;
                }

                public void characters(char[] ch, int start, int length) throws SAXException {

                    if (bfname) {
                        System.out.println("FULLTNAME: " + new String(ch, start, length));
                        setFullName(new String(ch, start, length));

                        bfname = false;
                    }


                    if (bgroup) {
                        System.out.println("GROUP : " + new String(ch, start, length));
                        setGroup(Integer.parseInt(new String(ch, start, length)));
                        bgroup = false;
                        student = new Student(getFullName());
                        student.setGroup(getGroup());

                        students.add(student);
                    }

                    if (bExamMark) {
                        System.out.println("EXAMMARK : " + new String(ch, start, length));
                        setExamMark(Integer.parseInt(new String(ch, start, length)));
                        student.addExam(new Exam(getExamName(), getExamMark()));
                        student.setAverageMark();
                        bExamMark = false;
                    }
                    if (bExamName) {
                        System.out.println("EXAMNAME : " + new String(ch, start, length));
                        setExamName(new String(ch, start, length));
                        bExamName = false;

                    }

                }

            };

            saxParser.parse(file.getCanonicalPath(), handler);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudents(Student student) {
        this.students.add(student);
    }

    public String getEndElement() {
        return endEl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getExamMark() {
        return examMark;
    }

    public void setExamMark(int examMark) {
        this.examMark = examMark;
    }
}
