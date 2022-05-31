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
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry
 */
public class Stencil {

    private final int countOfCellsToWin;
    private final int amountOfRows;
    private final int amountOfCols;


    public Stencil(final char[][] field_, final int countOfCellsToWin_) {
        countOfCellsToWin = countOfCellsToWin_;
        amountOfRows = field_.length;
        amountOfCols = field_[0].length;
    }

    public Coordinate[][] getAllRows() {
        List<Coordinate[]> resultArray = new ArrayList<>();
        for (int i = 0; i < amountOfRows; i++) {
            for (int j = 0; j <= amountOfCols - countOfCellsToWin; j++) {
                Coordinate[] array = new Coordinate[countOfCellsToWin];
                for (int k = j, l = 0; k < j + countOfCellsToWin; k++, l++) {
                    array[l] = new Coordinate(i, k);
                }
                resultArray.add(array);
            }
        }
        return resultArray.toArray(new Coordinate[0][]);
    }

    public Coordinate[][] getAllCols() {
        List<Coordinate[]> resultArray = new ArrayList<>();
        for (int i = 0; i <= amountOfRows - countOfCellsToWin; i++) {
            for (int j = 0; j < amountOfCols; j++) {
                Coordinate[] array = new Coordinate[countOfCellsToWin];
                for (int k = i, l = 0; k < i + countOfCellsToWin; k++, l++) {
                    array[l] = new Coordinate(k, j);
                }
                resultArray.add(array);
            }
        }
        return resultArray.toArray(new Coordinate[0][]);
    }

    public Coordinate[][] getAllMainDiags() {
        List<Coordinate[]> resultArray = new ArrayList<>();
        for (int i = 0; i <= amountOfRows - countOfCellsToWin; i++) {
            for (int j = 0; j <= amountOfCols - countOfCellsToWin; j++) {
                Coordinate[] array = new Coordinate[countOfCellsToWin];
                for (int k = j, n = i, l = 0; k < j + countOfCellsToWin; k++, n++, l++) {
                    array[l] = new Coordinate(n, k);
                }
                resultArray.add(array);
            }
        }
        return resultArray.toArray(new Coordinate[0][]);
    }

    public Coordinate[][] getAllSecondaryDiags() {
        List<Coordinate[]> resultArray = new ArrayList<>();
        for (int i = 0; i <= amountOfRows - countOfCellsToWin; i++) {
            for (int j = countOfCellsToWin - 1; j < amountOfCols; j++) {
                Coordinate[] array = new Coordinate[countOfCellsToWin];
                for (int k = j, n = i, l = 0; n < i + countOfCellsToWin; k--, n++, l++) {
                    array[l] = new Coordinate(n, k);
                }
                resultArray.add(array);
            }
        }
        return resultArray.toArray(new Coordinate[0][]);
    }

    public Coordinate[][] getAllStencilEntries() {
        var a1 = Arrays.stream(getAllRows()).toList();
        var a2 = Arrays.stream(getAllCols()).toList();
        var a3 = Arrays.stream(getAllMainDiags()).toList();
        var a4 = Arrays.stream(getAllSecondaryDiags()).toList();
        var a = new ArrayList<>(a1);
        a.addAll(a2);
        a.addAll(a3);
        a.addAll(a4);
        return a.toArray(new Coordinate[0][]);
    }
}
