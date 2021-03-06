package desktopgames.reversi;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Logic {

    private final byte[][] board = new byte[8][8];

    private boolean moveFlag;

    private boolean impossibleMoveStepBlack;
    private boolean impossibleMoveStepWhite;

    private final Set<Pair<Integer, Integer>> placeablePositions = new HashSet<>();

    private final List<Pair<Integer, Integer>> repaintCell = new ArrayList<>();

    private byte blackScore;
    private byte whiteScore;

    // Начало игры
    public Logic() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }

        board[3][3] = 2;
        board[4][4] = 2;
        board[3][4] = 1;
        board[4][3] = 1;

        moveFlag = true;

        impossibleMoveStepBlack = false;
        impossibleMoveStepWhite = false;

        whiteScore = 2;
        blackScore = 2;
    }

    public boolean findCorrectLine(int i, int j, boolean move) {
        repaintCell.clear();

        if (move) repaintCell.add(new Pair<>(i, j));

        byte currentPlayer = moveFlag ? (byte) 1 : 2;

        // Снизу от (i, j)
        if (j != 7) {
            int newJ = j;
            int count = 1;
            while (newJ < 7) {
                newJ++;
                if (board[i][newJ] == 0) break;
                if (board[i][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        if (move)
                            for (int m = j; m < newJ; m++)
                                board[i][m] = currentPlayer;

                        for (int m = j + 1; m < newJ; m++)
                            repaintCell.add(new Pair<>(i, m));
                    }

                    break;
                }
                count++;
            }
        }

        // Сверху от (i, j)
        if (j != 0) {
            int newJ = j;
            int count = 1;
            while (newJ > 0) {
                newJ--;
                if (board[i][newJ] == 0) break;
                if (board[i][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        if (move)
                            for (int m = j; m > newJ; m--)
                                board[i][m] = currentPlayer;

                        for (int m = j - 1; m > newJ; m--)
                            repaintCell.add(new Pair<>(i, m));
                    }

                    break;
                }
                count++;
            }
        }

        // Справа от (i, j)
        if (i != 7) {
            int newI = i;
            int count = 1;
            while (newI < 7) {
                newI++;
                if (board[newI][j] == 0) break;
                if (board[newI][j] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        if (move)
                            for (int k = i; k < newI; k++)
                                board[k][j] = currentPlayer;

                        for (int k = i + 1; k < newI; k++)
                            repaintCell.add(new Pair<>(k, j));
                    }

                    break;
                }
                count++;
            }
        }

        // Слева от (i, j)
        if (i != 0) {
            int newI = i;
            int count = 1;
            while (newI > 0) {
                newI--;
                if (board[newI][j] == 0) break;
                if (board[newI][j] == currentPlayer){
                    if (count != 1) {
                        if (!move) return true;

                        if (move)
                            for (int k = i; k > newI; k--)
                                board[k][j] = currentPlayer;

                        for (int k = i - 1; k > newI; k--)
                            repaintCell.add(new Pair<>(k, j));
                    }

                    break;
                }
                count++;
            }
        }

        // Нижняя правая диагональ от (i, j)
        if (i != 7 && j != 7) {
            int newI = i;
            int newJ = j;
            int count = 1;
            while (newI < 7 && newJ < 7) {
                newI++;
                newJ++;
                if (board[newI][newJ] == 0) break;
                if (board[newI][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        if (move) {
                            int k = i;
                            int m = j;
                            while(k < newI && m < newJ) {
                                board[k][m] = currentPlayer;
                                k++;
                                m++;
                            }
                        }

                        int k = i + 1;
                        int m = j + 1;
                        while(k < newI && m < newJ) {
                            repaintCell.add(new Pair<>(k, m));
                            k++;
                            m++;
                        }
                    }

                    break;
                }
                count++;
            }
        }

        // Правая верхняя диагональ от (i, j)
        if (i != 7 && j != 0) {
            int newI = i;
            int newJ = j;
            int count = 1;
            while (newI < 7 && newJ > 0) {
                newI++;
                newJ--;
                if (board[newI][newJ] == 0) break;
                if (board[newI][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        if (move) {
                            int k = i;
                            int m = j;
                            while(k < newI && m > newJ) {
                                board[k][m] = currentPlayer;
                                k++;
                                m--;
                            }
                        }

                        int k = i + 1;
                        int m = j - 1;
                        while(k < newI && m > newJ) {
                            repaintCell.add(new Pair<>(k, m));
                            k++;
                            m--;
                        }
                    }

                    break;
                }
                count++;
            }
        }

        // Левая верхняя диагональ от (i, j)
        if (i != 0 && j != 0) {
            int newI = i;
            int newJ = j;
            int count = 1;
            while (newI > 0 && newJ > 0) {
                newI--;
                newJ--;
                if (board[newI][newJ] == 0) break;
                if (board[newI][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        if (move) {
                            int k = i;
                            int m = j;
                            while(k > newI && m > newJ) {
                                board[k][m] = currentPlayer;
                                k--;
                                m--;
                            }
                        }

                        int k = i - 1;
                        int m = j - 1;
                        while(k > newI && m > newJ) {
                            repaintCell.add(new Pair<>(k, m));
                            k--;
                            m--;
                        }
                    }

                    break;
                }
                count++;
            }
        }

        // Нижняя левая диагональ от (i, j)
        if (i != 0 && j != 7) {
            int newI = i;
            int newJ = j;
            int count = 1;
            while (newI > 0 && newJ < 7) {
                newI--;
                newJ++;
                if (board[newI][newJ] == 0) break;
                if (board[newI][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;


                        if (move) {
                            int k = i;
                            int m = j;
                            while(k > newI && m < newJ) {
                                board[k][m] = currentPlayer;
                                k--;
                                m++;
                            }
                        }

                        int k = i - 1;
                        int m = j + 1;
                        while(k > newI && m < newJ) {
                            repaintCell.add(new Pair<>(k, m));
                            k--;
                            m++;
                        }
                    }

                    break;
                }
                count++;
            }
        }

        if (move) {
            updateScores();
            moveFlag = !moveFlag;
        }

        return false;
    }

    public void findPlaceablePositions() {
        if (impossibleMoveStepBlack && impossibleMoveStepWhite) {
            repaintCell.clear();
            return;
        }

        placeablePositions.clear();

        int previousPlayer = moveFlag ? 2 : 1;

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j] == previousPlayer) {
                    if (j < 7) {
                        if (board[i][j + 1] == 0)
                            if (findCorrectLine(i, j + 1, false))
                                placeablePositions.add(new Pair<>(i, j + 1));
                        if (i < 7)
                            if (board[i + 1][j + 1] == 0)
                                if (findCorrectLine(i + 1, j + 1, false))
                                    placeablePositions.add(new Pair<>(i + 1, j + 1));
                        if (i > 0)
                            if (board[i - 1][j + 1] == 0)
                                if (findCorrectLine(i - 1, j + 1, false))
                                    placeablePositions.add(new Pair<>(i - 1, j + 1));
                    }

                    if (j > 0) {
                        if (board[i][j - 1] == 0)
                            if (findCorrectLine(i, j - 1, false))
                                placeablePositions.add(new Pair<>(i, j - 1));
                        if (i < 7)
                            if (board[i + 1][j - 1] == 0)
                                if (findCorrectLine(i + 1, j - 1, false))
                                    placeablePositions.add(new Pair<>(i + 1, j - 1));
                        if (i > 0)
                            if (board[i - 1][j - 1] == 0)
                                if (findCorrectLine(i - 1, j - 1, false))
                                    placeablePositions.add(new Pair<>(i - 1, j - 1));
                    }

                    if (i < 7)
                        if (board[i + 1][j] == 0)
                            if (findCorrectLine(i + 1, j, false))
                                placeablePositions.add(new Pair<>(i + 1, j));

                    if (i > 0)
                        if (board[i - 1][j] == 0)
                            if (findCorrectLine(i - 1, j, false))
                                placeablePositions.add(new Pair<>(i - 1, j));
                }

        if (placeablePositions.isEmpty()) {
            if (moveFlag)
                impossibleMoveStepBlack = true;
            else
                impossibleMoveStepWhite = true;

            moveFlag = !moveFlag;
            findPlaceablePositions();
        }

        impossibleMoveStepWhite = false;
        impossibleMoveStepBlack = false;
    }


    private void updateScores() {
        if (moveFlag) {
            blackScore += (byte) repaintCell.size();
            whiteScore -= (byte) (repaintCell.size() - 1);
        }
        else {
            whiteScore += (byte) repaintCell.size();
            blackScore -= (byte) (repaintCell.size() - 1);
        }
    }

    public byte getWhiteScore() { return whiteScore; }

    public byte getBlackScore() { return blackScore; }

    public boolean getMoveFlag() { return moveFlag; }

    public int getValueFromArray(int i, int j) { return board[i][j]; }

    public List<Pair<Integer, Integer>> getRepaintCell() { return repaintCell; }

    public Set<Pair<Integer, Integer>> getPlaceablePositions() { return placeablePositions; }

}