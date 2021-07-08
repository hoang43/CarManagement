package Controller;

import Model.Car;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class IOFile {

    private final int DELAYTIME = 3600000;

    public void writeListMaxPrice(ArrayList<Car> list) {
        while (true) {
            list = readFile();
            ArrayList<Car> tempList = list;
            Collections.sort(tempList, Car.carComparator);
            ArrayList<Car> listMaxPrice = new ArrayList<>();
            int size = tempList.size();
            if (size == 0) {
                System.out.println("File rỗng");
            } else if (size < 5) {
                for (Car c : tempList) {
                    listMaxPrice.add(c);
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    Car car = new Car();
                    car = tempList.get(i);
                    listMaxPrice.add(car);
                }
            }
            //Lay ngay gio de tao ten file
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.mm.ss");
            String format = localDateTime.format(dateTimeFormatter);
            String fileName = new String("fileReport/report");
            fileName += format;
            fileName += ".json";
            //Ghi file
            writeFile(listMaxPrice, fileName);
            try {
                Thread.sleep(DELAYTIME/6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile(ArrayList<Car> list, String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), list);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Car> readFile() {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            InputStream inputStream = new FileInputStream(new File("file/listcar.json"));
            TypeReference<ArrayList<Car>> typeReference = new TypeReference<ArrayList<Car>>() {
            };
            cars = mapper.readValue(inputStream, typeReference);
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("không tìm thấy file");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
