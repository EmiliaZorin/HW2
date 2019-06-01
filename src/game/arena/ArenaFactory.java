package game.arena;

import game.enums.SnowSurface;
import game.enums.WeatherCondition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ArenaFactory {
    private String arenaType;

    /**
     * get arena
     * @param arenaType
     * @param arenaLen
     * @param surface
     * @param weatherCondition
     * @return IArena
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public IArena getArena(String arenaType, double arenaLen, SnowSurface surface, WeatherCondition weatherCondition)throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,InstantiationException ,InvocationTargetException{
        this.arenaType = arenaType;
        Class c = Class.forName("game.arena." + arenaType);
        Constructor constructor = c.getConstructor(new Class[]{double.class, SnowSurface.class, WeatherCondition.class});
        return (IArena)constructor.newInstance(arenaLen, surface, weatherCondition);
    }
}
