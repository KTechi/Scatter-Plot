import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScatterPlotCanvas extends Canvas {
	private GraphicsContext gc = getGraphicsContext2D();
	private double h, w;
	private double currX, currY;
	private double prevX, prevY;
	private boolean showVertex = true;
	private boolean showEdge = true;
	private boolean showFace = true;
	private boolean showAxisV = true;
	private boolean showAxisE = true;
	private boolean autoSpin = false;
	
	private Color p = new Color(1, 1, 1, 1);
	private Color red = new Color(1, 0, 0, 1);
	private Color green = new Color(0, 1, 0, 1);
	private Color blue = new Color(0, 0, 1, 1);
	private Vertex[] vertex = {};
	private Edge[] edge = {};
	private Face[] face = {};
	
	// Axis
	private Vertex[] axisV = new Vertex[12];
	private Edge[] axisE = new Edge[3];
	
	// Animation
	private Timeline timeline;
	private int duration = 10;
	
	ScatterPlotCanvas() {
		heightProperty().addListener(event -> repaint());
		widthProperty().addListener(event -> repaint());
		
		timeline = new Timeline(
			new KeyFrame(
				new Duration(duration),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						calc(0.01, 0.005, 0);
						repaint();
					}
				}
			)
		);
		
		timeline.setCycleCount(Timeline.INDEFINITE);
		resetAxis();
		repaint();
	}
	
	private void repaint() {
		h = this.getHeight();
		w = this.getWidth();
		gc.setFill(new Color(0, 0, 0, 1));
		gc.fillRect(0, 0, w, h);
		
		// Axis
		if (showAxisE) for (int i = 0; i < axisE.length; i++) paintEdge(axisE[i]);
		if (showAxisV) for (int i = 0; i < axisV.length; i++) paintVertex(axisV[i]);
		
		if (showFace) for (int i = 0; i < face.length; i++) paintFace(face[i]);
		if (showEdge) for (int i = 0; i < edge.length; i++) paintEdge(edge[i]);
		if (showVertex) for (int i = 0; i < vertex.length; i++) paintVertex(vertex[i]);
	}
	
	private void paintVertex(Vertex v) {
		gc.setFill(v.p);
		gc.fillOval(v.x + w/2 - 2, v.y + h/2 - 2, 4, 4);
	}
	
	private void paintEdge(Edge e) {
		gc.setStroke(e.p);
		gc.strokeLine(e.v[0].x + w/2, e.v[0].y + h/2, e.v[1].x + w/2, e.v[1].y + h/2);
	}
	
	private void paintFace(Face f) {
		double[] xx = new double[f.v.length];
		double[] yy = new double[f.v.length];
		for (int i = 0; i < f.v.length; i++) {
			xx[i] = f.v[i].x + w/2;
			yy[i] = f.v[i].y + h/2;
		}
		gc.setFill(f.p);
		gc.fillPolygon(xx, yy, xx.length);
	}
	
	/**
	 * 0 : 2D
	 * 1 : 3D
	 * 2 : Vertexes
	 * 3 : Edges
	 * 4 : Faces
	 * 5 : AxisV
	 * 6 : AxisE
	 */
	void changeView(int i) {
		if (i == 0) System.out.println("2D");
		else if (i == 1) System.out.println("3D");
		else if (i == 2) showVertex = !showVertex;
		else if (i == 3) showEdge = !showEdge;
		else if (i == 4) showFace = !showFace;
		else if (i == 5) showAxisV = !showAxisV;
		else if (i == 6) showAxisE = !showAxisE;
		else System.out.println("Error-[ScatterPlotCanvas.changeView()] : There is no method for this MenuItem.");
		repaint();
	}
	
	/**
	 * 0 : 2D
	 * 1 : 3D
	 */
	void loadData(int i) {
		if (i == 0 || i == 1) {
			File fl = new FileChooser().showOpenDialog(new Stage());
			if (fl == null) return;
			
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(fl));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
			
			try {
				String line;
				char c = 'c';
				ArrayList<Vertex> loadedVertexs = new ArrayList<Vertex>();
				ArrayList<Edge> loadedEdges = new ArrayList<Edge>();
				ArrayList<Face> loadedFaces = new ArrayList<Face>();
				
				while ((line = br.readLine()) != null) {
					if (line.equals("")) continue;
					String[] token = line.split(" ");
					
					if (token[0].equalsIgnoreCase("Vertex")) {
						c = 'v';
						continue;
					} else if (token[0].equalsIgnoreCase("Edge")) {
						c = 'e';
						continue;
					} else if (token[0].equalsIgnoreCase("Face")) {
						c = 'f';
						continue;
					} else if (token[0].equalsIgnoreCase("/*")) {
						c = 'c';
						continue;
					}
					
					if (c == 'v') {
						double x = Double.parseDouble(token[0]);
						double y = Double.parseDouble(token[1]);
						double z = Double.parseDouble(token[2]);
						double p1 = 1, p2 = 1, p3 = 1;
						
						if (token.length == 6) {
							p1 = Double.parseDouble(token[3]);
							p2 = Double.parseDouble(token[4]);
							p3 = Double.parseDouble(token[5]);
						}
						
						loadedVertexs.add(new Vertex(x, y, z, new Color(p1, p2, p3, 1)));
						
					} else if (c == 'e') {
						int v1 = Integer.parseInt(token[0]);
						int v2 = Integer.parseInt(token[1]);
						
						loadedEdges.add(new Edge(loadedVertexs.get(v1), loadedVertexs.get(v2), new Color(1, 1, 1, 1)));
					
					} else if (c == 'f') {
						Vertex[] v = new Vertex[token.length];
						
						for (int j = 0; j < token.length; j++)
							v[j] = loadedVertexs.get(Integer.parseInt(token[j]));
						
						loadedFaces.add(new Face(v, new Color(0.9, 0.9, 0.9, 1)));
					}
				}
				
				resetAxis();
				
				vertex = new Vertex[loadedVertexs.size()];
				edge = new Edge[loadedEdges.size()];
				face = new Face[loadedFaces.size()];
				
				for (int j = 0; j < loadedVertexs.size(); j++) vertex[j] = loadedVertexs.get(j);
				for (int j = 0; j < loadedEdges.size(); j++) edge[j] = loadedEdges.get(j);
				for (int j = 0; j < loadedFaces.size(); j++) face[j] = loadedFaces.get(j);
				
				calc(0, Math.PI, 0);
				
				br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else System.out.println("Error-[ScatterPlotCanvas.readData()] : There is no method for this MenuItem.");
		repaint();
	}
	
	/**
	 * 0 : Animation start
	 * 1 : Animation stop
	 * 2 : Size +
	 * 3 : Size -
	 */
	void details(int i) {
		if (i == 0) timeline.play();
		else if (i == 1) timeline.pause();
		else if (i == 2) System.out.println("Not ready");
		else if (i == 3) System.out.println("Not ready");
		else System.out.println("Error-[ScatterPlotCanvas.details()] : There is no method for this MenuItem.");
	}
	
	void mousePressed(double x, double y) {
		currX = x;
		currY = y;
	}
	
	void mouseDragged(double x, double y) {
		prevX = currX;
		prevY = currY;
		currX = x;
		currY = y;
		calc((currX - prevX) / 100, (currY - prevY) / 100, 0);
	}
	
	void calc(double x, double y, double z) {
		for (int i = 0; i < axisV.length; i++) {
			axisV[i].addAngleX(x);
			axisV[i].addAngleY(y);
			axisV[i].addAngleZ(z);
		}
		for (int i = 0; i < axisE.length; i++) {
			axisE[i].v[0].addAngleX(x);
			axisE[i].v[0].addAngleY(y);
			axisE[i].v[0].addAngleZ(z);
			axisE[i].v[1].addAngleX(x);
			axisE[i].v[1].addAngleY(y);
			axisE[i].v[1].addAngleZ(z);
		}
		for (int i = 0; i < vertex.length; i++) {
			vertex[i].addAngleX(x);
			vertex[i].addAngleY(y);
			vertex[i].addAngleZ(z);
		}
		repaint();
	}
	
	void animation() {
		if (autoSpin = !autoSpin) timeline.play();
		else timeline.pause();
	}
	
	void axisView() {
		showAxisE = showAxisV = !showAxisE;
		repaint();
	}
	
	void resetAxis() {
		axisV[0] = new Vertex( 200, 0, 0, red);
		axisV[1] = new Vertex(-200, 0, 0, red);
		axisV[2] = new Vertex(0,  200, 0, green);
		axisV[3] = new Vertex(0, -200, 0, green);
		axisV[4] = new Vertex(0, 0,  200, blue);
		axisV[5] = new Vertex(0, 0, -200, blue);
		axisV[6] = new Vertex( 400, 0, 0, red);
		axisV[7] = new Vertex(-400, 0, 0, red);
		axisV[8] = new Vertex(0,  400, 0, green);
		axisV[9] = new Vertex(0, -400, 0, green);
		axisV[10] = new Vertex(0, 0,  400, blue);
		axisV[11] = new Vertex(0, 0, -400, blue);
		axisE[0] = new Edge(new Vertex(400, 0, 0, p), new Vertex(-400, 0, 0, p), red);
		axisE[1] = new Edge(new Vertex(0, 400, 0, p), new Vertex(0, -400, 0, p), green);
		axisE[2] = new Edge(new Vertex(0, 0, 400, p), new Vertex(0, 0, -400, p), blue);
	}
}


