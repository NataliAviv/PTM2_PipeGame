package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.ModelPg;
import theme.FirstTheme;
import theme.SecondTheme;
import theme.ThemeDisplayer;
import viewModel.ViewModel;

public class MainWindowsController implements Initializable {
	@FXML
	PipeDisplayer pipeDisplayer;


	public StringProperty timeLeft;
	public StringProperty currentWindow;
	ViewModel viewmodel;
	private ListProperty<char[]> pgboard;
	ModelPg modelpg;
	ThemeDisplayer theme;
	int buttonClicked = 0;
	
	public MainWindowsController() {
		modelpg = new ModelPg();
		viewmodel = new ViewModel(modelpg);
		this.pgboard = new SimpleListProperty<>();
		this.pgboard.bind(viewmodel.pgboard);
	}

	public void MoushClick() {
		pipeDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {pipeDisplayer.requestFocus();});
		pipeDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double H = pipeDisplayer.getH();
				System.out.println(H);
				double W = pipeDisplayer.getW();
				System.out.println(W);
				int i = (int) (event.getX() / W);
				int j = (int) (event.getY() / H);
				System.out.println(i+" "+j);
				viewmodel.switchCell(i, j);
				pipeDisplayer.setpipeboard(pgboard);
			}
		});
	}/*
	public void countMouseClick()
	{
		objButton1.addActionListener(new ActionListener() {

		    public void actionPerformed(ActionEvent e)
		    {
		       buttonClicked++;
		       System.out.println(buttonClicked);
		    }
		}); 
	}*/
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pipeDisplayer.setPipeData(pgboard, new FirstTheme());
		MoushClick();
	}

	public void start() {
		System.out.println("start the game!\n");
	}

	public void openFile() throws IOException {
		FileChooser fc = new FileChooser();
		fc.setTitle("open saved level");
		fc.setInitialDirectory(new File("./resources"));
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {

			System.out.println(chosen.getName());
			viewmodel.load(chosen);
			pipeDisplayer.redraw();
		}
	}

	public void setFirstTheme() {
		ThemeDisplayer firstTheme = new FirstTheme();
		pipeDisplayer.setPipeTheme(firstTheme);
	}

	public void setSecondTheme() {
		ThemeDisplayer secondTheme = new SecondTheme();
		pipeDisplayer.setPipeTheme(secondTheme);
	}

	public void save() throws IOException {
		if (viewmodel.save()) {
			saveMessage();
		} else
			wonMessage();
	}

	public void wonMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Congratulations");
		alert.setHeaderText(null);
		alert.setContentText("You Won!");
		alert.showAndWait();
	}

	public void saveMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Saved");
		alert.setHeaderText(null);
		alert.setContentText("Game saved");
		alert.showAndWait();
	}
	public void errorMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("OOPS Something is wrong");
		alert.setHeaderText(null);
		alert.setContentText("Game error");
		alert.showAndWait();
	}
	
	public void settings() throws FileNotFoundException {
			modelpg.setHost("localhost");
			modelpg.setPort(6400);
		}

    

	
}
