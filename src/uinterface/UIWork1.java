package uinterface;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorAdjustBuilder;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.CalcReliability;

/**
 * FX UI demo for habrahabr.ru article
 *
 * @author Sergey Grinev
 */


public class UIWork1 extends Application {

    private final String TEXT_PRESS_BTN = "Рассчёт произведён!";
    private final String TEXT_ERROR = "Введены не все данные!";
    private final String TEXT_ERROR_CORRECTNESS = "Введенные данные\nнекорректны!";
    
    //Текстовые поля для ввода данных
    private TextField numberListTextField;
    private TextField dayTextField;
    private TextField monthTextField;
    private TextField yearTextField;
    
    //Текстовые поля для отображения результа рассчёта
    private TextField tTextField;
    private TextField t2TextField;
    private TextField lFromTTextField;
    private TextField lFromNullTextField;
    private TextField fTextField;
    private TextField ptTextField;
    
    private CalcReliability obj = null;
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    private HBox taskbar;

    @Override
    public void start(Stage stage) {
        
        stage.setTitle("Рассчётное задание №1");
        stage.setFullScreen(true);

        // здесь мы создаём сцену, которая является содержимым окна и layout manager для неё
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 720, 650, Color.LIGHTGRAY);
        stage.setScene(scene);
        //scene.getStylesheets().add(FXUIDemo.class.getResource("style.css").toExternalForm());
        
        // создадим отдельный layout для иконок-кнопок -- horizontal box
        taskbar = new HBox(3);
        taskbar.setAlignment(Pos.CENTER);
        taskbar.setPadding(new Insets(10, 30, 50, 30));
        taskbar.setPrefHeight(150);
        taskbar.setStyle("-fx-background-color: linear (0%,0%) to (100%,100%) stops (0.0,black) (1.0,red);");
        
        view = new StackPane();
        view.getChildren().add(new Text("Рассчётное задание №1"));

        root.setCenter(view);
        root.setBottom(taskbar);

