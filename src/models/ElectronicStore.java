package models;//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.util.*;

public class ElectronicStore {
    //public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
   // private int curProducts;
    private String name;
    private ArrayList<Product> stock, itemsBought; //Array to hold all products
    private ArrayList<Product> threeItems; //Array to hold all products
    private HashMap<Product,Integer> itemsAdded;
    private double revenue;
    private double totalRevenue;
    private int itemSoldCount;

    //  private HashMap<Customer,String> customers;
    private final ArrayList<Product> initialStock;

    public ElectronicStore(String initName) {
        revenue = 0.00;
        name = initName;
        stock = new ArrayList<>();
        itemsBought = new ArrayList<>();
        initialStock = new ArrayList<>();
        threeItems = new ArrayList<>();
        itemsAdded = new HashMap<>();
        revenue = 0.0;
        totalRevenue = 0.0;
        itemSoldCount = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> resetStock(ArrayList<Product> initList) {

        //resets "stock" or any list that i pass in
        initList.clear();
        initList.addAll(initialStock);

        return initList;
    }

    public ArrayList<Product> getThreeItems() {
        return threeItems;
    }

    public HashMap<Product, Integer> getItemsAdded() {
        return itemsAdded;
    }

    public ArrayList<Product> getStock() {
        return stock;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getRevenue() {
        return revenue;
    }

    public ArrayList<Product> getInitialStock() {
        return initialStock;
    }

    public int getItemSoldCount() {
        return itemSoldCount;
    }

    public void setItemSoldCount(int itemSoldCount) {
        this.itemSoldCount = itemSoldCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public ArrayList<Product> getItemsBought() {
        return itemsBought;
    }

    public void setItemsBought(ArrayList<Product> itemsBought) {
        this.itemsBought = itemsBought;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {

        boolean shouldAdd = true;

        for (Product product : stock) {

            if (newProduct.toString().equals(product.toString())) {
                shouldAdd = false;
                //if the product's to string matches anothers tostring, do not add it and break out
            }
        }

        if (shouldAdd) {
            stock.add(newProduct);
            initialStock.add(newProduct);
            //if has searched through and found no duplicates
        }

        return shouldAdd;

    }

    public void threeItems(){

        threeItems.add(initialStock.get(0));
        threeItems.add(initialStock.get(1));
        threeItems.add(initialStock.get(2));

    }

    public ArrayList<String> createCartList(){
        //turns hashmap of cartitems to a arraylist so it can be passed into the list view

        ArrayList<String> newList = new ArrayList<>();
        for(Product p : itemsAdded.keySet() ){

            newList.add(itemsAdded.get(p).toString() + "x " + p.toString());
        }

        return newList;

    }

    public ArrayList<Product> cartItemsAL(HashMap<Product,Integer> initHM){

        ArrayList<Product> initAL = new ArrayList<>(initHM.keySet());
        return initAL;
    }


    public List<Product> mostSold(ElectronicStore model, ArrayList<Product> initList){

        TreeSet<Product> sortedProducts = new TreeSet<Product>(initList);
        //refers to comparable method in song class
        ArrayList<Product> cutOffList = new ArrayList<>(sortedProducts);

        if(cutOffList.size() > 3) {
            return cutOffList.subList(0, 3);
        }
        else{
            //fill list if its not length 3
            for(Product p : model.stock)
            {
              if(p.getSoldQuantity() == 0) {
                  cutOffList.add(p);
              }
              if(cutOffList.size() == 3){
                  break;
              }
            }
            return cutOffList.subList(0,3);
        }

    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        store1.threeItems();
        return store1;
    }
}
