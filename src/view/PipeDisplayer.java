package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class PipeDisplayer extends Canvas {

	int X, Y;

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public PipeDisplayer() {
		wallFileName = new SimpleStringProperty();
		PipeDisplayer pipeDisplayer=this;
		   this.setOnMouseClicked(new EventHandler<MouseEvent>() {
		
		  @Override public void handle(MouseEvent event) { 
			  double H =pipeDisplayer.getHeight();
			  double W = pipeDisplayer.getWidth();
		  int h =(int)((double)H / (double)pipeData.length); 
		  int w = (int)((double)W /(double)pipeData[0].length);
		  int mx = (int) event.getX();
		  int my = (int) event.getY();
		  int i = my / h; 
		  int j = mx / w;
		  pipeDisplayer.switchCell(i, j, 1);
		  
		  } });
	}

	char[][] pipeData;
	private StringProperty wallFileName;

	public String getWallFileName() {
		return wallFileName.get();
	}

	public void setWallFileName(String wallFileName) {
		this.wallFileName.set(wallFileName);
	}

	GraphicsContext g = getGraphicsContext2D();

	public void setPipeData(char[][] pipeData) {
		this.pipeData = pipeData;
		redraw(g);
	}

	public void redraw(GraphicsContext gc) {

		int H = (int) this.getHeight();
		int W = (int) this.getWidth();
		gc.clearRect(0, 0, W, H);
		int h = (int) ((double) H / (double) this.pipeData.length);
		int w = (int) ((double) W / (double) this.pipeData[0].length);
		gc.setFill(Color.BLACK);
		Image line = null;
		try {
			line = new Image(new FileInputStream("./Images/-.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image seven = null;
		try {
			seven = new Image(new FileInputStream("./Images/7.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image f = null;
		try {
			f = new Image(new FileInputStream("./Images/F.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image je = null;
		try {
			je = new Image(new FileInputStream("./Images/J.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image l = null;
		try {
			l = new Image(new FileInputStream("./Images/L.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image ie = null;
		try {
			ie = new Image(new FileInputStream("./Images/I.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < this.pipeData.length; ++i) {
			for (int j = 0; j < this.pipeData[i].length; ++j) {
				gc.rect(j * w, i * h, w, h);
				switch (this.pipeData[i][j]) {
				case '-':
					gc.drawImage(line, j*w, i*h, w, h);
					break;
				case '7':
					gc.drawImage(seven, j*w, i*h, w, h);

					break;
				case 'F':
					gc.drawImage(f, j*w, i*h, w, h);

					break;
				case 'J':
					gc.drawImage(je, j*w, i*h, w, h);

					break;
				case 'L':
					gc.drawImage(l, j*w, i*h, w, h);

					break;
				case 'g':
					gc.setFill(Color.RED);
					gc.strokeOval(j * w + w / 6, i * h + h / 6, 4 * w / 6, 4 * h / 6);
					gc.fillOval(j * w + w / 4, i * h + h / 4, w / 2, h / 2);
					break;
				case 's':
					gc.setFill(Color.GREEN);
					gc.fillOval(j * w + w / 4, i * h + h / 4, w / 2, h / 2);
					break;
				case '|':
					gc.drawImage(ie, j*w, i*h, w, h);

					break;
				}
			}
		}
	}

	public void switchCell(int i, int j, int times) {
		for (int t = 0; t < times; ++t) {
			switch (this.pipeData[i][j]) {
			case '-':
				this.pipeData[i][j] = '|';
				break;
			case '7':
				this.pipeData[i][j] = 'J';
				break;
			case 'F':
				this.pipeData[i][j] = '7';
				break;
			case 'J':
				this.pipeData[i][j] = 'L';
				break;
			case 'L':
				this.pipeData[i][j] = 'F';
				break;
			case '|':
				this.pipeData[i][j] = '-';
			}

			this.redraw(g);
			if (t < times - 1) {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException var6) {
					;
				}
			}
		}

	}
}