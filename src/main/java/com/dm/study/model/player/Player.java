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

package com.dm.study.model.player;

import com.dm.study.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry
 */
public class Player {
    private static final List<Mark> addedMarks = new ArrayList<>();
    private final Mark mark;
    private final String name;
    private final SupplierOfMoves supplier;

    public Player(final String name, final Mark mark, final SupplierOfMoves supplier) {
        if (addedMarks.contains(mark)) {
            throw new IllegalArgumentException("Этот знак занят, выберите другой");
        }
        addedMarks.add(mark);
        this.supplier = supplier;
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public char getMark() {
        return mark.getSymbol();
    }

    public Coordinate getMove() {
        return supplier.getMove();
    }

    public static char[] getAllMarksOfPlayersWithoutMark(char mark) {
        char[] marks = new char[addedMarks.size() - 1];
        char buffer;
        for (int i = 0; i < addedMarks.size(); i++) {
            buffer = addedMarks.get(i).getSymbol();
            if (buffer != mark) {
                marks[i++] = buffer;
            }
        }
        return marks;
    }
}
