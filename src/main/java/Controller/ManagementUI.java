package Controller;


import Model.Car;
import Model.CarTableModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ManagementUI {
    private JTextField tfName;
    private JTextField tfColor;
    private JTextField tfPrice;
    public JTable table1;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton deleteButton;
    private JButton theMostExpensiveButton;
    private JPanel myPanel;
    private JScrollPane jScroll;
    private JComboBox cmbBranch;
    private JButton findButton;

    public static ArrayList<Car> listCar = new ArrayList<>();
    public static int id = 1;
    private final String PATH = "file/listcar.json";
    private final String[] COLUMNS = {"ID", "TÊN XE", "HÃNG XE", "MÀU XE", "GIÁ TIỀN"};
    public final String[] BRANCHS = {"BMW", "Rolls-Royce", "Mercedes-Benz", "Land Rover", "Audi", "Porsche"};
    private static IOFile ioFile = new IOFile();
    private static Logger logger = Logger.getLogger(ManagementUI.class.getName());


    public void showData() {
        //doc file json
        listCar = ioFile.readFile();
        //Logging file
        logger.info("Đọc file json");
        //show data len table
        CarTableModel carTableModel = new CarTableModel(COLUMNS, listCar);
        table1.setModel(carTableModel);
    }

    public void deleteData(ArrayList<Car> cars, int index) {
        if (index == -1) {
            JOptionPane.showMessageDialog(myPanel, "Chọn item để xóa");
        } else if (cars.size() == 0) {
            JOptionPane.showMessageDialog(myPanel, "Không có dữ liệu cần xóa");
        } else {
            cars.remove(index);
            ioFile.writeFile(cars, PATH);
        }
    }

    public ManagementUI() {
        //Set comboBox
        cmbBranch.setModel(new DefaultComboBoxModel(BRANCHS));
        //Show data
        showData();

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Thêm một item vào danh sách");
                ArrayList<Car> listResult = new ArrayList<>();

                listResult = ioFile.readFile();
                //Nếu listResult đã co data thì gán id = current car.id +1
                if (listResult.size() > 0) {
                    id = listResult.get(listResult.size() - 1).getId() + 1;
                }
                String name = tfName.getText();
                String branch = cmbBranch.getSelectedItem().toString();
                String color = tfColor.getText();
                String priceTemp = tfPrice.getText();

                if (branch.isEmpty() || color.isEmpty() || priceTemp.isEmpty()) {
                    JOptionPane.showMessageDialog(myPanel, "Vui lòng nhập đầy đủ thông tin");
                } else {
                    try {
                        float price = Float.parseFloat(priceTemp);
                        Car car = new Car(id, name, branch, color, price);
                        id++;
                        listResult.add(car);
                        listCar = listResult;
                        ioFile.writeFile(listCar, PATH);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(myPanel, "Nhập lại số");
                    }
                }
                // Hien thi len bang
                showData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Xóa một item");
                int rowIndex = table1.getSelectedRow();
                deleteData(listCar, rowIndex);
                showData();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Update thông tin một item");
                int index = table1.getSelectedRow();
                if (index == -1) {
                    JOptionPane.showMessageDialog(myPanel, "Chọn một item để sửa");
                } else if (listCar.size() == 0) {
                    JOptionPane.showMessageDialog(myPanel, "Không có dữ liệu để sửa");
                } else {
                    listCar.get(index).setName(tfName.getText());
                    int indexCombo = cmbBranch.getSelectedIndex();
                    String itemCombo = (String) cmbBranch.getItemAt(indexCombo);
                    listCar.get(index).setBranch(itemCombo);
                    listCar.get(index).setColor(tfColor.getText());
                    listCar.get(index).setPrice(Float.parseFloat(tfPrice.getText()));
                    ioFile.writeFile(listCar, PATH);
                    showData();
                }
            }
        });

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Tìm kiếm xe theo hãng");
                listCar = ioFile.readFile();
                ArrayList<Car> tempList = new ArrayList<>();
                String branchCar = cmbBranch.getSelectedItem().toString();
                for (Car car : listCar) {
                    if (car.getBranch().equals(branchCar)) {
                        tempList.add(car);
                    }
                }
                CarTableModel carTableModel = new CarTableModel(COLUMNS, tempList);
                table1.setModel(carTableModel);
            }
        });

        theMostExpensiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Tìm danh sách các xe đắt nhất");
                float maxPrice = listCar.get(0).getPrice();
                int index = 0;
                List<Car> maxCars = new ArrayList<>();
                //Tim xe co gia dat nhat
                for (int i = 0; i < listCar.size(); i++) {
                    if (maxPrice < listCar.get(i).getPrice()) {
                        maxPrice = listCar.get(i).getPrice();
                        index = i;
                    }
                }
                //Tim cac xe co gia dat nhat
                for (int i = 0; i < listCar.size(); i++) {
                    if (listCar.get(i).getPrice() == listCar.get(index).getPrice()) {
                        maxCars.add(listCar.get(i));
                    }
                }
                CarTableModel carTableModel = new CarTableModel(COLUMNS, maxCars);
                table1.setModel(carTableModel);
            }
        });

        //Click item and show up textfield
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.rowAtPoint(e.getPoint());
                Car car = listCar.get(row);
                tfName.setText(car.getName());
                String combo = car.getBranch();
                for (int i = 0; i < BRANCHS.length; i++) {
                    if (BRANCHS[i].equals(combo)) {
                        cmbBranch.setSelectedItem(BRANCHS[i]);
                    }
                }
                tfColor.setText(car.getColor());
                tfPrice.setText(String.valueOf(car.getPrice()));
                //Ghi log
                String log = "click vào " + car.getName() + " " + car.getBranch() + " " + car.getColor() + " " + car.getPrice();
                logger.info(log);
            }
        });


        tfName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfName.setText("");
            }
        });
        tfColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfColor.setText("");
            }
        });
        tfPrice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfPrice.setText("");
            }
        });

        tfPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLetter(c)) {
                    tfPrice.setEditable(false);
                    JOptionPane.showMessageDialog(myPanel, "Vui lòng nhập số");
                } else {
                    tfPrice.setEditable(true);
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý xe");
        frame.setContentPane(new ManagementUI().myPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 400);
        frame.setVisible(true);
        //Thuc thi luong ghi danh sach 5 xe gia cao nhat vao file report
        ThreadWriteFile th = new ThreadWriteFile(ioFile, listCar);
        th.start();

    }
}

