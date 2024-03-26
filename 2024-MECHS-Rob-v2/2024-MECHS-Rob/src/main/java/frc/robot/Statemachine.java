package frc.robot;
import frc.robot.Robot;

public class Statemachine {
    public State currenState;
    public enum State{
        MANUAL,
        INTBACKUP,
        NOTESEPARATION,
        SHOOTERWINDUP,
        SHOOTNOTE;
    
    }
    Statemachine (){
        this.currenState = State.MANUAL;
    }

    public void execute_Manual(Robot robot){

    }
    
}

