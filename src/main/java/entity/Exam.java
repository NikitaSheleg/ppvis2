package entity;

import java.util.Objects;

public class Exam {
    private String name;
    private Integer mark;
public Exam(){

}

    public void setName(String name) {
        this.name = name;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Exam(String name, Integer mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }


    public Integer getMark() {
        return mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exam)) return false;
        Exam exam = (Exam) o;
        return getName().equals(exam.getName()) &&
                getMark().equals(exam.getMark());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMark());
    }
}
