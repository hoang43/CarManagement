package Model;

import Model.Car;
import org.apache.log4j.Logger;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CarTableModel extends AbstractTableModel {
    private String[] COLUMNS = {};
    private List<Car> cars = new ArrayList<>();

    public CarTableModel(String[] COLUMNS, List<Car> cars) {
        this.COLUMNS = COLUMNS;
        this.cars = cars;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public int getRowCount() {
        if (cars != null) {
            return cars.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        if (COLUMNS != null) {
            return COLUMNS.length;
        }
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Car c = cars.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getName();
            case 2:
                return c.getBranch();
            case 3:
                return c.getColor();
            case 4:
                return c.getPrice();
            default:
                return "";

        }
    }
}
