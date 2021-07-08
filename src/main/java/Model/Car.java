package Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Comparator;

@JsonDeserialize
public class Car implements Serializable {
    private int id;
    private String name;
    private String branch;
    private String color;
    private float price;

    public Car(int id, String name, String branch, String color, float price) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.color = color;
        this.price = price;
    }

    public Car() {
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBranch(String type) {
        this.branch = type;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getBranch() {
        return branch;
    }

    public String getColor() {
        return color;
    }

    public float getPrice() {
        return price;
    }


    public static Comparator<Car> carComparator = new Comparator<Car>() {
        @Override
        public int compare(Car c1, Car c2) {
            float priceCar1 = c1.getPrice();
            float priceCar2 = c2.getPrice();
            return (int) (priceCar2 - priceCar1);
        }
    };

}
