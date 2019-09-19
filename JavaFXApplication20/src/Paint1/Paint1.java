package Paint1;
//All imports
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Paint1 extends Application {
    //Declaring all the variables.
    WhiteCanvas whiteCanvas;
    File file;
    double x;
    double y;
    double radius;
    double xDifference;
    double yDifference;
    double y2;
    double x2;
    double x4;
    double y4;
    double xSelectInitial;
    double ySelectInitial;
    double xSelectFinal;
    double ySelectFinal;
    double xDiff;
    double yDiff;
    Image snapshotSelect;
    double xLine;
    double yLine;
    int i;

    @Override
    public void start(Stage primaryStage) {
    //Creates new canvas and assigns it to the variable whiteCanvas. 
        whiteCanvas = new WhiteCanvas(450, 400);
    //creating Vbox,Scene,ColorPicker,ComboBox,TextField 
        VBox root = new VBox();
        Scene scene = new Scene(root, 675, 550);
        ColorPicker ColorPicker = new ColorPicker();
        ComboBox<String> selectBox = new ComboBox();
        TextField textBox = new TextField();
        selectBox.setPromptText("Edit Tools");
        //All the boxes labelled
        selectBox.getItems().addAll("Dropper", "TextBox", "Eraser", "Select", "Drag", "Line", "Pencil", "Circle", "Solid Circle", "Rectangle", "Solid Rectangle", "Triangle");
        /*Creating a new MenuBar object, where Menu objects are added. */
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
 
        //Created New Menu objects.
        Menu fileMenu = new Menu("File");
        fileMenu.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));//creating a shortcut key for a File Menu
        Menu editMenu = new Menu("Edit");
        editMenu.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));//creating a shortcut key for an Edit menu
        //Created new MenuItem objects, named "Open", "Save", "Save As", "Exit", "Undo",and "Redo"
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));//creating a shortcut key for an image
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));//creating a shortcut key for Save 
        MenuItem saveAsMenuItem = new MenuItem("Save As");
        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));//creating a shortcut key for Save As 
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));//creating a shortcut key for an Exit 

        MenuItem undoMenuItem = new MenuItem("Undo");
        undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));//creating a shortcut key for UNDO 
        MenuItem redoMenuItem = new MenuItem("Redo");
        redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));//creating a shortcut key for REDO 
        
        //Creating stacks for undo, redo, and shapeVisibleDragStack
        Stack<Image> undoStack = new Stack();
        Stack<Image> shapeVisibleDragStack = new Stack();
        Stack<Image> redoStack = new Stack();

        //The exitMenuItem is being set to exit the program.
        exitMenuItem.setOnAction((ActionEvent event) -> {
            Stage Quit = new Stage();
            StackPane exitBox = new StackPane();
            Scene quitScene = new Scene(exitBox, 300, 150);
            Quit.setTitle("Paint");

            Label quitLabel = new Label("Do you want to save changes?");
            Button yes = new Button("Yes");
            Button no = new Button("No");
            
            //height and width of 'Yes' Button
            yes.setMaxHeight(30);
            yes.setMaxWidth(80);
            yes.setTranslateX(-50);
            yes.setTranslateY(25);

            //height and width of 'No' Button
            no.setMaxHeight(30);
            no.setMaxWidth(80);
            no.setTranslateX(50);
            no.setTranslateY(25);

            
            quitLabel.setTranslateY(-30);
            exitBox.getChildren().addAll(yes, no, quitLabel);
            Quit.setScene(quitScene);
            Quit.show();

            yes.setOnAction((ActionEvent event1) -> {
                saveAsOption(primaryStage);
                Platform.exit();
            });
            no.setOnAction((ActionEvent actionEvent) -> {
                Platform.exit();
            });

        });

        /*Created an action for the openMenuItem. It gives you a file chooser
        to open the file, and then displays the choosen file on the imageView. */
        openMenuItem.setOnAction((ActionEvent event) -> {
            //Created a FileChooser object.
            snapshot(whiteCanvas.canvas, undoStack);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooserExtensions(fileChooser);
            file = fileChooser.showOpenDialog(primaryStage);

            Image image = new Image(file.toURI().toString());
            whiteCanvas.canvas.setWidth(image.getWidth());
            whiteCanvas.canvas.setHeight(image.getHeight());

            //imageView set to the chosen image.
            whiteCanvas.gc.drawImage(image, 0, 0, 450, 400);
        });
        //Save as option--action
        saveAsMenuItem.setOnAction((ActionEvent event) -> {
            saveAsOption(primaryStage);
        });
        //Creating a slider to change the width. Min value set to 1 and Max value set to 100
        Slider slider = new Slider();
        Label label = new Label("1.0");
        slider.setMin(1);
        slider.setMax(100);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        
        //The Drop down menu items are set to their respective functionalities.
        selectBox.setOnAction((ActionEvent event) -> {
            //This is the Dropper tool which enables the user to pick any color on the picture.
            if ("Dropper".equals(selectBox.getValue())) {
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                });

                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                });
                //Gets the x and y coordinates of the first mouse-clicked and released and sets the ColorPicker value to the color being clicked on Picture.
                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    int xInitial = (int) e.getX();
                    int yInitial = (int) e.getY();
                    WritableImage im = whiteCanvas.canvas.snapshot(null, null);
                    Color colorSelect = im.getPixelReader().getColor(xInitial, yInitial);
                    whiteCanvas.gc.setFill(colorSelect);
                    whiteCanvas.gc.setStroke(colorSelect);
                    ColorPicker.setValue(colorSelect);

                });
            }
            //Text Box tool, helps user to put text on picture 
            if ("TextBox".equals(selectBox.getValue())) {
                //Gets the x and y coordinates of the first mouse-click
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    snapshot(whiteCanvas.canvas, undoStack);
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    xSelectInitial = e.getX();
                    ySelectInitial = e.getY();

                    double xFinal = e.getX();
                    if (xFinal < xSelectInitial) {
                        double xTemp = xSelectInitial;
                        xSelectInitial = xFinal;
                        xFinal = xTemp;
                    }
                    xDiff = xFinal - xSelectInitial;
                    String xText = textBox.getText();

                    whiteCanvas.gc.strokeText(xText, xSelectInitial, ySelectInitial, 900);
                    whiteCanvas.gc.setFont(Font.font(xText, FontWeight.SEMI_BOLD, 70));

                });

                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    

                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    shapeVisibleDragStack.clear();
                });
            }
            //Creates Eraser
            if ("Eraser".equals(selectBox.getValue())) {
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    whiteCanvas.gc.setStroke(Color.WHITE);
                    whiteCanvas.gc.beginPath();
                    snapshot(whiteCanvas.canvas, undoStack);
                });
                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.lineTo(e.getX(), e.getY());
                    whiteCanvas.gc.stroke();
                    whiteCanvas.gc.setLineWidth(slider.getValue());

                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    whiteCanvas.gc.closePath();
                    shapeVisibleDragStack.clear();
                });
            }
            //This enables user to select a part of the image/blank canvas
            if ("Select".equals(selectBox.getValue())) {
                //Gets the x and y coordinates of the first mouse-click and starts the line
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    xSelectInitial = e.getX();
                    ySelectInitial = e.getY();
                    snapshot(whiteCanvas.canvas, undoStack);
                });

                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {

                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    xSelectFinal = e.getX();
                    ySelectFinal = e.getY();
                    whiteCanvas.gc.setLineWidth(1);

                    whiteCanvas.gc.strokeLine(xSelectInitial, ySelectInitial, xSelectInitial, ySelectFinal);
                    whiteCanvas.gc.strokeLine(xSelectInitial, ySelectInitial, xSelectFinal, ySelectInitial);
                    whiteCanvas.gc.strokeLine(xSelectFinal, ySelectInitial, xSelectFinal, ySelectFinal);
                    whiteCanvas.gc.strokeLine(xSelectInitial, ySelectFinal, xSelectFinal, ySelectFinal);

                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    xDifference = e.getX();
                    yDifference = e.getY();
                    whiteCanvas.gc.setLineWidth(1);
                    whiteCanvas.gc.strokeLine(xSelectInitial, ySelectInitial, xSelectInitial, yDifference);
                    whiteCanvas.gc.strokeLine(xSelectInitial, ySelectInitial, xDifference, ySelectInitial);
                    whiteCanvas.gc.strokeLine(xDifference, ySelectInitial, xDifference, yDifference);
                    whiteCanvas.gc.strokeLine(xSelectInitial, yDifference, xDifference, yDifference);
                    shapeVisibleDragStack.clear();
                });
            }
            //This helps to cut and drag the selected part of the image/blank canvas.
            if ("Drag".equals(selectBox.getValue())) {
                snapshotSelect = whiteCanvas.canvas.snapshot(null, null);

                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    snapshot(whiteCanvas.canvas, undoStack);

                });

                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    whiteCanvas.gc.setFill(Color.WHITE);
                    whiteCanvas.gc.fillRect(xSelectInitial, ySelectInitial, xDifference, yDifference);
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    whiteCanvas.gc.drawImage(snapshotSelect, xSelectInitial, ySelectInitial, xDifference, yDifference, e.getX(), e.getY(), xDifference, yDifference);
                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    whiteCanvas.gc.setFill(Color.WHITE);
                    whiteCanvas.gc.fillRect(xSelectInitial, ySelectInitial, xDifference, yDifference);
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    whiteCanvas.gc.drawImage(snapshotSelect, xSelectInitial, ySelectInitial, xDifference, yDifference, e.getX(), e.getY(), xDifference, yDifference);
                    shapeVisibleDragStack.clear();
                });
            }
            //Creates a straight line on the image/blank canvas by dragging the mouse.
            if ("Line".equals(selectBox.getValue())) {
                //Gets the x and y coordinates of the first mouse-click and starts the line
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    xLine = e.getX();
                    yLine = e.getY();
                    whiteCanvas.gc.lineTo(xLine, yLine);
                    snapshot(whiteCanvas.canvas, undoStack);
                });

                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    whiteCanvas.gc.beginPath();
                    whiteCanvas.gc.lineTo(xLine, yLine);
                    whiteCanvas.gc.getStroke();
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.lineTo(e.getX(), e.getY());
                    whiteCanvas.gc.stroke();

                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.closePath();
                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    whiteCanvas.gc.lineTo(e.getX(), e.getY());
                    whiteCanvas.gc.stroke();

                    whiteCanvas.gc.setLineWidth(slider.getValue());

                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                });
            }
            //Draws a circle on the image/blank canvas by dragging the mouse.
            if ("Circle".equals(selectBox.getValue())) {
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    x = e.getX();
                    y = e.getY();
                    snapshot(whiteCanvas.canvas, undoStack);
                });
                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    radius = Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY()));
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    radius = Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY()));
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
                    shapeVisibleDragStack.clear();
                });
            }
            //Draws a filled circle on the image/blank canvas
            if ("Solid Circle".equals(selectBox.getValue())) {
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    x = e.getX();
                    y = e.getY();
                    snapshot(whiteCanvas.canvas, undoStack);
                });
                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    radius = Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY()));
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    radius = Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY()));
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                    shapeVisibleDragStack.clear();
                });
            }
            //Allows user to draw anything on the picture/blank canvas
            if ("Pencil".equals(selectBox.getValue())) {

                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    whiteCanvas.gc.beginPath();
                    whiteCanvas.gc.lineTo(e.getX(), e.getY());
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    snapshot(whiteCanvas.canvas, undoStack);
                });
                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    whiteCanvas.gc.stroke();
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.lineTo(e.getX(), e.getY());
                    whiteCanvas.gc.setLineWidth(slider.getValue());

                });
                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    whiteCanvas.gc.lineTo(e.getX(), e.getY());
                    whiteCanvas.gc.stroke();
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.closePath();
                    shapeVisibleDragStack.clear();
                });

            }
            //Draws Rectangle on the image/blank canvas.
            if ("Rectangle".equals(selectBox.getValue())) {
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    x = e.getX();
                    y = e.getY();
                    snapshot(whiteCanvas.canvas, undoStack);
                });
                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    x2 = e.getX();
                    y2 = e.getY();
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.strokeLine(x, y, x, y2);
                    whiteCanvas.gc.strokeLine(x, y, x2, y);
                    whiteCanvas.gc.strokeLine(x2, y, x2, y2);
                    whiteCanvas.gc.strokeLine(x, y2, x2, y2);
                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    x2 = e.getX();
                    y2 = e.getY();
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.strokeLine(x, y, x, y2);
                    whiteCanvas.gc.strokeLine(x, y, x2, y);
                    whiteCanvas.gc.strokeLine(x2, y, x2, y2);
                    whiteCanvas.gc.strokeLine(x, y2, x2, y2);
                    shapeVisibleDragStack.clear();

                });
            }
            //Draws the Filled Rectangle on the image/blank canvas
            if ("Solid Rectangle".equals(selectBox.getValue())) {
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    x = e.getX();
                    y = e.getY();
                    snapshot(whiteCanvas.canvas, undoStack);

                });
                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    x2 = e.getX();
                    y2 = e.getY();
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.setLineWidth(slider.getValue());
                    whiteCanvas.gc.strokeLine(x, y, x, y2);
                    whiteCanvas.gc.strokeLine(x, y, x2, y);
                    whiteCanvas.gc.strokeLine(x2, y, x2, y2);
                    whiteCanvas.gc.strokeLine(x, y2, x2, y2);
                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    double x2 = e.getX();
                    double y2 = e.getY();
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.setLineWidth((double) slider.getValue());
                    whiteCanvas.gc.strokeLine(x, y, x, y2);
                    whiteCanvas.gc.strokeLine(x, y, x2, y);
                    whiteCanvas.gc.strokeLine(x2, y, x2, y2);
                    whiteCanvas.gc.strokeLine(x, y2, x2, y2);
                    shapeVisibleDragStack.clear();

                });
            }
            //Draws a Triangle on the image/blank canvas 
            if ("Triangle".equals(selectBox.getValue())) {
                whiteCanvas.canvas.setOnMousePressed((MouseEvent e) -> {
                    x = e.getX();
                    y = e.getY();
                    snapshot(whiteCanvas.canvas, undoStack);
                });
                whiteCanvas.canvas.setOnMouseDragged((MouseEvent e) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, shapeVisibleDragStack, redoStack);
                    snapshot(whiteCanvas.canvas, shapeVisibleDragStack);
                    x2 = e.getX();
                    y2 = e.getY();
                    whiteCanvas.gc.setStroke(ColorPicker.getValue());
                    whiteCanvas.gc.setLineWidth(slider.getValue());

                    whiteCanvas.gc.strokeLine(x, x, y, y2);
                    whiteCanvas.gc.strokeLine(y, y2, (x + x2) / 2, x);
                    whiteCanvas.gc.strokeLine((x2 + x) / 2, x, x, x);

                });

                whiteCanvas.canvas.setOnMouseReleased((MouseEvent e) -> {
                    x2 = e.getX();
                    y2 = e.getY();

                    shapeVisibleDragStack.clear();

                });

            }

        }
        );
        //Undo button Action
        undoMenuItem.setOnAction(
                (ActionEvent event) -> {
                    undo(whiteCanvas.canvas, whiteCanvas.gc, undoStack, redoStack);
                }
        );
        //Redo button action
        redoMenuItem.setOnAction(
                (ActionEvent event) -> {
                    redo(whiteCanvas.canvas, whiteCanvas.gc, undoStack, redoStack);
                }
        );

        //Created a save button action.
        saveMenuItem.setOnAction(
                (ActionEvent event) -> {
                    saveOption();
                }
        );

        /*Adds the menuItems to the fileMenu created before. Also adds a
        seperator. */
        fileMenu.getItems()
                .addAll(openMenuItem, saveMenuItem, saveAsMenuItem,
                        new SeparatorMenuItem(), exitMenuItem);
        
        editMenu.getItems()
                .addAll(undoMenuItem, redoMenuItem);

        //Adds the fileMenu and editMenu to the the menu bar.
        menuBar.getMenus()
                .addAll(fileMenu, editMenu);

        //Creates a new ToolBar object.
        ToolBar toolBar = new ToolBar(ColorPicker, selectBox, slider, textBox);

        /*Adds the menuBar and the toolBar to the frame. menuBar at top, and
          toolBar at the bottom. */
        root.getChildren()
                .addAll(menuBar, toolBar, whiteCanvas.canvas);
        

        primaryStage.setTitle(
                "Image Editor");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void fileChooserExtensions(FileChooser fileChooser) {
        // Created extension filters.
        FileChooser.ExtensionFilter j_extFilter = new FileChooser.ExtensionFilter("JPG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter p_extFilter = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
        FileChooser.ExtensionFilter t_extFilter = new FileChooser.ExtensionFilter("TIF (*.tif;*.tiff)", "*.tif;*.tiff");
        FileChooser.ExtensionFilter g_extFilter = new FileChooser.ExtensionFilter("GIF (*.gif)", "*.gif");
        FileChooser.ExtensionFilter b_extFilter = new FileChooser.ExtensionFilter("Monochrome Bitmap (*.bmp;*.dib)", "*.bmp;*.dib");
        FileChooser.ExtensionFilter b1_extFilter = new FileChooser.ExtensionFilter("16 Color Bitmap (*.bmp;*.dib)", "*.bmp;*.dib");
        FileChooser.ExtensionFilter b2_extFilter = new FileChooser.ExtensionFilter("256 Color Bitmap (*.bmp;*.dib)", "*.bmp;*.dib");
        FileChooser.ExtensionFilter b3_extFilter = new FileChooser.ExtensionFilter("24-bit Bitmap (*.bmp;*.dib)", "*.bmp;*.dib");
        FileChooser.ExtensionFilter allFilesFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
        // Added the file extensions to the file chooser
        fileChooser.getExtensionFilters().addAll(j_extFilter, p_extFilter, t_extFilter, g_extFilter, b_extFilter, b1_extFilter, b2_extFilter, b3_extFilter, allFilesFilter);
    }
    //Save As
    public void saveAsOption(Stage primaryStage) {
        WritableImage im = whiteCanvas.canvas.snapshot(new SnapshotParameters(), null);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooserExtensions(fileChooser);
        file = fileChooser.showSaveDialog(primaryStage);

        BufferedImage bImage = SwingFXUtils.fromFXImage(im, null);
        try {
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Save
    public void saveOption() {
        //Creates a writable image (image that's supposed to be saved)
        WritableImage im = whiteCanvas.canvas.snapshot(null, null);

        /*Assigns the "opened" image file to the outputFile to overwrite
            image file. */
        File outputFile = file;
        BufferedImage bImage = SwingFXUtils.fromFXImage(im, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Undo
    public static void undo(Canvas canvas, GraphicsContext gc, Stack<Image> undoStack, Stack<Image> redoStack) {
        Image image = canvas.snapshot(new SnapshotParameters(), null);
        redoStack.push(image);
        if (!undoStack.empty()) {
            gc.drawImage(undoStack.pop(), 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        }
    }
    //Redo
    public static void redo(Canvas canvas, GraphicsContext gc, Stack<Image> undoStack, Stack<Image> redoStack) {
        Image image = canvas.snapshot(new SnapshotParameters(), null);
        undoStack.push(image);
        if (!redoStack.empty()) {
            gc.drawImage(redoStack.pop(), 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        }
    }
    //Snapshot
    public void snapshot(Canvas canvas, Stack<Image> stack) {
        Image image = canvas.snapshot(new SnapshotParameters(), null);
        stack.push(image);
    }

}
