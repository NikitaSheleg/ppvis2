package util;

import entity.Exam;
import entity.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Dom {
    private static List<Student> students = new ArrayList<Student>();

    public static List<Student> getStudents() {
        return students;
    }


    private static Node createStudentElement(Document doc, String name,
                                             String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }

    private static Node createStudent(Document document, String fUllName, List<Exam> exams, Integer gorup) {
        Element student = document.createElement("student");

        student.appendChild(createStudentElement(document, "fullName", fUllName));

        student.appendChild(createStudentElement(document, "group", gorup.toString()));
        for (Exam exam : exams) {
            student.appendChild(createStudentElement(document, "examName", exam.getName()));
            student.appendChild(createStudentElement(document, "examMark", exam.getMark().toString()));

        }
        return student;
    }

    public static void setStudents(List<Student> students, File myFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElementNS("", "Students");
            document.appendChild(root);
            for (Student student : students) {
                root.appendChild(createStudent(document, student.getFullName(), student.getExams(), student.getGroup()));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transf = null;
            try {
                transf = transformerFactory.newTransformer();
                transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transf.setOutputProperty(OutputKeys.INDENT, "yes");
                transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(document);

                //File myFile = new File("src/main/resources/studentsJustForTest.xml");
                StreamResult file = new StreamResult(myFile);

                transf.transform(source, file);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
