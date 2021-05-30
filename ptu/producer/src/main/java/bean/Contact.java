package bean;

public class Contact extends Data{
    private String tel;
    private String name;

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setValue(Object value){
        content = (String) value;
        String[] values = content.split("\t");
        setName(values[1]);
        setTel(values[0]);
    }
    @Override
    public String toString() {
        return "Contact [tel=" + tel + ", name=" + name + "]";
    }
}
