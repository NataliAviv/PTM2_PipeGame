package Theme;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class ThemeMap {

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

}
