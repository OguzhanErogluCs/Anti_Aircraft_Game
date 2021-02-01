import org.junit.Test;
import javax.swing.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class UnitTest{

    /**
     * This method tests whether getPath function works true or not.
     */
    @Test
    public void testGetPath() {
        Board mockBoard = new Board();
        String result = mockBoard.getPath();
        assertThat("C:\\Users\\oguzh\\IdeaProjects\\Anti_Aircraft_Game-main", is(result));
    }

    /**
     * This method tests mouseclicks. When we click the left button of mouse, x and y coordinates of cursor must be >= 0 .
     * (Before click, they are -1.)
     */
    @Test
    public void testMouseClicked(){
        Board mockBoard = new Board();
        JButton buttonToSimulateClicking = new JButton();
        mockBoard.getL().add(buttonToSimulateClicking);
        MouseEvent clickEvent = new MouseEvent(buttonToSimulateClicking, MouseEvent.MOUSE_CLICKED, 1, 1, 1, 1, 1, true);
        mockBoard.mouseClicked(clickEvent);
        assertThat((mockBoard.getMouseX()>=0), is(true));
        assertThat((mockBoard.getMouseY()>=0), is(true));
    }

    /**
     * This method tests whether images are loaded succesfuly or not.
     * If the images loaded succesfuly, their values must not be null.
     */
    @Test
    public void testImageLoaded(){
        Board mockBoard = new Board();
        assertThat(mockBoard.getAircraftImage(), notNullValue());
        assertThat(mockBoard.getBullet(), notNullValue());
        assertThat(mockBoard.getTower(), notNullValue());
    }

    /**
     * This method tests whether code exceed the maximum value of aircraft array or not.
     */
    @Test
    public void testMaxAircrafts(){
        Aircraft aircraft_container = new Aircraft();
        for(int i=0;i<20;i++){
            aircraft_container.createAircraft(1,1,1,1);
        }
        assertThat(aircraft_container.getAircrafts().size()<=10, is(true));
    }

    /**
     * This method tests whether game over or not, if 3 aircrafts passes the board.
     */
    @Test
    public void testGameOver(){
        Aircraft aircraft_container = new Aircraft();
        List<Aircraft> passedAircrafts = new ArrayList<Aircraft>();
        for(int i=0;i<3;i++){
            aircraft_container.aircrafts.add(new Aircraft(1,100,1));
        }
        for(Aircraft a: aircraft_container.getAircrafts()){
            passedAircrafts = aircraft_container.checkPassings(1, passedAircrafts, a);
        }
        assertThat((aircraft_container.getGameOver()), is(true));
    }

}
