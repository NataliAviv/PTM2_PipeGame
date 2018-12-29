package theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ThemeMap {
	private MediaPlayer media;
	private String backgroundImageOn; 
	public class ImageURL {
		String pathURL;

		public ImageURL(String s) {
			pathURL = s;
		}

		public String getPathURL() {
			return pathURL;
		}
	};

	ThemeDisplayer theme;
	Map<Character, ImageURL> imagesPath = new HashMap<>();
	Map<Character, Image> images = new HashMap<>();

	public ThemeMap(ThemeDisplayer atheme){
			this.theme=atheme;

			imagesPath.put('-', new ImageURL(theme.get_Line()));
			imagesPath.put('|', new ImageURL(theme.get_I()));
			imagesPath.put('7', new ImageURL(theme.getPipe_7()));
			imagesPath.put('J', new ImageURL(theme.getPipe_J()));
			imagesPath.put('L', new ImageURL(theme.getPipe_L()));
			imagesPath.put('F', new ImageURL(theme.getPipe_F()));
			imagesPath.put('s', new ImageURL(theme.get_s()));
			imagesPath.put('g', new ImageURL(theme.get_g()));
			this.BackgroundImage();
			this.MusicOn();
	}

	public Image getImage(char type) {
		if (!images.containsKey(type)) {
			String path = imagesPath.get(type).getPathURL();
			System.out.println(path);
			Image a = null;
			try {
				a = new Image(new FileInputStream(path));
				System.out.println(a);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			images.put(type, a);
		}
		return images.get(type);
	}
	
	public void BackgroundImage() {
		/* try {
				this.backgroundImageOn = new Image(new FileInputStream(".resources/FirstTheme/Background.jpg"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/
			backgroundImageOn = theme.getBackgroundImage();//new Image(new FileInputStream(backgroundFileName.get()));

		SnapshotParameters snp = new SnapshotParameters();
		snp.setFill(Color.TRANSPARENT);
	}
	
	public void MusicOn(){
		if(media!=null){
			media.stop();
		}
		String musicFile = theme.getMusic();
		Media music = new Media(new File(musicFile).toURI().toString());
		media = new MediaPlayer(music);
		media.play();
	}


}
