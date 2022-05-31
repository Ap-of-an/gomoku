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

package com.dm.study;

import com.dm.study.components.suppliers_of_moves.Stencil;
import com.dm.study.components.suppliers_of_moves.SupplierComputer;
import com.dm.study.components.suppliers_of_moves.SupplierHuman;
import com.dm.study.gui.GameGUI;
import com.dm.study.libs.CircularList;
import com.dm.study.model.GameField;
import com.dm.study.model.GameModel;
import com.dm.study.model.player.Difficulty;
import com.dm.study.model.player.Mark;
import com.dm.study.model.player.Player;

/**
 * @author Dmitry
 */
public class Game {
    private final GameModel gameModel;
    private final GameGUI gui;

    public Game() {
        int amountOfRow = 7;
        int amountOfCols = 7;
        int cellsToWin = 4;
        char charOfEmptyCell = ' ';
        char[][] field = emptyField(amountOfRow, amountOfCols, charOfEmptyCell);
        Stencil stencil = new Stencil(field, cellsToWin);
        GameField gameField = new GameField(field, charOfEmptyCell, cellsToWin, stencil);
        gui = new GameGUI(amountOfRow, amountOfCols, 20, 50);

        gameField.addSubscriber(gui);

        CircularList<Player> players = new CircularList<>();
        players.add(new Player(
                "Player 1",
                Mark.X,
                new SupplierHuman(gui::getUserInput)));
        players.add(new Player(
                "Player 2",
                Mark.O,
                new SupplierComputer(field, Difficulty.LEVEL_2, Mark.O.getSymbol(), charOfEmptyCell, stencil)));

        gameModel = new GameModel(players, gameField);
    }

    public void start() {
        gui.showWindow();
        while (true) {
            gameModel.run();

            Player winner = gameModel.getWinner();
            String winnerName = winner == null ? null : winner.getName();
            gui.showWinner(winnerName);

            gameModel.reset();
        }
    }

    private char[][] emptyField(int amountOfRow, int amountOfCols, char charOfEmptyCell) {
        char[][] field = new char[amountOfRow][amountOfCols];
        for (int i = 0; i < amountOfRow; i++) {
            for (int j = 0; j < amountOfCols; j++) {
                field[i][j] = charOfEmptyCell;
            }
        }
        return field;
    }
}
