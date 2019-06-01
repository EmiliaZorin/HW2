package game.states;

public class Disabled implements IState {
    /**
     * constructor
     */
    public Disabled(){}
    /**
     * void
     * @return string
     */
    @Override
    public String inform() {
       return "Disabled";
    }

}
