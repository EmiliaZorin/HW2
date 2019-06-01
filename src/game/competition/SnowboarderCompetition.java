package game.competition;

import game.arena.IArena;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

public class SnowboarderCompetition extends WinterCompetition{
    /**
     * Constructor
     * @param arena
     * @param maxCompetitors
     * @param league
     * @param discipline
     * @param gender
     */
    public SnowboarderCompetition(IArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender){
        super(arena,maxCompetitors,discipline,league,gender);
    }

    /**
     * void
     * @return information about the snowboarder competitor.
     */
    public String toString(){
        return "SnowBoarder Competition: " +getArena().getClass().getSimpleName() + ", " + getMaxCompetitors() + ", " + getDiscipline() + ","
                + getLeague() + ", " + getGender();
    }
}
