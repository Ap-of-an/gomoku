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

package com.dm.study.components.Analyzer;

import com.dm.study.components.suppliers_of_moves.Stencil;
import com.dm.study.model.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author Dmitry
 */
class StencilTest {
    final char[][] field = new char[][]{
            {' ', ' ', 'x'},
            {' ', 'x', ' '},
            {'x', ' ', ' '}
    };
    Stencil stencil = new Stencil(field, 2);


    @Test
    void getAllRows() {
        var actual = stencil.getAllRows();
        Coordinate[][] excepted = new Coordinate[][]{
                {new Coordinate(0, 0), new Coordinate(0, 1)},
                {new Coordinate(0, 1), new Coordinate(0, 2)},
                {new Coordinate(1, 0), new Coordinate(1, 1)},
                {new Coordinate(1, 1), new Coordinate(1, 2)},
                {new Coordinate(2, 0), new Coordinate(2, 1)},
                {new Coordinate(2, 1), new Coordinate(2, 2)}
        };
        assertArrayEquals(excepted, actual);
    }

    @Test
    void getAllCols() {
        var actual = stencil.getAllCols();
        Coordinate[][] excepted = new Coordinate[][]{
                {new Coordinate(0, 0), new Coordinate(1, 0)},
                {new Coordinate(0, 1), new Coordinate(1, 1)},
                {new Coordinate(0, 2), new Coordinate(1, 2)},
                {new Coordinate(1, 0), new Coordinate(2, 0)},
                {new Coordinate(1, 1), new Coordinate(2, 1)},
                {new Coordinate(1, 2), new Coordinate(2, 2)}
        };
        assertArrayEquals(excepted, actual);
    }

    @Test
    void getAllMainDiags() {
        var actual = stencil.getAllMainDiags();
        Coordinate[][] excepted = new Coordinate[][]{
                {new Coordinate(0, 0), new Coordinate(1, 1)},
                {new Coordinate(0, 1), new Coordinate(1, 2)},
                {new Coordinate(1, 0), new Coordinate(2, 1)},
                {new Coordinate(1, 1), new Coordinate(2, 2)}
        };
        assertArrayEquals(excepted, actual);
    }

    @Test
    void getAllSecondaryDiags() {
        var actual = stencil.getAllSecondaryDiags();
        Coordinate[][] excepted = new Coordinate[][]{
                {new Coordinate(0, 1), new Coordinate(1, 0)},
                {new Coordinate(0, 2), new Coordinate(1, 1)},
                {new Coordinate(1, 1), new Coordinate(2, 0)},
                {new Coordinate(1, 2), new Coordinate(2, 1)}
        };
        assertArrayEquals(excepted, actual);
    }
}