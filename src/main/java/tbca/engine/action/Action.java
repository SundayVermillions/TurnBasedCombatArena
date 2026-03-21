package tbca.engine.action;

import tbca.engine.GameState;
import tbca.ui.Ui;

public interface Action {
    ActionType getType();

    void execute(Ui ui, GameState gameState);

}
