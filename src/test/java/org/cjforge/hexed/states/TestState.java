package org.cjforge.hexed.states;

import org.newdawn.slick.state.BasicGameState;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public abstract class TestState extends BasicGameState{

    private final int stateId;

    protected TestState() {
        stateId = StateIds.next();
    }



    @Override
    public int getID() {
        return stateId;
    }
}
