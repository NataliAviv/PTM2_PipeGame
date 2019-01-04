package viewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import model.ModelPg;

public class ViewModel implements IViewModel {

	private ModelPg modelpg;
	public ListProperty<char[]> pgboard;
	public BooleanProperty isGoal;
	public StringProperty timeLeft;
	private Timer timer;
	private TimerTask task;

	public ViewModel(ModelPg modelpg) {
		this.modelpg=modelpg;
		this.pgboard = new SimpleListProperty<>();
		this.pgboard.bind(modelpg.pgboard);
		/*this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bind(modelpg.win);*/
	}

	public void switchCell(int i, int j) {
		this.modelpg.switchCell(i, j);
	}

	

	public boolean save() throws IOException  {
	return this.modelpg.save();

	}

	public List<String> solve() {
		return this.modelpg.solve();

	}
	public void setPort(int port) {
		this.modelpg.setPort(port);
	}

	public void setHost(String host) {
		this.modelpg.setHost(host);
	}

	public void win() {
		this.modelpg.win.set(this.modelpg.isGoalState());
	}

	public ModelPg getModelpg() {
		return modelpg;
	}

	public void setModelpg(ModelPg modelpg) {
		this.modelpg = modelpg;
	}

	public void startTimer() {
		stopTimer();
		timer = new Timer();
		timeLeft.setValue("10");
		task = new TimerTask() {
			@Override
			public void run() {
				timeLeft.set(String.valueOf(Integer.valueOf(timeLeft.get()) - 1));
				if (Integer.valueOf(timeLeft.get()) <= 0) {
					//currentWindow.setValue("overView");
					stopTimer();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	private void stopTimer() {
		if (timer != null) {
			modelpg.win.setValue(false);
			timeLeft.setValue("0");
			task.cancel();
			timer.cancel();
		}
	}

 
	public void load(File file) {
		this.modelpg.load(file);
	}
	
	//numberof step

}
