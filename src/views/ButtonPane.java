
package views;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ButtonPane extends Pane {
    private Button  resetButton, addButton, removeButton, completeButton;

    public Button getResetButton() { return resetButton; }
    public Button getAddButton() { return addButton; }
    public Button getRemoveButton() { return removeButton; }
    public Button getCompleteButton() { return completeButton; }


    public ButtonPane() {
        Pane innerPane = new Pane();

        // Create the buttons
        resetButton = new Button("Reset Store");
        resetButton.setStyle("-fx-font: 12 arial; ");
        resetButton.relocate(60, 0);
        resetButton.setPrefSize(130,45);

        resetButton.setDisable(false);

        addButton = new Button("Add to Cart");
        addButton.setStyle("-fx-font: 12 arial; ");
        addButton.relocate(320, 0);
        addButton.setPrefSize(130,45);

        addButton.setDisable(true);

        removeButton = new Button("Remove from Cart");
        removeButton.setStyle("-fx-font: 12 arial;");
        removeButton.relocate(530, 0);
        removeButton.setPrefSize(140,45);

        removeButton.setDisable(true);

        completeButton = new Button("Complete Sale");
        completeButton.setStyle("-fx-font: 12 arial;");
        completeButton.relocate(670, 0);
        completeButton.setPrefSize(130,45);

        completeButton.setDisable(true);


        // Add all three buttons to the pane
        innerPane.getChildren().addAll(resetButton, addButton, removeButton, completeButton);

        getChildren().addAll(innerPane);//, titleLabel);
    }
}

