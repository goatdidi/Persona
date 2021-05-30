package bean;

public class Data implements Value {

    protected String content;

    public void setValue(Object value) {
        this.content = (String)value;
    }

    @Override
    public Object getValue() {
        return content;
    }

}
