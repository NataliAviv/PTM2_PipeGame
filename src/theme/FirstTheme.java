package theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class FirstTheme implements ThemeDisplayer	{

	/*public Media music;
	public Image backgroundImage;
	
	public FirstTheme() {
		try {
			this.backgroundImage = new Image(new FileInputStream("./resources/FirstTheme/Background.jpg"));
			this.music = new Media (new File("./resources/FirstTheme/song.mp3").toURI().toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}*/
	
	@Override
	public String get_Line() {
		return "./resources/FirstTheme/-.jpg";
	}

	@Override
	public String get_I() {
		return "./resources/FirstTheme/I.jpg";
	}

	@Override
	public String getPipe_7() {
		return "./resources/FirstTheme/7.jpg";
	}

	@Override
	public String getPipe_J() {
		return "./resources/FirstTheme/J.jpg";
	}

	@Override
	public String getPipe_L() {
		return "./resources/FirstTheme/L.jpg";
	}

	@Override
	public String getPipe_F() {
		return "./resources/FirstTheme/F.jpg";
	}
	
	@Override
	public String get_s() {
		return "./resources/FirstTheme/Start.png";
	}
	
	@Override
	public String get_g() {
		return "./resources/FirstTheme/marioStop.png";
	}

	@Override
	public String getBackgroundImage() {
		return "./resources/FirstTheme/Background.jpg";
	}

	@Override
	public String getMusic() {
		return "./resources/FirstTheme/song.mp3";
	}
	
}