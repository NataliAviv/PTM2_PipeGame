package view;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import theme.ThemeDisplayer;
import theme.ThemeMap;

public class PipeDisplayer extends Canvas {
	private List<char[]> pgboard;
	ThemeDisplayer theme;
	double w,h;
	
	public void setPipeData(List<char[]> pgboard, ThemeDisplayer themeDisp) {
//		this.pgboard = pgboard;
//		ThemeMap.getInstance().setTheme(themeDisp);
		setPipeTheme(themeDisp);
		setpipeboard(pgboard);
	}
	
	public void setpipeboard(List<char[]> pgboard) {
		this.pgboard=pgboard;
		redraw();
	}
	
	public void redraw() {
		if(pgboard!=null){
			//size of canvas
			double W = getWidth();
			double H = getHeight();
			//size of small square
			w = W/pgboard.get(0).length;
			h = H/pgboard.size();
			GraphicsContext gc=getGraphicsContext2D();
			gc.clearRect(0, 0, W, H);
			for(int i=0;i<pgboard.size();i++)
				for(int j=0;j<pgboard.get(i).length;j++){
					if(pgboard.get(i)[j]!=' ')
						gc.drawImage(ThemeMap.getInstance().getImage(pgboard.get(i)[j]), j*w, i*h, w, h);
				}
		}
	}
	
	public void resize(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
        redraw();
    }

	public boolean isResizable() {
		return true;
	}
	public double minHeight(double width) {
		return 100;
	}

	public double maxHeight(double width) {
		return 1200;
	}


	public double prefHeight(double width) {
		return minHeight(width);
	}


	public double minWidth(double height) {
		return 0;
	}

	public double maxWidth(double height) {
		return 10000;
	}
	
	public void switchCell(int i, int j, int times) {
		
		for (int t = 0; t < times; ++t) {/*
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
*/
			//viewModel changecell
			this.redraw();
			if (t < times - 1) {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException var6) {;}
			}
		}

	}
	

	public void setPipeTheme(ThemeDisplayer themedisp) {
		ThemeMap.getInstance().setTheme(themedisp);
		/*this.theme=themedisp;*/
		/*setPipeData(this.pgboard,themedisp);*/
		
	}
	
	
	public double getW() {
		return w;
	}
	public double getH() {
		return h;
	}
}