        //Кнопки и окна
        taskbar.getChildren().add(createButton("icon-2.png", new Runnable() {

            public void run() {
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);//пробелы между строками сетки
                grid.setVgap(10);//пробелы между столбцами сетки
                grid.setPadding(new Insets(5, 5, 5, 5));//пространство вокруг сетки
                String strText = null;

                Label numberList = new Label("Номер в списке: ");
                grid.add(numberList, 0, 1);

                numberListTextField = new TextField();
                grid.add(numberListTextField, 1, 1);
                
                Label day = new Label("День");
                grid.add(day, 0, 2);

                dayTextField = new TextField();
                grid.add(dayTextField, 0, 3);
                
                Label month = new Label("Месяц");
                grid.add(month, 1, 2);

                monthTextField = new TextField();
                grid.add(monthTextField, 1, 3);

                Label year = new Label("Год");
                grid.add(year, 2, 2);

                yearTextField = new TextField();
                grid.add(yearTextField, 2, 3);

                Button btn = new Button("Рассчёт");
                HBox hbBtn = new HBox(10);
                hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
                hbBtn.getChildren().add(btn);
                grid.add(btn, 1, 5);

                final  Text actionTarget = new Text();
                grid.add(actionTarget, 1, 6);
                
                btn.setOnAction(new EventHandler<ActionEvent>() {

                    
                    @Override
                    public void handle(ActionEvent t) {                  
                        
                        if (checkOfValues()) {
                            int numberInGroup = Integer.valueOf(numberListTextField.getText());
                            int dayBirthday = Integer.valueOf(dayTextField.getText());
                            int monthBirthday = Integer.valueOf(monthTextField.getText());
                            int yearBirthday = Integer.valueOf(yearTextField.getText());
                           
                            if (checkValuesCorrectness(numberInGroup, dayBirthday,
                                    monthBirthday, yearBirthday)) {
                                
                                actionTarget.setText(TEXT_PRESS_BTN);
                                
                                obj = new CalcReliability(numberInGroup, dayBirthday,
                                    monthBirthday, yearBirthday);
                                
                                tTextField.setText(String.valueOf(obj.getT())); 
                                t2TextField.setText(String.valueOf(obj.getT2()));
                                lFromNullTextField.setText(String.valueOf(obj.getLambdaFromNull()));
                                lFromTTextField.setText(String.valueOf(obj.getLambdaFromT()));
                                fTextField.setText(String.valueOf(obj.getFFromT()));
                                ptTextField.setText(String.valueOf(obj.getPFromT()));                               
                            } else {
                                actionTarget.setText(TEXT_ERROR_CORRECTNESS);
                            }     
                        } else {
                            actionTarget.setText(TEXT_ERROR);
                        }
                    }
                });
                
                Label text = new Label("Числовой результат:");
                grid.add(text, 0, 7);
                
                Label t = new Label("t*:");
                grid.add(t, 0, 8);
                
                tTextField = new TextField();
                grid.add(tTextField, 0, 9);
                
                Label lFromNull = new Label("l(0):");
                grid.add(lFromNull, 1, 8);
                
                lFromNullTextField = new TextField();
                grid.add(lFromNullTextField, 1, 9);
                
                Label lFromT = new Label("l(t):");
                grid.add(lFromT, 2, 8);
                
                lFromTTextField = new TextField();
                grid.add(lFromTTextField, 2, 9);
                
                Label T2 = new Label("t**:");
                grid.add(T2, 0, 10);
                    
                t2TextField = new TextField();
                grid.add(t2TextField, 0, 11);
                
                Label fT = new Label("F(t):");
                grid.add(fT, 1, 10);
                
                fTextField = new TextField();
                grid.add(fTextField, 1, 11);
                
                Label pT = new Label("P(t*):");
                grid.add(pT, 2, 10);
                
                ptTextField = new TextField();
                grid.add(ptTextField, 2, 11);
                
                changeView(grid);
            }
        }));
        
        taskbar.getChildren().add(createButton("icon-1.png", new Runnable() {

            public void run() {
                NumberAxis xAxis = new NumberAxis();
                NumberAxis yAxis = new NumberAxis();

                LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);
                chart.setTitle("График результатов");
                xAxis.setLabel("Вероятность отказов (v)");
                yAxis.setLabel("Pv(t)");
                
                XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
                XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
                series.setName("t*");
                series2.setName("t**");
                
                if (obj != null) {
                    for (int i = 0; i < 11; i++) {
                        series.getData().add(new XYChart.Data<Number, Number>(i + 1, obj.getPvFromT1(i)));
                        series2.getData().add(new XYChart.Data<Number, Number>(i + 1, obj.getPvFromT2(i)));
                    }
                } 
                chart.getData().add(series);
                chart.getData().add(series2);
                
                changeView(chart);
            }
        }));

        stage.show();
    } 
    
    private boolean checkOfValues() {
        
        boolean flag = true;
        
        if (numberListTextField.getText().length() != 0 
                && dayTextField.getText().length() != 0 
                && monthTextField.getText().length() != 0 
                && yearTextField.getText().length() != 0) {
            flag = true;
        } else {
            flag = false;
        }
        
        return flag;
    }
    
    private boolean checkValuesCorrectness(int numberInGroup, int dayBirthday,
            int monthBirthday, int yearBirthday) {
        
        boolean flag = true;
        
        if (numberListTextField.getText().length() != 0 
                && dayTextField.getText().length() != 0 
                && monthTextField.getText().length() != 0 
                && yearTextField.getText().length() != 0 
                && numberInGroup > 0 && dayBirthday > 0 
                && monthBirthday > 0 && yearBirthday >= 90) {
            flag = true;
        } else {
            flag = false;
        }
        
        return flag;
    }
    
    private StackPane view;

    private void changeView(Node node) {
        view.getChildren().clear();
        view.getChildren().add(node);
    }
    
    private static final double SCALE = 1.1; 
    private static final double DURATION = 300; //миллисикунды для анимации

    private Node createButton(String iconName, final Runnable action) {
        final ImageView node = new ImageView(new Image(getClass().
                getResource(iconName).toString()));

        //Анимация кнопок (увеличение)
        final ScaleTransition animationGrow = new ScaleTransition(
                Duration.millis(DURATION), node);
        animationGrow.setToX(SCALE);
        animationGrow.setToY(SCALE);

        //Анимация кнопок (уменьшение)
        final ScaleTransition animationShrink = new ScaleTransition(
                Duration.millis(DURATION), node);
        animationShrink.setToX(1);
        animationShrink.setToY(1);
       
        final Reflection effect = new Reflection();
        node.setEffect(effect);

        //Затеменение кнопок
        final ColorAdjust effectPressed = ColorAdjustBuilder.create().brightness(-0.5).build();

        node.setOnMouseReleased(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                // second effect should be done over setInput()
                effect.setInput(effectPressed);
                // this timelie with remove darkening effect
                TimelineBuilder.create().keyFrames(new KeyFrame(
                        Duration.millis(DURATION), new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        effect.setInput(null);
                    }
                })).build().play();
                action.run();
            }
        });

        //Включение анимации увеличения кнопки,при наведение мышкой
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                animationShrink.stop();
                animationGrow.playFromStart();
            }
        });
        
        //Включение анимации уменьшения кнопки,при наведение мышкой
        node.setOnMouseExited(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                animationGrow.stop();
                animationShrink.playFromStart();
            }
        });

        return node;
    }
}
