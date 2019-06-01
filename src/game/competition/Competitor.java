package game.competition;

import game.entities.IMobileEntity;

public interface Competitor extends IMobileEntity, Runnable, Cloneable {
    void run();
    void initRace();
    Object clone();
}
