/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepikachudoanmonjava;

import java.io.IOException;


/**
 *
 * @author Acer
 */
public class GamePikachuDoAnMonJava {

    static MainFrame frame;

    public GamePikachuDoAnMonJava() throws IOException {
        frame = new MainFrame();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        GamePikachuDoAnMonJava gamePikachuDoAnMonJava = new GamePikachuDoAnMonJava();
        Time time = new Time();
        time.start();
        new Thread(frame).start();
    }

    static class Time extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                   
                        e.printStackTrace();

                }
                if (frame.isPause()) {
                    if (frame.isResume()) {
                        frame.time--;
                    }
                } else {
                    frame.time--;
                }
                if (frame.time == 0) {
                    frame.showDialogNewGame("Hêt Giờ \n bạn Có Muốn Chơi Lại Lần Nữa?", "Lose", 1);
                }
            }
        }
    }
}
