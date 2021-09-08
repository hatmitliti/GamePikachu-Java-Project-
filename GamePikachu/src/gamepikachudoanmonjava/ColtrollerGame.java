/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepikachudoanmonjava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Acer
 */
public class ColtrollerGame extends JPanel implements ActionListener {

    private int row;
    private int col;
    private int bound = 2;
    private int size = 50;
    private JButton[][] btn;
    private  MaTrixGame controller;
    private Color backGroundColor = Color.BLACK;
    private MainFrame frame;

    private Point p1 = null;
    private Point p2 = null;
    private PointLine line;
    private int score = 0;
    private int item;

    // 
    private String DiemIO;
    private int STTIO;

    public ColtrollerGame(MainFrame frame,int row, int col) {
        this.frame = frame;
        this.row = row + 2;
        this.col = col + 2;
        item = row * col / 2;
        setLayout(new GridLayout(row, col, bound, bound));
        setBackground(backGroundColor);
        setPreferredSize(new Dimension((size + bound) * col, (size + bound) * row));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setAlignmentY(JPanel.CENTER_ALIGNMENT);
        newGame();
    }

    public void excute(Point p1, Point p2) {
        setDisable(btn[p1.x][p1.y]);
        setDisable(btn[p2.x][p2.y]);
    }

    public void setDisable(JButton btn) {
        btn.setIcon(null);
        btn.setBackground(backGroundColor);
        btn.setEnabled(false);
    }

    public void newGame() {
        controller = new  MaTrixGame(this.frame, this.row, this.col);
        addArrayButton();
    }

    private void addArrayButton() {
        btn = new JButton[row][col];
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                btn[i][j] = createButton(i + "," + j);
                Icon icon = getIcon(controller.getMatrix()[i][j]);
                btn[i][j].setIcon(icon);
                add(btn[i][j]);
            }
        }
    }

    private Icon getIcon(int index) {
        int width = 48, height = 48;
        Image image = new ImageIcon(this.getClass().getResource("/icongamePikachu/" + index + ".jpg")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
        return icon;

    }

    private JButton createButton(String action) {
        JButton btn = new JButton();
        btn.setActionCommand(action);
        btn.setBorder(null);
        btn.addActionListener(this);
        return btn;
    }

    public static int getSTT(String fileName) {
        try {
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                i++;
            }
            fr.close();
            br.close();
            return i;
        } catch (IOException ex) {
            System.out.println("Loi Doc File: " + ex);
            return 0;
        }
    }

    public static String[][] docFile(String fileName) {
         int i = getSTT(fileName);
        String[][] S1 = new String[i][3];
        try {
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
           
            String line;
            String[][] S = new String[i][3];
             for(int a = 0;(line = br.readLine()) != null && a<i;a++) {
                S1[a] = line.split("#");
            }
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < 3; k++) {
                    S[j][k] = S1[j][k];
                }
            }
            fr.close();
            br.close();
            return S;
        } catch (IOException ex) {
            System.out.println("Loi Doc File: " + ex);
            return null;
        }
    }

    public void ghiFile(String fileName) {
        int STT = getSTT(fileName) + 1;
        String content = STT + "#" + score + "#" + java.time.LocalDate.now() + "\n";
        try {
            File f = new File(fileName);
            try (FileWriter fw = new FileWriter(f.getAbsoluteFile(),true)) {
                fw.write(content);
            }
        } catch (IOException ex) {
            System.out.println("Loi Ghi File: " + ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String vitribtn = e.getActionCommand();
        int indexDot = vitribtn.lastIndexOf(",");
        int x = Integer.parseInt(vitribtn.substring(0, indexDot));
        int y = Integer.parseInt(vitribtn.substring(indexDot + 1, vitribtn.length()));

        if (p1 == null) {
            p1 = new Point(x, y);
            btn[p1.x][p1.y].setBorder(new LineBorder(Color.YELLOW));
        } else {
            p2 = new Point(x, y);
            line = controller.check2button(p1, p2);
            if (line != null) {
                controller.getMatrix()[p1.x][p1.y] = 0;
                controller.getMatrix()[p2.x][p2.y] = 0;
                excute(p1, p2);
                line = null;
                score += 1;
                item--;
                frame.time++;
                frame.lbScore.setText(score + "");
            }
            btn[p1.x][p1.y].setBorder(null);
            p1 = null;
            p2 = null;
        }
        if (item == 0 && frame.time > 0) {
            ghiFile("D:\\TaiLieuIT\\Hoc ki 3\\Java\\MyJava\\DoAnMonJava_TranNgocNam_NguyenCongDang_SangThu4\\GamePikachu\\DSD.txt");
            frame.showDialogNewGame("bạn Đã Thắng!\n Bạn Có Muốn Chơi Lại Lần Nữa?", "Win", 1);
        }
        if (item > 0 && frame.time == 0) {
            frame.showDialogNewGame("bạn Đã Thua!\n Bạn Có Muốn Chơi Lại Lần Nữa?", "Lose", 1);
             ghiFile("D:\\TaiLieuIT\\Hoc ki 3\\Java\\MyJava\\DoAnMonJava_TranNgocNam_NguyenCongDang_SangThu4\\GamePikachu\\DSD.txt");
        }
    }
}
