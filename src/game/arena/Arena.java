package game.arena;

import game.entities.IMobileEntity;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;
import utilities.ValidationUtils;

public abstract class Arena implements IArena {
    private double length;
    private SnowSurface surface;
    private WeatherCondition condition;

    /**
     * constructor
     * @param length
     * @param surface
     * @param condition
     */
    public Arena(double length, SnowSurface surface, WeatherCondition condition){
        setLength(length);
        setCondition(condition);
        setSurface(surface);
    }

    /**
     * get
     * @return length
     */
    public double getLength() {
        return length;
    }

    /**
     * set
     * @param length
     * @throws IllegalArgumentException
     */
    public void setLength(double length) throws IllegalArgumentException{
        ValidationUtils.assertNotNegative(length);
        this.length = length;
    }

    /**
     * get
     * @return surface
     */
    public SnowSurface getSurface() {
        return surface;
    }

    /**
     * set
     * @param surface
     * @throws IllegalArgumentException
     */
    public void setSurface(SnowSurface surface)throws IllegalArgumentException {
        ValidationUtils.assertNotNull(surface);
        this.surface = surface;
    }

    /**
     * get
     * @return condition
     */
    public WeatherCondition getCondition() {
        return condition;
    }

    /**
     *
     * @param condition
     * @throws IllegalArgumentException
     */
    public void setCondition(WeatherCondition condition)throws IllegalArgumentException {
        ValidationUtils.assertNotNull(condition);
        this.condition = condition;
    }

    /**
     * get
     * @return friction
     */
    public double getFriction(){
        return surface.getFriction();
    }

    /**
     * determines whether the racer has crossed the finish line
     * @param me
     * @return true\false
     */
    public boolean isFinished(IMobileEntity me){
        return me.getThisLocation().getX()>=getLength();

    }

    /**
     * void
     * @return name of the arena
     */
    public String toString(){
        return "WinterArena: " + getLength()+ ", " + getSurface() +", " + getCondition();
    }
}
