/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.manageitems.ui;

import de.knoplab.manageitems.DefaultItem;
import de.knoplab.manageitems.Item;
import de.knoplab.manageitems.ItemWrapper;
import de.knoplab.manageitems.ListPossibleValues;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author tuananh
 */
public class WidgetUI extends BorderPane implements Initializable, ListPossibleValues {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField textField;
    @FXML
    private Button moreButton;
    @FXML
    private ListView<Item> listView;
    private List<Item> listValues;
    private ObservableList<Item> observableList;
    private boolean bigger = false;

    public WidgetUI() throws IOException {
        listValues = new ArrayList<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Widget.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        observableList = FXCollections.observableArrayList(listValues.subList(0, 5));
        listView.setCellFactory((ListView<Item> l) -> new ListCellcheckbox());
        listView.setItems(observableList);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textField.onKeyPressedProperty();
        List<String> t = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            t.add(generateString(new Random(), "aegnfdjlkghsljkghflgjfsgsdgfgfgs", 10));
        }
        setPossibleValues(t);
        this.setPrefSize(listView.getPrefWidth(), listView.getPrefHeight() + 100);

    }

    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    @Override
    public void setPossibleValues(List<String> list) {
        Map<String, Integer> items;
        items = new HashMap<>();
        list.stream().map((s) -> {
            if (items.get(s) == null) {
                items.put(s, 1);
            } else {
                items.put(s, items.get(s) + 1);
            }
            return s;
        }).forEach((s) -> {
            System.out.println(s + items.get(s));
        });
        items.forEach((s, i) -> listValues.add(new ItemWrapper(s, i)));

    }

    public void filterQuery() {
        String s = textField.getText();
        List<Item> listValuesTmp = new ArrayList<>();
        if (bigger) {
            listValues.stream().filter(e -> e.getName().contains(s)).forEach(e -> listValuesTmp.add(e));
        } else {
            listValues.subList(0, 5).stream().filter(e -> e.getName().contains(s)).forEach(e -> listValuesTmp.add(e));

        }
        observableList = FXCollections.observableArrayList(listValuesTmp);
        listView.setItems(observableList);

    }

    public void setBigger() {
        if (!bigger) {
            bigger = true;
            filterQuery();

        }
    }

    public Predicate<String> getPredicate() {
        List<String> listBuffer = new ArrayList<>();
        listValues.stream().filter(e -> e.getState()).forEach(e -> listBuffer.add(e.getName()));
        return p -> listBuffer.contains(p);
    }
}
