import java.time.Clock;

public class SimpleCache {
    Entry head;
    Clock clock;

    public SimpleCache(Clock clock) {
        this.clock = clock;
    }

    public SimpleCache() {
        this.clock = Clock.systemDefaultZone();
    }

    public void put(Object key, Object value, int retentionInMillis) {
        if (head == null)
        {
            head = new Entry(key, value, retentionInMillis, clock);
        }
        else {
            Entry currentEntry = head;
            Entry previousEntry = null;

            while (currentEntry.next != null)
            {
                previousEntry = currentEntry;
                currentEntry = currentEntry.next;
            }
            currentEntry.next = new Entry(key, value, retentionInMillis, clock);
            currentEntry.next.previous = previousEntry;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        if (head == null)
        {
            return 0;
        }
        removeExpiredEntries();

        Entry currentEntry = head;
        int count = 0;
        while (currentEntry.next != null)
        {
            count++;
            currentEntry = currentEntry.next;
        }
        return count+1;
    }

    public Object get(Object key) {
        if (head == null)
        {
            return null;
        }

        removeExpiredEntries();

        Object result = null;
        Entry currentEntry = head;
        while (currentEntry.next != null)
        {
            if (currentEntry.key == key) {
                result = currentEntry.value;
                break;
            }
            currentEntry = currentEntry.next;
        }
        if (currentEntry.key == key) {
            result = currentEntry.value;
        }
        return result;
    }
    public void removeExpiredEntries() {
        if (head.isExpired(clock)) {
            if (head.next != null) {
                head = head.next;
            } else {
                head = null;
            }
        }

        if (head == null)
        {
            return;
        }

        Entry currentEntry = head;
        while (currentEntry.next != null)
        {
            if (currentEntry.isExpired(clock)) {
                currentEntry.previous.next = currentEntry.next;
            }
            currentEntry = currentEntry.next;
        }
        if (currentEntry.isExpired(clock)) {
            currentEntry.previous.next = null;
        }
    }
}