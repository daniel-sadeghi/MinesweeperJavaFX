package com.dsadeghi.minesweeper;

/**
 * Our exception to throw if we select a choice for difficulty that we haven't implemented the logic for yet
 */
public class InvalidChoiceException extends RuntimeException{
    public InvalidChoiceException() {};

    public InvalidChoiceException(String message) {
        super(message);
    }

}
