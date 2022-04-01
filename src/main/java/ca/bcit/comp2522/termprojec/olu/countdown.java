package ca.bcit.comp2522.termprojec.olu;

import java.util.Timer;
import java.util.TimerTask;

public class countdown {
    static int interval;
    static Timer timer;

    public void startCountdown(UI ui, int duration) {
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = duration;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                ui.updateCountdown(setInterval());
            }
        }, delay, period);
    }

    private static final int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }
}