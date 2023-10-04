package com.dsadeghi.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Holds the state for the Minesweeper grid. Uses Square objects.
 * The class stores coordinates corresponding to each square. The "origin" of the grid is (0,0). Conventionally, it should be in the top left.
 * Conventionally, a coordinate (x,y) represents the coorinate x horizontal steps right of the origin and y vertical steps down.
 *
 * Invariants: The grid is expected to be initialized with squares in every coordinate. The methods rely on this assumption.
 */
public class MineGrid implements Game{
    protected static final int WIDTH = 20;
    protected static final int HEIGHT = 15;
    protected int numMines;
    protected int revealCount;
    protected boolean didWin;
    private Square[][] grid = new Square[WIDTH][HEIGHT];

    public MineGrid(int numMines) {
        int[][] randGrid = generateRandomGrid(numMines);
        addAll(randGrid);
    }

    /**
     * Gets the value of the cell at the given coordinates. If the value is negative, then there is a mine. If it is a positive integer n, then there are n adjacent mines. If 0, there are no adjacent mines.
     * Throws an exception if the coordinates are out of bounds.
     * @param x
     * @param y
     * @return
     */
    protected Square getSquare(int x, int y) {
        return grid[x][y];
    }

    @Override
    public boolean checkWin() {
        return revealCount == (HEIGHT * WIDTH - numMines);
    }

    /**
     * A helper method for initializing our grid. Generates a random 2d array of integers to be translated into square objects for our game state.
     * In this format, a negative number means a mine. Any non negative number represents the number of adjacent mines
     * @param numMines
     * @return
     */
    protected int[][] generateRandomGrid(int numMines) {
        int[][] numGrid = new int[WIDTH][HEIGHT];

        Random random = new Random();
        int count = 0;
        int randX;
        int randY;
        while (count < numMines) {
            randX = random.nextInt(WIDTH);
            randY = random.nextInt(HEIGHT);

            if (numGrid[randX][randY] < 0) {
                continue;
            }

            numGrid[randX][randY] = -9;

            //Increment coordinates to the left and right
            if ( (randX-1) >= 0) {
                numGrid[randX-1][randY] += 1;
            }
            if ( (randX+1) <= WIDTH - 1) {
                numGrid[randX+1][randY] += 1;
            }


            //Increments all neighboring coordinates above
            if ( (randY-1) >= 0) {

                numGrid[randX][randY-1] += 1;

                if ( (randX-1) >= 0) {
                    numGrid[randX-1][randY-1] += 1;
                }

                if ( (randX+1) <= WIDTH - 1) {
                    numGrid[randX+1][randY-1] += 1;
                }
            }

            //Increments all neighboring coordinates below
            if ( (randY+1) <= HEIGHT - 1) {

                numGrid[randX][randY+1] += 1;

                if ( (randX-1) >= 0) {
                    numGrid[randX-1][randY+1] += 1;
                }

                if ( (randX+1) <= WIDTH - 1) {
                    numGrid[randX+1][randY+1] += 1;
                }
            }
            count++;
        }

        return numGrid;
    }

    @Override
    public void restart() {
        int[][] randGrid = generateRandomGrid(numMines);
        addAll(randGrid);
    }

    @Override
    public void setWin(boolean value) {
        didWin = value;
    }



    /**
     * Sets the grid according to a given 2d array of values
     * @param numGrid
     */
    protected void addAll(int[][] numGrid) {
        if (grid.length != WIDTH || grid[0].length != HEIGHT) {
            throw new IllegalArgumentException("2D Array must match size of grid");
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[j][i] = new Square(numGrid[j][i]);
            }
        }
    }




    @Override
    public String toString() {
        String string = "|";
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                string += grid[j][i].getValue() + "|";
            }
            string += "\n";
        }

        return string;
    }

    /**
     * Searches the grid for the given square and reveals it if it exists.
     * @param square
     */
    protected void reveal(Square square) {
        if (square == null) {
            throw new IllegalArgumentException("Passed a null object for reveal()");
        }
        for (Square[] sqX : grid) {
            for (Square sqY : sqX) {
                if (square.equals(sqY)) {
                    sqY.reveal();
                    revealCount++;
                }
            }
        }

        //Our win condition is based on how many squares are revealed
        if (revealCount == (HEIGHT * WIDTH - numMines)) {
            didWin = true;
        }
    }
}
