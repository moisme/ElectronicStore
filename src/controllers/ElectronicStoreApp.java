package controllers;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;
import models.ElectronicStore;
import models.Product;
import views.ButtonPane;
import views.ElectronicStoreView;

import javax.swing.*;
import java.util.Scanner;

//controller

public class ElectronicStoreApp  extends Application {

    ElectronicStore model;
    ElectronicStoreView view;
    private int selectedIndex;
    private Product selectedProduct;

    public void start(Stage primaryStage) {

        model = ElectronicStore.createStore();
        view = new ElectronicStoreView();

        Pane aPane = new Pane();

        // Create the view

        aPane.getChildren().add(view);

        primaryStage.setTitle("Electronic Store App - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();

        view.resetUpdate(model);

        view.getButtonPane().getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                //adds the selected index of the stock list to the cart (1), enables complete
                addToCart(selectedProduct);
                view.getButtonPane().getCompleteButton().setDisable(false);
                view.update(model);

            }
        });

        view.getStockVList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

               //find selected item
                selectedIndex = view.getStockVList().getSelectionModel().getSelectedIndex();
                selectedProduct = model.getStock().get(selectedIndex);
                view.getButtonPane().getAddButton().setDisable(false);

                view.update(model);

            }
        });

        view.getButtonPane().getResetButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                //ListView<String> viewList = view.getTitleList();

                view.resetUpdate(model);

            }
        });

        view.getCartVList().setOnMousePressed(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent mouseEvent) {

                view.getButtonPane().getRemoveButton().setDisable(false);


                view.update(model);

            }
        });


        view.getButtonPane().getRemoveButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                boolean inStock = false;

                //get the item they want to remove
                String selectedItem = view.getCartVList().getSelectionModel().getSelectedItem();

                //search through stock, see if the selected item exists in the stock
                for (Product i : model.getStock()) {

                    if (selectedItem.contains(i.toString())) {

                        //increase that items stock if it exists
                        i.setStockQuantity(i.getStockQuantity() + 1);

                        //if there's more than 1 of the selected item in the cart, remove one, if there's only 1, remove one and remove it from the hashmaop
                        if (model.getItemsAdded().get(i) > 1) {
                            model.getItemsAdded().replace(i, model.getItemsAdded().get(i) - 1);
                        }
                        else {
                            model.getItemsAdded().replace(i, model.getItemsAdded().get(i) - 1);
                            model.getItemsAdded().remove(i);
                        }

                        //when removed, the carts revenue should be updated so subtract it off
                        model.setRevenue(model.getRevenue() - i.getPrice());
                        inStock = true;
                    }
                }

                //if the selected item wasnt in the stock (for example all ten are in the cart)
                if (!inStock) {

                    //find it within the initial stock and add it to the real stock list
                    for (Product i : model.getInitialStock()) {

                        //remove if there's only one left in the cart
                        if (selectedItem.contains(i.toString()) && model.getItemsAdded().get(i) == 1) {

                            System.out.println("Removing item that exists and only one in cart");

                            model.getStock().add(i);
                            i.setStockQuantity(1);
                            model.getItemsAdded().remove(i);
                            model.setRevenue(model.getRevenue() - i.getPrice());


                        }
                        //else just subtract it by one
                        else if(selectedItem.contains(i.toString()) && model.getItemsAdded().get(i) > 1){
                            System.out.println("Removing item that exists. with more than one in cart");

                            model.getItemsAdded().replace(i,model.getItemsAdded().get(i) - 1 );
                            model.getStock().add(i);
                            i.setStockQuantity(1);
                            model.setRevenue(model.getRevenue() - i.getPrice());

                        }

                    }

                }

                    if (model.getItemsAdded().isEmpty()) {

                        view.getButtonPane().getRemoveButton().setDisable(true);
                    }

                    view.getButtonPane().getRemoveButton().setDisable(true);
                    if(model.getItemsAdded().isEmpty()){
                        view.getButtonPane().getCompleteButton().setDisable(true);
                    }

                    view.update(model);

            }
        });

        view.getButtonPane().getCompleteButton().setOnMousePressed(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent mouseEvent) {

                if(!model.getItemsAdded().isEmpty()){

                    for(Product p: model.getItemsAdded().keySet()){

                        if(!model.getItemsBought().contains(p)) {
                            model.getItemsBought().add(p);
                        }

                    }

                    view.getMostPopularVList().setItems(FXCollections.observableArrayList(model.mostSold(model, model.getItemsBought())));
                   // view.getMostPopularVList().setItems(FXCollections.observableArrayList((model.getItemsAdded())));

                    model.setTotalRevenue(model.getTotalRevenue() + model.getRevenue());

                    model.setItemSoldCount(model.getItemSoldCount() + 1);
                    view.getRevTF().setText(" " + model.getTotalRevenue() );
                    view.getDollarsTF().setText(" " + (model.getTotalRevenue() / model.getItemSoldCount()) );

                    model.setRevenue(0);
                    view.getCartLabel().setText("Current Cart ( $" + model.getRevenue() +"):" );
                    model.getItemsAdded().clear();

                    //sell units of that item, set amount sold to itself + amount sold

                }

                view.getButtonPane().getCompleteButton().setDisable(true);
                view.getButtonPane().getRemoveButton().setDisable(true);


                view.update(model);

            }
        });


    }

    public void handle(ActionEvent actionEvent) {

        view.update(model);
    }

    public void addToCart(Product p) {

        boolean added = false;

        //for all the products that have been added
        for (Product i : model.getItemsAdded().keySet()) {

            //check if the selected product has been added before
            if (i.toString().contains(p.toString())) {
                System.out.println("first if: " +  i.toString() + " " + i.getStockQuantity());

                //if there is sufficient stock to sell
                if ( i.getStockQuantity() > 1) {

                    //add one item to the hashmap of cart items, mark as added
                    model.getItemsAdded().replace(i, model.getItemsAdded().get(i) + 1);
                    //sell units will update the product's stock and product's # sold within this set call
                    model.setRevenue(model.getRevenue() + i.sellUnits(1));
                    added = true;
                }
                //if one remains in stock(cant sell any more after)
                else if(i.getStockQuantity() == 1){

                    //add last one, and remove that item from stock
                    model.getItemsAdded().replace(i, model.getItemsAdded().get(i) + 1);
                    //sell units will update the product's stock and product's # sold within this set call
                    model.setRevenue(model.getRevenue() + i.sellUnits(1));

                    model.getStock().remove(i);

                    view.getButtonPane().getAddButton().setDisable(true);
                    System.out.println("here");
                    added = true;
                }
                else{
                    model.getStock().remove(i);
                    //if it exists but, there's no more stock left, mark as added to avoid mis-ads
                   added = true;

                }
            }
        }

        //if the same item wasnt already in the cart, add 1
        if(!added)
        {
            System.out.println( p.toString() + " " + p.getStockQuantity());

            model.getItemsAdded().put(p, 1);
            added = true;
            model.setRevenue(model.getRevenue() + p.sellUnits(1));

            if(p.getStockQuantity() == 0){
                System.out.println("remove " + p);

                model.getStock().remove(p);
                view.getButtonPane().getAddButton().setDisable(true);
            }

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