class Vertex {
	double x, y, z;
	Color p;
	
	Vertex(double xx, double yy, double zz, Color pp) {
		x = xx;
		y = yy;
		z = zz;
		p = pp;
	}
	
	void addAngleX(double xx) {
		if (x == 0.0 && z == 0.0) return;
		double angle = (0 <= z) ? Math.atan(x/z) + xx : Math.atan(x/z) + xx + Math.PI;
		double r = Math.sqrt(x*x + z*z);
		x = r * Math.sin(angle);
		z = r * Math.cos(angle);
	}
	
	void addAngleY(double yy) {
		if (y == 0.0 && z == 0.0) return;
		double angle = (0 <= z) ? Math.atan(y/z) + yy : Math.atan(y/z) + yy + Math.PI;
		double r = Math.sqrt(y*y + z*z);
		y = r * Math.sin(angle);
		z = r * Math.cos(angle);
	}
	
	void addAngleZ(double zz) {
		if (x == 0.0 && y == 0.0) return;
		double angle = (0 <= x) ? Math.atan(y/x) + zz : Math.atan(y/x) + zz + Math.PI;
		double r = Math.sqrt(x*x + y*y);
		y = r * Math.sin(angle);
		x = r * Math.cos(angle);
	}
}


class Edge {
	Vertex[] v = new Vertex[2];
	Color p;
	
	Edge(Vertex vv, Vertex ww, Color pp) {
		v[0] = vv;
		v[1] = ww;
		p = pp;
	}
}


class Face {
	Vertex[] v;
	Color p;
	
	Face(Vertex[] vv, Color pp) {
		v = vv;
		p = pp;
	}
}