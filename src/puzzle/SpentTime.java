package puzzle;

import javax.swing.*;

class SpentTime {
    private int seconds = 0;
    private int minutes = 0;

    private Thread thread;

    private boolean threadIsActive = true;

    SpentTime() {
        this.thread = new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                while (threadIsActive) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    this.addSecond();
                }
            });
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e)  {
            e.printStackTrace();
        }
    }

    public void stop() {
        thread.stop();
        threadIsActive = false;
    }

    public void addSecond() {
        this.seconds++;

        if (seconds == 61) {
            seconds = 1;
            minutes++;
        }
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public String toString() {
        if (minutes > 0) {
            return this.minutes + " min. " + this.seconds + "sec.";
        }

        return this.seconds + "sec.";
    }
}
