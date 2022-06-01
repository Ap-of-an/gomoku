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

package com.dm.study.gui;

import com.dm.study.model.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author Dmitry
 */
public class GameGUI extends JFrame implements PropertyChangeListener {

    private final int sellSize;
    private final int fontSize;
    private final JLabel[][] cells;
    private final int amountOfRows;
    private final int amountOfCols;
    private Coordinate clickedCell;

    public GameGUI(int amountOfRows, int amountOfCols, int fontSize, int sellSize) {
        super("Gomoku");
        this.amountOfRows = amountOfRows;
        this.amountOfCols = amountOfCols;
        this.sellSize = sellSize;
        cells = new JLabel[amountOfRows][amountOfCols];
        this.fontSize = fontSize;
        setSystemLookAndFeel();
        setLayout(new GridLayout(amountOfRows, amountOfCols));
        createField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();

        UIManager.put("OptionPane.okButtonText", "Новая игра");
        UIManager.put("OptionPane.cancelButtonText", "Выйти из игры");
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException | UnsupportedLookAndFeelException |
                       IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
    }

    private void createField() {
        for (int i = 0; i < amountOfRows; i++) {
            for (int j = 0; j < amountOfCols; j++) {
                JLabel label = new JLabel();
                cells[i][j] = label;
                label.setPreferredSize(new Dimension(sellSize, sellSize));
                label.setBorder(BorderFactory.createLineBorder(Color.gray));
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));
                add(label);
                final Coordinate cell = new Coordinate(i, j);
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(final MouseEvent event) {
                        synchronized (GameGUI.this) {
                            clickedCell = cell;
                            GameGUI.this.notifyAll();
                        }
                    }
                });
            }
        }
    }

    private void display() {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
        setVisible(true);
    }

    public void printField(final char[][] field) {
        for (int i = 0; i < amountOfRows; i++) {
            for (int j = 0; j < amountOfCols; j++) {
                cells[i][j].setText(String.valueOf(field[i][j]));
            }
        }
    }

    public Coordinate getUserInput() {
        synchronized (this) {
            try {
                wait();
            } catch (final InterruptedException exception) {
                exception.printStackTrace();
                System.exit(2);
            }
        }
        return clickedCell;
    }

    public void showWindow() {
        display();
    }

    public void showWinner(final String name) {
        String title;
        String message;
        int result;
        if (name == null) {
            title = "Ничья";
            message = "Ничья!";
        } else {
            title = "Победитель";
            message = "Выиграл игрок " + name + "!";
        }
        result = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        printField((char[][]) (evt.getNewValue()));
    }
}
