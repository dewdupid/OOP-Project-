/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import java.io.*;
import java.util.ArrayList;

public class InventoryManager {

    private ArrayList<String> products = new ArrayList<>();

    // ADD
    public void addProduct(String name, int qty, double price) {
        products.add(name + " | " + qty + " | " + price);
    }

    // DELETE specific item
    public void deleteProduct(String item) {
        products.remove(item);
    }

    // SAVE FILE (IO)
    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter("product.txt");

            for (String p : products) {
                fw.write(p + "\n");
            }

            fw.close();
            System.out.println("Saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOAD FILE (IO)
    public ArrayList<String> loadFromFile() {
        ArrayList<String> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("product.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
                products.add(line);
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}