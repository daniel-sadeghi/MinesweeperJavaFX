package com.dsadeghi.minesweeper;

/**
 * A square object that represents the state of a tile on the game board for MineSweeper. Numeric value is used for the contents of a tile.
 * A negative value is used to represent a bomb. 0 represents an empty tile. The numbers 1-8 tell you how many adjacent bombs there are.
 */
public class Square {
    private static final int MAX_VALUE = 8;
    private boolean isFlagged;
    private boolean isRevealed;
    private int value;


    public Square() {
        value = 0;
    }

    public Square(int value) {
        this.value = value;
    }

    /**
     * Sets the value of the square to the given number. Must provide a no greater than 8.
     * @param n
     */
    public void setValue(int n) {
        if (n > MAX_VALUE) {
            throw new IllegalArgumentException();
        }

        value = n;
    }

    /**
     * Returns true if the square contains a bomb. Works by checking if the square contains a negative value.
     * @return
     */
    public boolean isMine() {
        return value < 0;
    }

    public boolean setFlag(boolean isFlagged) {
         return this.isFlagged = isFlagged;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public int getValue() {
        return value;
    }

    /**
     * "Reveals" the square by setting the property to true
     */
    public void reveal() {
        isRevealed = true;
    }

    public boolean isRevealed() {
        return isRevealed;
    }


}
