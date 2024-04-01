
import java.time.Clock;
//
public class Entry {

    Object key;
    Object value;
    int retententionInMillis;
    Entry next;
    Entry previous;
    long createdTime;

    public Entry(Object key, Object value, int retententionInMillis, Clock clock) {
        this.key = key;
        this.value = value;
        this.retententionInMillis = retententionInMillis;
        this.createdTime = clock.millis();
    }
    public boolean isExpired(Clock clock) {
        return  clock.millis() > this.createdTime + this.retententionInMillis;
    }
}