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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dmitry
 */
public class SimpleSmartStrategy extends MoveStrategy {

    public SimpleSmartStrategy(final char[][] field, final char charOfEmptyCell, final char mark, final Coordinate[][] allPossibleSetsOfCell) {
        super(field, charOfEmptyCell);
        this.allPossibleSetsOfCell = allPossibleSetsOfCell;
        this.mark = mark;
    }

    @Override
    public Coordinate getMove() {
        Coordinate[] array = getBestStencil();
        if (array != null) {
            return getRandomFreeCell(array);

        }
        return null;
    }

    private Coordinate getRandomFreeCell(final Coordinate[] array) {
        List<Coordinate> list = new ArrayList<>();
        for (Coordinate coordinate : array) {
            if (field[coordinate.row()][coordinate.col()] == charOfEmptyCell) {
                list.add(coordinate);
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    private Coordinate[] getBestStencil() {
        int max = 0;
        Coordinate[] result = null;
        for (Coordinate[] array : allPossibleSetsOfCell) {
            int counterMarks = 0;
            int counterEmptyCells = 0;
            for (Coordinate c : array) {
                if (field[c.row()][c.col()] == mark) {
                    counterMarks++;
                }
                if (field[c.row()][c.col()] == charOfEmptyCell) {
                    counterEmptyCells++;
                }
            }
            if (counterMarks > 0 && counterMarks + counterEmptyCells == array.length) {
                if (counterMarks > max) {
                    max = counterMarks;
                    result = array;
                }
            }
        }
        return result;
    }
}
