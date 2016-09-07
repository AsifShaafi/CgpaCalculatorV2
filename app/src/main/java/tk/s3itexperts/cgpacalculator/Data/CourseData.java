package tk.s3itexperts.cgpacalculator.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Asif Imtiaz Shaafi, on 9/4/2016.
 * Email: a15shaafi.209@gmail.com
 */
public class CourseData {

    private static List<CourseStructure> courses = new ArrayList<>();

    static {
        courses.add(new CourseStructure(
                2, 1,
                Arrays.asList("Data Structures", "Data Structures Lab", "Digital Logic Design", "Digital Logic Design Lab",
                                "Society,Ethics and Technology", "Mathematics-III", "Electronic Devices & Circuits",
                        "EEE Lab", "Software Development-II"),
                Arrays.asList(3d, 1.5, 3d, 1.5, 3d, 3d, 3d, 1.5, 0.75),
                "2_1"
        ));
    }

    public static List<CourseStructure> getCourseList()
    {
        return courses;
    }

}
