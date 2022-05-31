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

import com.dm.study.libs.CircularList;
import com.dm.study.model.player.Player;


/**
 * @author Dmitry
 */
public class GameModel {
    private Player currentPlayer;
    private final CircularList<Player> players;
    private final GameField gameField;

    public GameModel(final CircularList<Player> players, final GameField gameField) {
        this.players = players;
        this.gameField = gameField;
        currentPlayer = players.first();
    }

    /**
     * Запускает цикл ходов игроков, оканчивающийся либо победой, либо ничьей
     */
    public void run() {
        boolean acceptedMove;
        while (!gameField.isGameOver()) {
            Coordinate cellCoordinate = currentPlayer.getMove();
            acceptedMove = gameField.acceptMove(cellCoordinate, currentPlayer.getMark());
            if (acceptedMove) {
                currentPlayer = players.next();
            }
        }
    }

    public Player getWinner() {
        Player winner = null;
        while (currentPlayer != winner) {
            winner = players.next();
            if (winner.getMark() == gameField.getWinnerMark()) {
                return winner;
            }
        }
        return null;
    }

    public void reset() {
        gameField.reset();
        currentPlayer = players.first();
    }
}
