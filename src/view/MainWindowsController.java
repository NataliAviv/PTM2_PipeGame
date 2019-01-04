package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ModelPg;
import theme.FirstTheme;
import theme.SecondTheme;
import theme.ThemeDisplayer;
import viewModel.ViewModel;

public class MainWindowsController implements Initializable {
	@FXML
	PipeDisplayer pipeDisplayer;
	@FXML
	Label countStep;
    @FXML
    Label TimerLabel;

	ViewModel viewmodel;
	private ListProperty<char[]> pgboard;
	ModelPg modelpg;
	ThemeDisplayer theme;
	private DoubleProperty timeSeconds= new SimpleDoubleProperty(0);
	private StringProperty timeLeft;
	private TimerTask task;
	private Timer timer;
	private double timeleft=0;

	public MainWindowsController() {
		modelpg = new ModelPg();
		viewmodel = new ViewModel(modelpg);
		this.pgboard = new SimpleListProperty<>();
		this.pgboard.bind(viewmodel.pgboard);
		viewmodel.countStep.addListener((observable, oldValue, newValue)->countStep.setText(Integer.toString(viewmodel.countStep.get())));
	}

    public void MouseClick() {
        pipeDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            pipeDisplayer.requestFocus();
        });
        pipeDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                double H = pipeDisplayer.getH();
                System.out.println(H);
                double W = pipeDisplayer.getW();
                System.out.println(W);
                int j = (int) (event.getX() / W);
                int i = (int) (event.getY() / H);
				System.out.println(i+" "+j);
				viewmodel.switchCell(i, j);
				pipeDisplayer.setpipeboard(pgboard);
				if (viewmodel.isGoal()) {
                    System.out.println("YOU WON!!!");
                    wonMessage();
                }
			}
		});

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		countStep.setText("0");
		//timer.setText("0");
		pipeDisplayer.setDisable(true);
        pipeDisplayer.setPipeData(pgboard, new FirstTheme());
        MouseClick();
    }

    public void start() {
        pipeDisplayer.setDisable(false);
			 System.out.println("start the game!\n");
			 startTimer();

	}
		public void stopTheGame() throws IOException
		{
			stopTimer();
			//theme.stopMusic();
			System.out.println("stop the game!\n");

		}

    public void solve() {
        List<String> sol =  this.viewmodel.solve();

        Thread th = new Thread(() -> {

            try {
                System.out.println("Solution:");
                for (int k = 0; k < sol.size(); k++) {
                    String line = sol.get(k);
                    int i = Integer.parseInt(line.split(",")[0]);
                    int j = Integer.parseInt(line.split(",")[1]);
                    int times = Integer.parseInt(line.split(",")[2]);
                    System.out.println(line);
                    for (int w = 0; w < times; w++) {
                        viewmodel.switchCell(i, j);
                    }
                    pipeDisplayer.setpipeboard(pgboard);
                    Thread.sleep(100);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        th.start();
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

	public void startTimer()  {
	    TimerLabel.textProperty().bind(timeSeconds.asString());
		timer = new Timer(true);
		task = new TimerTask(){
			@Override
			public void run() {
				timeleft+=0.1;
				Platform.runLater(new Runnable() {
					public void run() {timeSeconds.set(Double.parseDouble(String.format ("%,.2f", timeleft)));}
				});
			}
		};

		timer.scheduleAtFixedRate(task, 0, 100);

    }


	public void resetTimer()
	{
		timeleft=0;
	}

	public void stopTimer()
	{
		timer.cancel();
	}

	public Double saveTimer()
	{
		return timeleft;
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

	/*
	public boolean wonTheGame() throws IOException, InterruptedException {
		if(viewmodel.finish()==true)
		{

		}
	}*/

	//alert for the player, won,lose,save game
	public void wonMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Well Done");
		alert.setHeaderText(null);
		alert.setContentText("You Won! :)");
		alert.showAndWait();
	}
	public void LossMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sorry you loss");
		alert.setHeaderText(null);
		alert.setContentText("You loss :( ");
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
//a dialog function with the player
	//configuration Window show the port and the ip
    public void configurationWindow() throws FileNotFoundException {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
			ButtonType saveButtonType = new ButtonType("save", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
			GridPane grid = new GridPane();
			TextField port = new TextField();
			port.setPromptText(String.valueOf(modelpg.port));
			TextField ip = new TextField();
        ip.setPromptText(modelpg.host);
			grid.add(new Label("ip:"), 0, 1);
			grid.add(ip, 1, 1);
			grid.add(new Label("Port number:"), 0, 0);
			grid.add(port, 1, 0);
			Node loginButton = dialog.getDialogPane().lookupButton(saveButtonType);
			loginButton.setDisable(true);
			port.textProperty().addListener((observable, oldValue, newValue) -> {
				loginButton.setDisable(newValue.trim().isEmpty());
			});

			dialog.getDialogPane().setContent(grid);
			Platform.runLater(() -> port.requestFocus());
			dialog.setResultConverter(dialogButton -> {
				if (dialogButton == saveButtonType) {
					return new Pair<>(port.getText(), ip.getText());
				}
				return null;
			});

			Optional<Pair<String, String>> result = dialog.showAndWait();
			if(result.isPresent()) {
				String resultPort = result.get().getKey();
				String resultIp = result.get().getValue();
				modelpg.setPort(Integer.parseInt(resultPort));
				modelpg.setHost(resultIp);
			}

		}

}
