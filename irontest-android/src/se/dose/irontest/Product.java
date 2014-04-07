package se.dose.irontest;

public class Product {
    public final static String PRODUCT_ID = "se.dose.irontest.PRODUCT_ID";

    private long id;
    private String name;
    private String status;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "<" + id + ", " + name + ", " + description.substring(0, 10) + ", " + status + ">";
    }
}
