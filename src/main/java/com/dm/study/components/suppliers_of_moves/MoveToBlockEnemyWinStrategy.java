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

package com.dm.study.components.suppliers_of_moves;

import com.dm.study.model.Coordinate;
import com.dm.study.model.player.Player;

/**
 * @author Dmitry
 */
public class MoveToBlockEnemyWinStrategy extends MoveStrategy {

    public MoveToBlockEnemyWinStrategy(final char[][] field, char charOfEmptyCell, final char mark, final Coordinate[][] allPossibleSetsOfCell) {
        super(field, charOfEmptyCell);
        this.allPossibleSetsOfCell = allPossibleSetsOfCell;
        this.mark = mark;
    }

    @Override
    public Coordinate getMove() {
        char[] enemyMarks = Player.getAllMarksOfPlayersWithoutMark(mark);
        for (char enemyMark : enemyMarks) {
            Coordinate[] arrayCoordinates = findMaxEnemyMarksAndOneFreeCell(enemyMark);
            if (arrayCoordinates != null) {
                for (Coordinate c : arrayCoordinates) {
                    if (field[c.row()][c.col()] == charOfEmptyCell) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    private Coordinate[] findMaxEnemyMarksAndOneFreeCell(final char enemyMark) {
        for (Coordinate[] array : allPossibleSetsOfCell) {
            Coordinate[] result = checkArray(enemyMark, array);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private Coordinate[] checkArray(final char enemyMark, final Coordinate[] array) {
        int counterEmptyCells = 0;
        int counterMarks = 0;
        for (Coordinate c : array) {
            if (field[c.row()][c.col()] == enemyMark) {
                counterMarks++;
            }
            if (field[c.row()][c.col()] == charOfEmptyCell) {
                counterEmptyCells++;
            }
        }
        if (counterMarks == array.length - 1 && counterEmptyCells == 1) {
            return array;
        }
        return null;
    }
}
