package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Countdown timer.
 * @author Urjit, Leo
 * @version 2022
 */
public class Countdown {
    private static int interval;
    private static Timer timer;

    /**
     * Starts new countdown.
     * @param ui UI to add timer to
     * @param duration Duration of the timer
     */
    public void startCountdown(final UI ui, final int duration) {
        final int delay = 1000;
        final int period = 1000;
        timer = new Timer(true);
        interval = duration;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                Platform.runLater(() -> {
                    int time = setInterval();
                    System.out.println(time);
                    ui.updateCountdown(time);
                });
            }
        }, delay, period);
    }

    private int setInterval() {
        if (interval == 1) {
            System.out.println("Cancelled!");
            timer.cancel();
            timer = null;
        }
        return --interval;
    }

    /**
     * Stops timer.
     */
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }
}
