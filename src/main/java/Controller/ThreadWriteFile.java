package Controller;

import Controller.IOFile;
import Controller.ManagementUI;
import Model.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ThreadWriteFile extends Thread {

    private IOFile ioFile = new IOFile();
    private ArrayList<Car> list = new ArrayList<>();

    public ThreadWriteFile(IOFile ioFile, ArrayList<Car> list) {
        this.ioFile = ioFile;
        this.list = list;
    }

    @Override
    public void run() {
        ioFile.writeListMaxPrice(list);
    }
}
