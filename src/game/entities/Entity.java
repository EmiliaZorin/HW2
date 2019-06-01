package game.entities;

import utilities.Point;
import utilities.ValidationUtils;

import java.util.Observable;


public abstract class Entity extends Observable {
    private Point location;

    /**
     * Default Constructor
     */
    public Entity(){
        location=new Point(0,0);
    }

    /**
     * Constructor
     * @param location
     */
    public Entity(Point location){
        setLocation(location);
    }

    /**
     * get
     * @return location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * set
     * @param location
     * @throws IllegalArgumentException
     */
    public void setLocation(Point location) throws IllegalArgumentException {
        ValidationUtils.assertNotNull(location);
        this.location = new Point(location.getX(),location.getY());
    }
}
