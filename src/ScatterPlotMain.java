import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScatterPlotMain extends Application {
	private ScatterPlotCanvas scatterPlotCanvas;
	private MenuItem[] viewMI;
	private MenuItem[] loadMI;
	private MenuItem[] detailsMI;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		// Canvas
		scatterPlotCanvas = new ScatterPlotCanvas();
		scatterPlotCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> mouseEventHandler(event));
		scatterPlotCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> mouseEventHandler(event));
		
		// Menu
		MenuBar menuBar = new MenuBar();
		
		Menu viewM = new Menu("View");
		Menu loadM = new Menu("Load");
		Menu detailsM = new Menu("Details");
		
		String[] viewS = {"2D", "3D", "Vertexes", "Edges", "Faces", "AxisV", "AxisE"};
		String[] loadS = {"2D", "3D"};
		String[] detailsS = {"Animation Start", "Animation Stop", "Size +", "Size -"};
		
		viewMI = new MenuItem[viewS.length];
		loadMI = new MenuItem[loadS.length];
		detailsMI = new MenuItem[detailsS.length];
		
		for (int i = 0; i < viewS.length; i++) {
			viewMI[i] = new MenuItem(viewS[i]);
			viewMI[i].setOnAction(event -> changeView(event));
		}
		for (int i = 0; i < loadS.length; i++) {
			loadMI[i] = new MenuItem(loadS[i]);
			loadMI[i].setOnAction(event -> loadData(event));
		}
		for (int i = 0; i < detailsS.length; i++) {
			detailsMI[i] = new MenuItem(detailsS[i]);
			detailsMI[i].setOnAction(event -> details(event));
		}
		
		viewM.getItems().addAll(viewMI);
		loadM.getItems().addAll(loadMI);
		detailsM.getItems().addAll(detailsMI);
		
		menuBar.getMenus().addAll(viewM, loadM, detailsM);
		
		// Pane
		BorderPane bp = new BorderPane();
		Pane pane = new Pane();
		
		bp.setTop(menuBar);
		bp.setCenter(pane);
		
		pane.getChildren().add(scatterPlotCanvas);
		scatterPlotCanvas.heightProperty().bind(pane.heightProperty());
		scatterPlotCanvas.widthProperty().bind(pane.widthProperty());
		
		// Scene
		Scene scene = new Scene(bp);
		scene.setOnKeyPressed(event -> keyEventHandler(event));
		
		// Stage
		primaryStage.setTitle("Scatter Plot");
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void changeView(Event event) {
		for (int i = 0; i < viewMI.length; i++)
			if ((MenuItem)event.getTarget() == viewMI[i])
				scatterPlotCanvas.changeView(i);
	}
	
	private void loadData(Event event) {
		for (int i = 0; i < loadMI.length; i++)
			if ((MenuItem)event.getTarget() == loadMI[i])
				scatterPlotCanvas.loadData(i);
	}
	
	private void details(Event event) {
		for (int i = 0; i < detailsMI.length; i++)
			if ((MenuItem)event.getTarget() == detailsMI[i])
				scatterPlotCanvas.details(i);
	}
	
	private void keyEventHandler(Event event) {
		KeyEvent e = (KeyEvent)event;
		
		if (e.getCode() == KeyCode.LEFT) scatterPlotCanvas.calc(-0.05, 0, 0);
		else if (e.getCode() == KeyCode.RIGHT) scatterPlotCanvas.calc(0.05, 0, 0);
		else if (e.getCode() == KeyCode.UP) scatterPlotCanvas.calc(0, -0.05, 0);
		else if (e.getCode() == KeyCode.DOWN) scatterPlotCanvas.calc(0, 0.05, 0);
		else if (e.getCode() == KeyCode.COMMA) scatterPlotCanvas.calc(0, 0, -0.05);
		else if (e.getCode() == KeyCode.PERIOD) scatterPlotCanvas.calc(0, 0, 0.05);
		else if (e.getCode() == KeyCode.SPACE) scatterPlotCanvas.animation();
		else if (e.getCode() == KeyCode.L) scatterPlotCanvas.loadData(0);
		else if (e.getCode() == KeyCode.A) scatterPlotCanvas.axisView();
		else if (e.getCode() == KeyCode.DIGIT2) scatterPlotCanvas.changeView(0);
		else if (e.getCode() == KeyCode.DIGIT3) scatterPlotCanvas.changeView(1);
		else if (e.getCode() == KeyCode.V) scatterPlotCanvas.changeView(2);
		else if (e.getCode() == KeyCode.E) scatterPlotCanvas.changeView(3);
		else if (e.getCode() == KeyCode.F) scatterPlotCanvas.changeView(4);
	}
	
	private void mouseEventHandler(Event event) {
		MouseEvent e = (MouseEvent)event;
		
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED)
			scatterPlotCanvas.mousePressed(e.getX(), e.getY());
		else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED)
			scatterPlotCanvas.mouseDragged(e.getX(), e.getY());
	}
}