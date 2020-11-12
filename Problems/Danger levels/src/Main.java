enum DangerLevel {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private int level;

    DangerLevel(int level) {
        this.level = level;
    }

    int getLevel() {
        return level;
    }

    void setLevel(int newLevel) {
        this.level = newLevel;
    }

}

public class Main {
    public static void main(String[] args) {
        DangerLevel high = DangerLevel.HIGH;
        DangerLevel medium = DangerLevel.MEDIUM;

        System.out.println(high.getLevel() > medium.getLevel()); // true

        high.setLevel(42);
        System.out.println(high.getLevel());
        System.out.println(medium.getLevel());

    }
}