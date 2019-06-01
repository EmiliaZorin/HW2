package game.arena;

import game.enums.SnowSurface;
import game.enums.WeatherCondition;

public class WinterArena extends Arena{
    /**
     * constructor
     * @param length
     * @param surface
     * @param condition
     */
    public WinterArena(double length, SnowSurface surface, WeatherCondition condition){
        super(length,surface,condition);
    }

    /**
     * void
     * @return string
     */
    public String toString(){
        return "Winter Arena: " + getLength() + ", " + getSurface() + ","
                + getCondition();
    }
}
