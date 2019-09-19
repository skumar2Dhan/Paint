/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paint1;

import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class Paint1Test {
    
    public Paint1Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class Paint1.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        Stage primaryStage = null;
        Paint1 instance = new Paint1();
        instance.start(primaryStage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Paint1.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Paint1.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fileChooserExtensions method, of class Paint1.
     */
    @Test
    public void testFileChooserExtensions() {
        System.out.println("fileChooserExtensions");
        FileChooser fileChooser = null;
        Paint1 instance = new Paint1();
        instance.fileChooserExtensions(fileChooser);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveAsOption method, of class Paint1.
     */
    @Test
    public void testSaveAsOption() {
        System.out.println("saveAsOption");
        Stage primaryStage = null;
        Paint1 instance = new Paint1();
        instance.saveAsOption(primaryStage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveOption method, of class Paint1.
     */
    @Test
    public void testSaveOption() {
        System.out.println("saveOption");
        Paint1 instance = new Paint1();
        instance.saveOption();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of undo method, of class Paint1.
     */
    @Test
    public void testUndo() {
        System.out.println("undo");
        Canvas canvas = null;
        GraphicsContext gc = null;
        Stack<Image> undoStack = null;
        Stack<Image> redoStack = null;
        Paint1.undo(canvas, gc, undoStack, redoStack);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of redo method, of class Paint1.
     */
    @Test
    public void testRedo() {
        System.out.println("redo");
        Canvas canvas = null;
        GraphicsContext gc = null;
        Stack<Image> undoStack = null;
        Stack<Image> redoStack = null;
        Paint1.redo(canvas, gc, undoStack, redoStack);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of snapshot method, of class Paint1.
     */
    @Test
    public void testSnapshot() {
        System.out.println("snapshot");
        Canvas canvas = null;
        Stack<Image> stack = null;
        Paint1 instance = new Paint1();
        instance.snapshot(canvas, stack);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
