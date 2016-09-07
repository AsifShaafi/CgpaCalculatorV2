package tk.s3itexperts.cgpacalculator.Data;

import java.util.List;

/**
 * Created by Asif Imtiaz Shaafi, on 9/4/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class CourseStructure {

    private List<String> courseTitles;
    private List<Double> courseCredits;
    private String webPageNameExtenction;
    private int year, semester;

    public CourseStructure(int year, int semester, List<String> courseTitles, List<Double> courseCredits, String webPageNameExtenction) {
        this.courseTitles = courseTitles;
        this.courseCredits = courseCredits;
        this.webPageNameExtenction = webPageNameExtenction;
        this.year = year;
        this.semester = semester;
    }

    public List<String> getCourseTitles() {
        return courseTitles;
    }

    public List<Double> getCourseCredits() {
        return courseCredits;
    }

    public String getWebPageNameExtenction() {
        return webPageNameExtenction;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }
}
