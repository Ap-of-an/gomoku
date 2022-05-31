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
import com.dm.study.model.player.Difficulty;
import com.dm.study.model.player.SupplierOfMoves;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry
 */
public class SupplierComputer implements SupplierOfMoves {
    private final List<MoveStrategy> strategies;

    public SupplierComputer(final char[][] field, Difficulty difficulty, final char mark, final char charOfEmptyCell, Stencil stencil) {
        strategies = new ArrayList<>();
        Coordinate[][] allPossibleSetsOfCell = stencil.getAllStencilEntries();
        if (difficulty == Difficulty.LEVEL_2) {
            strategies.add(new MoveToWinStrategy(field, charOfEmptyCell, mark, allPossibleSetsOfCell));
            strategies.add(new MoveToBlockEnemyWinStrategy(field, charOfEmptyCell, mark, allPossibleSetsOfCell));
            strategies.add(new SimpleSmartStrategy(field, charOfEmptyCell, mark, allPossibleSetsOfCell));
        }
        strategies.add(new SimpleStrategy(field, charOfEmptyCell));
    }

    @Override
    public Coordinate getMove() {
        for (MoveStrategy strategy : strategies) {
            Coordinate coordinate = strategy.getMove();
            if (coordinate != null) {
                return coordinate;
            }
        }
        return null;
    }
}
