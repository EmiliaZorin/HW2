package game.entities;

import utilities.Point;
import utilities.ValidationUtils;

public class MobileEntity extends Entity implements IMobileEntity{
    private double maxSpeed, acceleration=0, speed;

    /**
     * Constructor
     * @param maxSpeed
     * @param acceleration
     */
    public MobileEntity(double maxSpeed, double acceleration){
        setMaxSpeed(maxSpeed);
        setAcceleration(acceleration);
    }

    /**
     * get
     * @return maxSpeed
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * set
     * @param maxSpeed
     * @throws IllegalArgumentException
     */
    public void setMaxSpeed(double maxSpeed) throws IllegalArgumentException{
        ValidationUtils.assertNotNegative(maxSpeed);
        this.maxSpeed = maxSpeed;
    }

    /**
     * get
     * @return acceleration
     */
    public double getAcceleration() {
        return acceleration;
    }

    /**
     * set
     * @param acceleration
     * @throws IllegalArgumentException
     */
    public void setAcceleration(double acceleration) throws IllegalArgumentException{
        ValidationUtils.assertNotNegative(maxSpeed);
        this.acceleration += acceleration;
    }

    /**
     * get
     * @return speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * set
     * @param speed
     * @throws IllegalArgumentException
     */
    public void setSpeed(double speed) throws IllegalArgumentException{
        ValidationUtils.assertNotNegative(speed);
        this.speed = speed;
    }

    /**
     * Calculating the new speed in each turn, moving the competitor and setting he`s new location.
     * @param friction
     */
    public synchronized void move(double friction){
        double new_speed=speed+(1-friction)*getAcceleration();
        if(new_speed>=maxSpeed){
            setSpeed(maxSpeed);
        }
        else {
            setSpeed(new_speed);
        }
        double new_x=getThisLocation().getX();
        double significant_len = 0.5;
        for(double i=0;i<new_speed;i=i+significant_len){
            new_x +=significant_len;
            Point new_point = new Point(new_x, 0);
            setLocation(new_point);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * get
     * @return location.
     */
    public Point getThisLocation(){
        return getLocation();
    }
}

