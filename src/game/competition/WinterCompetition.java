package game.competition;

import game.arena.IArena;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import utilities.ValidationUtils;

public abstract class WinterCompetition extends Competition{
    private League league;
    private Discipline discipline;
    private Gender gender;

    /**
     * Constructor
     * @param arena
     * @param maxCompetitors
     * @param league
     * @param discipline
     * @param gender
     */
    public WinterCompetition(IArena arena, int maxCompetitors,Discipline discipline,League league,Gender gender)
    {
        super(arena,maxCompetitors);
        setDiscipline(discipline);
        setGender(gender);
        setLeague(league);
    }

    /**
     * get
     * @return league
     */
    public League getLeague() {
        return league;
    }

    /**
     * set
     * @param league
     * @throws IllegalArgumentException
     */
    public void setLeague(League league)throws IllegalArgumentException {
        ValidationUtils.assertNotNull(league);
        this.league = league;
    }

    /**
     * get
     * @return discipline
     */
    public Discipline getDiscipline() {
        return discipline;
    }

    /**
     * set
     * @param discipline
     * @throws IllegalArgumentException
     */
    public void setDiscipline(Discipline discipline)throws IllegalArgumentException {
        ValidationUtils.assertNotNull(discipline);
        this.discipline = discipline;
    }

    /**
     * get
     * @return gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * set
     * @param gender
     */
    public void setGender(Gender gender){
        this.gender = gender;
    }


}
