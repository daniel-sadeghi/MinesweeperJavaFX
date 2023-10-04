package com.dsadeghi.minesweeper;

public interface Game {
    /**
     * Check to see if the model is in "Win" state
     * @return
     */
    public boolean checkWin();

    /**
     * Initializes the model to a new game state
     */
    public void restart();

    /**
     * Manually set the win state of the game
     * @param value
     */
    public void setWin(boolean value);
}
