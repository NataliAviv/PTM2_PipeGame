package view;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import theme.FirstTheme;
import theme.SecondTheme;
import theme.ThemeDisplayer;

public class MainWindowsController implements Initializable {
	char[][] pipeData = { 
			{ 's', '-', '7', ' ' },
			{ ' ', '|', 'L', '7' },
			{ '-', 'F', ' ', '|' },
			{ '7', 'F', '-', 'J' },
			{ ' ', 'g', ' ', '-' } };

	@FXML
	PipeDisplayer pipeDisplayer;
	public StringProperty timeLeft;
    public StringProperty currentWindow;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		pipeDisplayer.setPipeData(pipeData,new FirstTheme());
		
	}

	public void start() {
		System.out.println("start the game!\n");
	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open pipe file");
		fc.setInitialDirectory(new File("./Images"));
		fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Prawn XML files", "*.xml"));
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {
			System.out.println(chosen.getName());
		}
	}
	public void setFirstTheme()
	{
		ThemeDisplayer firstTheme = new FirstTheme();
		pipeDisplayer.setPipeTheme(firstTheme);
	}
	public void setSecondTheme()
	{
		ThemeDisplayer secondTheme = new SecondTheme();
		pipeDisplayer.setPipeTheme(secondTheme);
	}
	

  

}
