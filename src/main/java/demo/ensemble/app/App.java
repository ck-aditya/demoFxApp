package demo.ensemble.app;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.reflections.Reflections;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {

    private ObservableList<Class<? extends Application>> items;
    private ListView<Class<? extends Application>> listView;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        BorderPane root = new BorderPane();
        root.setCenter(createListView());
        this.primaryStage.setScene(new Scene(root, 500, 300));
        this.primaryStage.setTitle("OpenJFx Samples");
        this.primaryStage.show();
    }

    private Node createListView() {
        listView = new ListView<Class<? extends Application>>();
        items = FXCollections.observableArrayList();
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    Class<?> klass = listView.getSelectionModel().getSelectedItem();
                    try {
                        Object object = klass.getDeclaredConstructor(null).newInstance(null);
                        Parent node = (Parent) klass.getMethod("createContent").invoke(object);
                        Stage stage = new Stage();
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.initOwner(primaryStage);
                        stage.setScene(new Scene(node));
                        stage.show();
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                            | NoSuchMethodException | SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        setEnsembleAppClasses();
        return listView;
    }

    private void setEnsembleAppClasses() {
        Reflections reflections = new Reflections("ensemble.samples");
        Set<Class<? extends Application>> allClasses = reflections.getSubTypesOf(Application.class);
        items.addAll(allClasses);
        items.sort((Class<?> c1, Class<?> c2) -> (c1.getName().compareTo(c2.getName())));
    }

    public static void main(String[] args) {
        launch(args);
    }
}