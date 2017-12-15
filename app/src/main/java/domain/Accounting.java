package domain;

@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Accounting {
    private String dateStart;
    private String dateEnd;
    private String volume;

    public Accounting(String dateStart, String dateEnd, String volume) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.volume = volume;
    }

    public Accounting() {

    }

}
