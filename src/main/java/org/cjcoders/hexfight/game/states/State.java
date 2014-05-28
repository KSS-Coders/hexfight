package org.cjcoders.hexfight.game.states;

import org.cjcoders.hexfight.game.Player;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public enum State {
    MAIN_MENU(0), GAME_INTRO(1), KICK(2), PREPARE_GAME(3), PAUSE(0), TURN(4);

    private int code;

    public static int turnStateID(Player player){
        return TURN.getCode() + player.getID();
    }

    public int getCode() {
        return code;
    }

    private State(int code){
        this.code = code;
    }
}
