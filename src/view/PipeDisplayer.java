package view;
import Theme.ThemeDisplayer;
import Theme.ThemeMap;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.input.MouseEvent;
public class PipeDisplayer extends Canvas {
	char[][] pipeData;
	ThemeMap themeMap;
	
	public PipeDisplayer() {
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

	public void setPipeData(char[][] pipeData, ThemeDisplayer themeDisp) {
		this.pipeData = pipeData;
		this.themeMap=new ThemeMap(themeDisp);
		redraw();
	}

	public void redraw() {
		if(pipeData!=null){
			double W = getWidth();
			double H = getHeight();
			double w = W/pipeData[0].length;
			double h = H/pipeData.length;
			GraphicsContext gc=getGraphicsContext2D();
			gc.clearRect(0, 0, W, H);
			for(int i=0;i<pipeData.length;i++)
				for(int j=0;j<pipeData[i].length;j++){
					if(pipeData[i][j]!=' ')
						gc.drawImage(themeMap.getImage(pipeData[i][j]), j*w, i*h, w, h);
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

			this.redraw();
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