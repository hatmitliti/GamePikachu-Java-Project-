/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepikachudoanmonjava;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import sun.security.krb5.internal.tools.Ktab;

/**
 *
 * @author Acer
 */
public final class MaTrixGame{

    private int row;
    private int col;
    private int[][] matrix;
    MainFrame frame;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public  MaTrixGame(MainFrame frame, int row, int col) {
        this.frame = frame;
        this.row = row;
        this.col = col;
        System.out.println(row + "," + col);
        taoMaTran();
        xuatMaTran();
    }

    public void xuatMaTran() {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                System.out.printf("%3d", matrix[i][j]);
            }
            System.out.println();
        }
    }
    public PointLine check2button(Point p1, Point p2) {
        if (!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y]) {
            // check line with x
            if (p1.x == p2.x) {
                if (checkbtnHangNgang(p1.y, p2.y, p1.x)) {
                    return new PointLine(p1, p2);
                }
            }
            // check line with y
            if (p1.y == p2.y) {
                if (checkbtnHangDoc(p1.x, p2.x, p1.y)) {
                    return new PointLine(p1, p2);
                }
            }            
            // check in rectangle with x
            if ( checkRectX(p1, p2)) {
                return new PointLine(p1, p2);
            }
            // check in rectangle with y
            if (checkRectY(p1, p2)) {
                return new PointLine(p1, p2);
            }
            // check more right
            if (kiemTraDTDocNMT(p1, p2, 1)) {
                return new PointLine(p1, p2);
            }
            // check more left
            if (kiemTraDTDocNMT(p1, p2, -1)) {
                return new PointLine(p1, p2);
            }
            // check more down
            if (kiemTraDTNgangNMT(p1, p2, 1)) {
                return new PointLine(p1, p2);
            }
            // check more up
            if (kiemTraDTNgangNMT(p1, p2, -1)) {
                return new PointLine(p1, p2);
            }
        }
        return null;
    }

    private void taoMaTran() {

        matrix = new int[row][col];
        for (int i = 0; i < col; i++) {
            matrix[0][i] = matrix[row - 1][i] = 0;
        }
        for (int j = 0; j < row; j++) {
            matrix[j][0] = matrix[j][col - 1] = 0;
        }
        Random rd = new Random();
        int imageCountIcon = 37;
        int[] arr = new int[imageCountIcon + 1];
        int Max = imageCountIcon / 2;
        ArrayList<Point> listPoint = new ArrayList<>();
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                listPoint.add(new Point(i, j));
            }
        }
        int a = 0;
        do {
            int  index = rd.nextInt(imageCountIcon) + 1;
            if (arr[index] < Max) {
                arr[index] += 2;
                for (int k = 0; k < 2; k++) {
                    try {
                        int size = listPoint.size();
                        int pointIndex = rd.nextInt(size);
                        matrix[listPoint.get(pointIndex).x][listPoint.get(pointIndex).y] = index;
                        listPoint.remove(pointIndex);
                    } catch (Exception e) {

                    }

                }
                a++;
            }
        } while (a < row * col / 2);

    }

    private boolean checkbtnHangNgang(int y1, int y2, int x) {
        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);

        for (int y = min + 1; y < max; y++) {
            if (matrix[x][y] != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkbtnHangDoc(int x1, int x2, int y) {
        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);

        for (int x = min + 1; x < max; x++) {
            if (matrix[x][y] != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRectX(Point p1, Point p2) {
        // find point have y min and max
        Point pMinY = p1, pMaxY = p2;
        if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
        }
        for (int y = pMinY.y; y <= pMaxY.y; y++) {
            if (y > pMinY.y && matrix[pMinY.x][y] != 0) {
                return false;
            }
            // check two line
            if ((matrix[pMaxY.x][y] == 0)
                    && checkbtnHangDoc(pMinY.x, pMaxY.x, y)
                    && checkbtnHangNgang(y, pMaxY.y, pMaxY.x)) {
                // if three line is true return column y
                return true;
            }
        }
        // have a line in three line not true then return -1
        return false;
    }

    private boolean checkRectY(Point p1, Point p2) {
        // find point have y min
        Point pMinX = p1, pMaxX = p2;
        if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
        }
        // find line and y begin
        for (int x = pMinX.x; x <= pMaxX.x; x++) {
            if (x > pMinX.x && matrix[x][pMinX.y] != 0) {
                return false;
            }
            if ((matrix[x][pMaxX.y] == 0)
                    && checkbtnHangNgang(pMinX.y, pMaxX.y, x)
                    && checkbtnHangDoc(x, pMaxX.x, pMaxX.y)) {
                return true;
            }
        }
        return false;
    }

    private boolean kiemTraDTDocNMT(Point p1, Point p2, int type) {
        Point pMinY = p1;
        Point pMaxY = p2;
        if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
        }
        // find line and y begin
        int y = pMaxY.y + type;
        int row = pMinY.x;
        int colFinish = pMaxY.y;
        if (type == -1) {
            colFinish = pMinY.y;
            y = pMinY.y + type;
            row = pMaxY.x;
        }
        if ((matrix[row][colFinish] == 0 || pMinY.y == pMaxY.y)
                && checkbtnHangNgang(pMinY.y, pMaxY.y, row)) {
            while (matrix[pMinY.x][y] == 0
                    && matrix[pMaxY.x][y] == 0) {
                if (checkbtnHangDoc(pMinY.x, pMaxY.x, y)) {
                    return true;
                }
                y += type;
            }
        }
        return false;
    }

    private boolean kiemTraDTNgangNMT(Point p1, Point p2, int type) {
        Point pMinX = p1, pMaxX = p2;
        if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
        }
        int x = pMaxX.x + type;
        int col = pMinX.y;
        int rowFinish = pMaxX.x;
        if (type == -1) {
            rowFinish = pMinX.x;
            x = pMinX.x + type;
            col = pMaxX.y;
        }
        if ((matrix[rowFinish][col] == 0|| pMinX.x == pMaxX.x)
                && checkbtnHangDoc(pMinX.x, pMaxX.x, col)) {
            while (matrix[x][pMinX.y] == 0
                    && matrix[x][pMaxX.y] == 0) {
                if (checkbtnHangNgang(pMinX.y, pMaxX.y, x)) {
                    return true;
                }
                x += type;
            }
        }
        return false;
    }
}
