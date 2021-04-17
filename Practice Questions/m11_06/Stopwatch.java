import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Stopwatch extends Label {

    private boolean isStarted = false;

    private Instant start;
    private Instant finish;
    private long timeElapsedNano;

    private Timeline timeline;
    private String text = "00:00:000";

    public Stopwatch() {
        setText(text);
    }

    public void start() {
        if (timeline != null) {
            timeline.stop();
        }

        isStarted = true;
        setText(text);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.millis(1),
            e -> {
                finish = Instant.now();
                timeElapsedNano = java.time.Duration.between(start, finish).toNanos();

                long min = TimeUnit.NANOSECONDS.toMinutes(timeElapsedNano);
                long sec = TimeUnit.NANOSECONDS.toSeconds(timeElapsedNano) - TimeUnit.MINUTES.toSeconds(min);
                long milli = TimeUnit.NANOSECONDS.toMillis(timeElapsedNano) - TimeUnit.SECONDS.toMillis(sec) - TimeUnit.MINUTES.toMillis(min);

                text = String.format("%02d:%02d:%03d", min, sec, milli);
                setText(text);
            }));

        start = Instant.now();
        timeline.playFromStart();
    }

    public void stop() {
        isStarted = false;
        timeline.stop();
    }

    public boolean isStarted() {
        return isStarted;
    }
}