package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;


public class Countdown {
    private int interval;
    private Timer timer;


    public void startCountdown(final UI ui, final int duration) {
        int delay = 1000;
        int period = 1000;
        timer = new Timer(true);
        interval = duration;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                Platform.runLater(() -> {
                    ui.updateCountdown(setInterval());
                });
            }
        }, delay, period);
    }

    private int setInterval() {
        if (interval == 1) {
            System.out.println("Cancelled!");
            this.timer.cancel();
            this.timer = null;
        }
        return --interval;
    }
}
