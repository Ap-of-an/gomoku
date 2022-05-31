/*
 * MIT License
 *
 * Copyright (c) [2022] [Dmitry]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dm.study.model;

import com.dm.study.components.suppliers_of_moves.Stencil;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Dmitry
 */
public class GameField {

    private final char[][] field;
    private char winnerMark;
    public final char charOfEmptyCell;
    private final int countOfCellsToWin;
    private final int numberOfCells;
    private int numberOfFilledCells;
    private final Stencil stencil;
    private final PropertyChangeSupport support;

    public GameField(final char[][] field, final char charOfEmptyCell, final int cellsToWin, final Stencil stencil) {
        this.field = field;
        this.charOfEmptyCell = charOfEmptyCell;
        countOfCellsToWin = cellsToWin;
        this.stencil = stencil;
        winnerMark = this.charOfEmptyCell;
        numberOfFilledCells = 0;
        numberOfCells = field.length * field[0].length;
        support = new PropertyChangeSupport(this);
    }

    public boolean isGameOver() {
        winnerMark = checkWinner();
        return numberOfFilledCells == numberOfCells || winnerMark != charOfEmptyCell;
    }

    private char checkWinner() {
        char firstCell;
        var all = stencil.getAllStencilEntries();
        for (Coordinate[] c : all) {
            firstCell = field[c[0].row()][c[0].col()];
            if (firstCell == charOfEmptyCell) {
                continue;
            }
            int count = 1;
            for (int i = 1; i < c.length; i++) {
                if (firstCell != field[c[i].row()][c[i].col()]) {
                    break;
                }
                count++;
            }
            if (count == countOfCellsToWin) {
                return firstCell;
            }
        }
        return charOfEmptyCell;

    }

    public boolean acceptMove(final Coordinate cell, final char mark) {
        if (isCellEmpty(cell)) {
            field[cell.row()][cell.col()] = mark;
            numberOfFilledCells++;
            support.firePropertyChange("field", null, field);
            return true;
        }
        return false;
    }

    private boolean isCellEmpty(final Coordinate cell) {
        return field[cell.row()][cell.col()] == charOfEmptyCell;
    }

    public void addSubscriber(PropertyChangeListener subscriber) {
        support.addPropertyChangeListener(subscriber);
    }

    public void reset() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = charOfEmptyCell;
            }
        }
        winnerMark = charOfEmptyCell;
        numberOfFilledCells = 0;
        support.firePropertyChange("field", null, field);
    }

    public char getWinnerMark() {
        return winnerMark;
    }


}
