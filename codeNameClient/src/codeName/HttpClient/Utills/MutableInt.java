package codeName.HttpClient.Utills;

public class MutableInt {
    private int value;

    public MutableInt(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }

    public void increment(int amount) {
        this.value += amount;
    }
}
