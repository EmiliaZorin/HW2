package game.states;

import java.util.Random;

public class Injured implements IState {
    private int injuryPlace;
    private IState StaticState;

    /**
     * constructor
     * @param finalY
     */
     public Injured(int finalY){
         destiny(finalY);
     }
    /**
     * void
     * @return string
     */
    @Override
    public String inform() {
        return "Injured";
    }

    /**
     * randomly picks place of injury
     * @param finalY
     */
   public void destiny(int finalY) {
        Random randomPlace = new Random();
        injuryPlace = randomPlace.nextInt(finalY);
        faith();
    }

    /**
     * randomly picks the next state of the competitor.
     */
    public void faith(){
        int number;
        Random randomNumber = new Random();
        number = randomNumber.nextInt(10);
        if(number<=5){
            StaticState = new Disabled();
        }
        else{
            return;
        }
    }
}
