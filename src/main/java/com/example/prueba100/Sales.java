package com.example.prueba100;

import DAO.ClientDAO;
import DAO.Product_orderDAO;
import connections.ConnectionMySQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Sales implements Initializable {
    @FXML
    private NumberAxis unitOrder;
    @FXML
    private CategoryAxis id_product;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private FlowPane main;

    @FXML
    private Label totalBenefits;

    @FXML
    private TextField totalCustomers;

    @FXML
    private Pane pane1;

    private ConnectionMySQL connection = new ConnectionMySQL();

    public void initialize(URL location, ResourceBundle resources) {
        // Get data from DAO
        Product_orderDAO productOrderDAO = new Product_orderDAO(connection);
        List<XYChart.Data<String, Integer>> chartData = productOrderDAO.getProductOrderChartDataWithNames();

        // Configure chart axes
        id_product.setLabel("Products name");
        unitOrder.setLabel("Number of units sold");

        // Configure chart data
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Most sold products");
        series.getData().addAll(chartData);

        // Add data to the chart
        barChart.getData().add(series);

        ConnectionMySQL conexionBD = new ConnectionMySQL();
        ClientDAO clientDAO = new ClientDAO(conexionBD);
        int totalClientes = clientDAO.getClientTotal();
        if (totalCustomers != null) {
            totalCustomers.setText(Integer.toString(totalClientes));
        }

        Label totalCustomersLabel = new Label("TOTAL CUSTOMERS");
        if (totalCustomers != null && totalCustomersLabel != null) {
            main.getChildren().addAll(totalCustomersLabel, totalCustomers);
            totalCustomers.setEditable(false);
        }
    }

    @FXML
    void gotoProduct(ActionEvent event) throws IOException {
        App.setRoot("Crud");
    }

    @FXML
    void gotoclient(ActionEvent event) throws IOException {
        App.setRoot("Orders");
    }

    @FXML
    void gotoshop(ActionEvent event) throws IOException {
        App.setRoot("Shop");
    }

    @FXML
    void gotosupplier(ActionEvent event) throws IOException {
        App.setRoot("CrudSupplier");
    }
}
