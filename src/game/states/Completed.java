package game.states;

public class Completed implements IState {
    /**
     * constructor
     */
    public Completed(){}
    /**
     * void
     * @return string
     */
    @Override
    public String inform() {
        return "Completed";
    }


}
