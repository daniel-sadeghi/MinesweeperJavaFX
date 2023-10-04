module com.dsadeghi.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dsadeghi.minesweeper to javafx.fxml;
    exports com.dsadeghi.minesweeper;
}