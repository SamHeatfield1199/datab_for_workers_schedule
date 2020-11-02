package sample;

public class User {

    private String first;
    private String second;
    private String day;

    public User(String day, String first, String second) {
        this.first = first;
        this.second = second;
        this.day = day;
    }

    public User() {
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String first) {
        this.second = second;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String email) {
        this.day = day;
    }
}
