package domain;

public class Accounting {
    private String dateStart;
    private String dateEnd;
    private String volume;

    public Accounting(String dateStart, String dateEnd, String volume) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.volume = volume;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
