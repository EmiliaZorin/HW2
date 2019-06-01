package game.arena;

import game.enums.SnowSurface;
import game.enums.WeatherCondition;

public class SummerArena extends Arena{
    /**
     * constructor
     * @param length
     * @param surface
     * @param condition
     */
    public SummerArena(double length, SnowSurface surface, WeatherCondition condition){
        super(length,surface,condition);
    }

    /**
     *  void
     * @return string
     */
    public String toString(){
        return "Summer Arena: " + getLength() + ", " + getSurface() + ","
                + getCondition();
    }
}
