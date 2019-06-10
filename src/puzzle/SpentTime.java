package puzzle;

import java.text.SimpleDateFormat;
import java.util.Date;

class SpentTime {
    private Date started;
    private Date ended;

    private SimpleDateFormat formatter;

    SpentTime() {
        this.formatter = new SimpleDateFormat("mm:ss");
        this.started = new Date(System.currentTimeMillis());
    }

    public void stop() {
        this.ended = new Date(System.currentTimeMillis());
    }

    public String toString() {
        if (ended == null) {
            return formatter.format(new Date(System.currentTimeMillis() - started.getTime()));
        }

        return formatter.format(new Date(ended.getTime() - started.getTime()));
    }
}
