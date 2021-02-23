package objectTests;

import objects.Robot;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

public class robotTest {
    Robot robot;
    @BeforeAll
    public void setUP(){

    }

    /**
     * Checks if setPosition() actually updates position
     */
    @Test
    public void setPositionShouldChangePosition(){
        robot = new Robot(2,2);
        robot.setPosition(3,4);

        assertEquals(3, robot.getX(), 0.1);
        assertEquals(4, robot.getY(), 0.1);
    }

    @Test
    public void turnLeftTest(){
        robot = new Robot(0,0);
        robot.setDirection("S");
        robot.turnLeft();
        assertEquals("E", robot.getDir());
    }

    @Test
    public void turnRightTest(){
        robot = new Robot(0,0);
        robot.setDirection("S");
        robot.turnRight();
        assertEquals("W", robot.getDir());
    }

    //TODO: test om move faktisk flytter

    //TODO: teste om robot for damage tokens når den tar skade

    //TODO: sjekke om robot mister life token ved 10 damage tokens
}
