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
public class SimpleStrategy extends MoveStrategy {

    public SimpleStrategy(final char[][] field, final char charOfEmptyCell) {
        super(field, charOfEmptyCell);
    }

    @Override
    public Coordinate getMove() {
        Random random = new Random();
        List<Coordinate> list = freeCells();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    private List<Coordinate> freeCells() {
        List<Coordinate> coordinateList = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == charOfEmptyCell) {
                    coordinateList.add(new Coordinate(i, j));
                }
            }
        }
        return coordinateList;
    }
}
