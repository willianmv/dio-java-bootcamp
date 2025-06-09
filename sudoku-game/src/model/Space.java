package model;

public class Space {

    private Integer actualNumber;
    private final int expected;
    private final boolean fixed;

    public Space(int expected, boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if(fixed) actualNumber = expected;
    }

    public void setActualNumber(Integer actualNumber) {
        if(fixed) return;
        this.actualNumber = actualNumber;
    }

    public void clearSpace(){
        setActualNumber(null);
    }

    public Integer getActualNumber() {
        return actualNumber;
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }
}
