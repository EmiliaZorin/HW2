package game.competition;

import java.lang.reflect.InvocationTargetException;

public interface ISkiCompetitionBuilder {
    void buildArena() throws  ClassNotFoundException,NoSuchMethodException,SecurityException,InstantiationException,IllegalAccessException, InvocationTargetException;
    void buildCompetitors() ;
    void buildMaxCompetitors();
    Competition getDefaultCompetition();
}
