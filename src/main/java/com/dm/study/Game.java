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

    public Game(String[] args) {
        int amountOfRow = 15;
        int amountOfCols = 15;
        int cellsToWin = 5;
        char charOfEmptyCell = ' ';
        char[][] field = emptyField(amountOfRow, amountOfCols, charOfEmptyCell);
        Stencil stencil = new Stencil(field, cellsToWin);
        GameField gameField = new GameField(field, charOfEmptyCell, cellsToWin, stencil);
        gui = new GameGUI(amountOfRow, amountOfCols, 20, 50);

        gameField.addSubscriber(gui);

        CircularList<Player> players = new CircularList<>();
        addPlayerToList("man", players, Mark.X, "Player 1", field, 2, charOfEmptyCell, stencil);
        addPlayerToList("comp", players, Mark.O, "Player 2", field, 2, charOfEmptyCell, stencil);

        gameModel = new GameModel(players, gameField);


        if (args.length != 4) {
            return;
        }
        int sizeField;
        int levelDifficulty;
        try {
            sizeField = Integer.parseInt(args[2]);
            levelDifficulty = Integer.parseInt(args[3]);
            if (sizeField > 15 || sizeField < 7) {
                System.out.println("Размер игрового поля не может быть меньше 7 и больше 15");
                throw new NumberFormatException();
            }
            if (levelDifficulty < Difficulty.getMinDifficulty().ordinal() || levelDifficulty > Difficulty.getMaxDifficulty().ordinal()) {
                System.out.println("Неправильная сложность. Допустимые значения: от " + Difficulty.getMinDifficulty().ordinal()
                        + " до " + Difficulty.getMaxDifficulty().ordinal());
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("неверный формат входных данных. Пример правильного ввода: man comp 15 1." +
                    "Данный пример создаст игровое поле размером 15 на 15 с двумя игроками: человеком и компьютером" +
                    " с простой сложностью");
            System.exit(1);
            return;
        }
        String player1Type = args[0];
        String player2Type = args[1];
        players.clear();
        addPlayerToList(player1Type, players, Mark.X, "Player 1", field, levelDifficulty, charOfEmptyCell, stencil);
        addPlayerToList(player2Type, players, Mark.O, "Player 2", field, levelDifficulty, charOfEmptyCell, stencil);
    }

    private void addPlayerToList(String player1Type, CircularList<Player> players, Mark mark, String name, char[][] field, int levelDifficulty, char charOfEmptyCell, Stencil stencil) {
        if (player1Type.equals("man")) {
            players.add(new Player(
                    "Player 1",
                    mark,
                    new SupplierHuman(gui::getUserInput)));
        } else if (player1Type.equals("comp")) {
            players.add(new Player(
                    name,
                    mark,
                    new SupplierComputer(field, Difficulty.valueOf(levelDifficulty), mark.getSymbol(), charOfEmptyCell, stencil)));
        } else {
            System.out.println("Неверный ввод игроков");
            System.exit(1);
        }
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
