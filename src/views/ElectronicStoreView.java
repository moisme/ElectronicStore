package views;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.ElectronicStore;
import models.Product;
import java.util.ArrayList;

public class ElectronicStoreView extends Pane {

    private ListView<Product> mostPopularVList,stockVList;
    private ListView<String> cartVList;
    private ButtonPane buttonPane;
    private TextField dollarsTF = new TextField();
    private TextField revTF = new TextField();
    private TextField salesTF = new TextField();
    private Label cartLabel = new Label();


    public ListView<Product> getMostPopularVList() { return mostPopularVList; }
    public ListView<Product> getStockVList() { return stockVList; }
    public ListView<String> getCartVList() { return cartVList; }
    public ButtonPane getButtonPane() { return buttonPane; }
    public TextField getRevTF() { return revTF; }
    public Label getCartLabel() {return cartLabel;}
    public TextField getDollarsTF() {return dollarsTF;}

    public void update(ElectronicStore model){

        //create three arrays for the listviews

        if(stockVList.getSelectionModel().getSelectedIndex() < 0){
            getButtonPane().getAddButton().setDisable(true);
        }

        ArrayList<Product> inStock = new ArrayList<Product>(model.getStock());
        ArrayList<Product> mostPopular = new ArrayList<Product>();
        //ArrayList<Product> stock = new ArrayList<Product>();
        ArrayList<String> cart = new ArrayList<String>();

        //add stock to stock view and an arraylist of added items(converted from hashmap) to cart VL
        stockVList.setItems(FXCollections.observableArrayList(inStock));
        cartVList.setItems(FXCollections.observableArrayList(model.createCartList()));

        //puts the current items total revenue in the cart
        cartLabel.setText("Current Cart ( $" + model.getRevenue() +"):" );
        salesTF.setText(" " + model.getItemSoldCount());

       //System.out.println("Check");

    }

    public void resetUpdate(ElectronicStore model){

        //resets to default values of GUI
        salesTF.setText(" " + 0);
        salesTF.setEditable(false);

        //model.getItemSold

        dollarsTF.setText(" N/A " );
        dollarsTF.setEditable(false);

        revTF.setText(" " + 0.00);
        revTF.setEditable(false);

        stockVList.setItems(FXCollections.observableArrayList(model.resetStock(model.getStock())));

        model.getItemsAdded().clear();

        cartVList.setItems(FXCollections.observableArrayList((model.createCartList())));

        mostPopularVList.setItems(FXCollections.observableArrayList(model.getThreeItems()));

        buttonPane.getAddButton().setDisable(true);

        buttonPane.getCompleteButton().setDisable(true);

        buttonPane.getRemoveButton().setDisable(true);

        model.setRevenue(0.00);
        model.setItemSoldCount(0);


        //makes sure all items are back to normal stock values
        for(Product p: model.getStock()){

            p.setSoldQuantity(0);
            p.setStockQuantity(10);
        }

        cartLabel.setText("Current Cart ( $" + model.getRevenue() +"):" );

        //update(model);


    }

    public ElectronicStoreView() {

        // Create the labels, text fields

        Label labelTitle = new Label("Store Summary:");
        labelTitle.relocate(55,15);

        Label label1 = new Label("#Sales: ");
        label1.relocate(50, 45);
        //TextField salesTF = new TextField();
        salesTF.relocate(100,40);

        salesTF.setPrefSize(90,10);

        Label label2 = new Label("Revenue: ");
        label2.relocate(45, 85);
        revTF.relocate(100,80);

        revTF.setPrefSize(90,10);

        Label label3 = new Label("$ / Sale: ");
        label3.relocate(50, 125);

        dollarsTF.relocate(100,120);

        dollarsTF.setPrefSize(90,10);

        // Create the lists and labels
        Label mostPLabel = new Label("Most Popular Items: ");
        mostPLabel.relocate(50,160);

        mostPopularVList = new ListView<Product>();
        mostPopularVList.relocate(10, 180);
        mostPopularVList.setPrefSize(200,150);

        Label stockLabel = new Label("Store Stock:");
        stockLabel.relocate(320,15);

        stockVList = new ListView<Product>();
        stockVList.relocate(230, 40);
        stockVList.setPrefSize(270,290);

        cartLabel = new Label("Current Cart:");
        cartLabel.relocate(600,15);

        cartVList = new ListView<String>();
        cartVList.relocate(510, 40);
        cartVList.setPrefSize(270,290);

        // Create the button pane
        buttonPane = new ButtonPane();
        buttonPane.relocate(-20, 340);
        buttonPane.setPrefSize(800,50);

        // Add all the components to the Pane
        getChildren().addAll(label1, label2, label3, mostPLabel,salesTF,dollarsTF,revTF, stockLabel, cartLabel ,mostPopularVList, stockVList, cartVList, buttonPane, labelTitle);

        setPrefSize(800, 400);
    }
}






