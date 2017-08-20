package org.windbandu.populator;


import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.winbandu.populator.Populator;
import org.windbandu.Student;

public class PopulatorTest {
    @Test public void testPopulate() {
        System.out.println("testing populator bean now");
        Student student = new Student();
        assertNull(student.getFather());
        assertNull(student.getRollNumber());
        Populator.rigUp(student);
        assertNotNull(student.getFather());
        assertNotNull(student.getRollNumber());

    }
}
