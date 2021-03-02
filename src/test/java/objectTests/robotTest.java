package objectTests;

import Cards.MovementCard;
import Cards.TurningCard;
import com.badlogic.gdx.math.Vector2;
import objects.Robot;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.lwjgl.system.CallbackI;

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

    /**
     * Checks if setDirection() updates direction, and that only N, S, E or W is accepted.
     */
    @Test
    public void setDirectionShouldChangeDirection(){
        robot = new Robot(2,2);

        robot.setDirection("N");
        assertEquals("N", robot.getDir());

        robot.setDirection("M");
        assertEquals("N", robot.getDir());
    }

    /**
     * Checks if move() moves the Robot as it should
     */
    @Test
    public void checkIfMoveWorks(){
        robot = new Robot(0,0);

        // Checks vertical movement
        float initialYCoordinate = robot.getY();
        robot.setDirection("N");
        robot.move(3);
        assertEquals(initialYCoordinate + 3, robot.getY(), 0.1);

        // Check horisontal movement
        float initialXCoordinate = robot.getX();
        robot.setDirection("W");
        robot.move(3);
        assertEquals(initialXCoordinate + -3, robot.getX(), 0.1);
    }

    /**
     * Test moveBasedOnNextCard
     */
    @Test
    public void checkIfMoveBasedOnNextCardWorks(){
        robot = new Robot(0,0);
        robot.setDirection("N");

        // Checks if robot moves 1 up
        robot.addCard(new MovementCard(1,0));
        robot.moveBasedOnNextCard();
        assertEquals(1, robot.getY(), 0.1);

        // Checks if robot turns 1 time to the right
        robot.addCard(new TurningCard(true, false, 0));
        robot.moveBasedOnNextCard();
        assertEquals("E", robot.getDir());

        // Checks if robot can do a sequence of cards
        robot.addCard(new MovementCard(2,0));
        robot.addCard(new TurningCard(true, false, 0));
        robot.addCard(new MovementCard(3,0));

        Vector2 originalPosition = new Vector2(robot.getX(), robot.getY());
        robot.moveBasedOnNextCard();
        robot.moveBasedOnNextCard();
        robot.moveBasedOnNextCard();
        assertEquals(new Vector2(originalPosition.x + 2, originalPosition.y - 3), new Vector2(robot.getX(), robot.getY()));

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
