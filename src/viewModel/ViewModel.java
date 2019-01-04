package viewModel;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	public IntegerProperty countStep=new SimpleIntegerProperty();
	//public IntegerProperty timer=new SimpleIntegerProperty();
	

	public ViewModel(ModelPg modelpg) {
		this.modelpg=modelpg;
		this.pgboard = new SimpleListProperty<>();
		this.pgboard.bind(modelpg.pgboard);
		this.countStep.bind(modelpg.countStep);
		//this.timer.bind(modelpg.timer);
		/*this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bind(modelpg.win);*/
	}

	public void switchCell(int j, int i) {
		this.modelpg.switchCell(j, i);
	}

	

	public boolean save() throws IOException  {
	return this.modelpg.save();

	}

	public void solve() {
	//	this.modelpg.solve();

	}
	public void setPort(int port) {
		this.modelpg.setPort(port);
	}

	public void setHost(String host) {
		this.modelpg.setHost(host);
	}


	public ModelPg getModelpg() {
		return modelpg;
	}

	public void setModelpg(ModelPg modelpg) {
		this.modelpg = modelpg;
	}

	public void load(File file) {
		this.modelpg.load(file);
	}
	
	public boolean finish()throws IOException, InterruptedException
	{
		return this.modelpg.finishGame();
	}
	
}
