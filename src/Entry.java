import java.time.Clock;

public class Entry {

    Object key;
    Object value;
    Entry next;
    Entry previous;
    long expiryTime;

    public Entry(Object key, Object value, int retententionInMillis, Clock clock) {
        this.key = key;
        this.value = value;
        this.expiryTime = clock.millis() + retententionInMillis;
    }
    public boolean isExpired(Clock clock) {
        return  clock.millis() >= expiryTime;
    }
}