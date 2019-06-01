package game.arena;

import game.entities.IMobileEntity;

public interface IArena {

    double getFriction();
    boolean isFinished(IMobileEntity me);

}
