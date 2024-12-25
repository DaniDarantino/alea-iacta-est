public class Cell<Integer,String> {
    private final Integer value;
    private String tokenStr;

    public Cell(Integer value, String tokenStr) {
        this.value = value;
        this.tokenStr = tokenStr;
    }

    public Integer getValue() {
        return value;
    }

    public String getTokenStr() {
        return tokenStr;
    }

    public void setTokenStr(String tokenStr){
        this.tokenStr = tokenStr;
    }
}
