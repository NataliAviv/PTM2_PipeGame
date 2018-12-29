package theme;



public class SecondTheme implements ThemeDisplayer {
	/*public Media music;
	public Image backgroundImage;
	
	public SecondTheme() {
		try {
			this.backgroundImage = new Image(new FileInputStream("./resources/SecondTheme/background.jpg"));
			this.music = new Media (new File("./resources/SecondTheme/MarioSong.mp3").toURI().toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}*/
	@Override
	public String get_Line() {
		return "./resources/SecondTheme/theme-.png";
	}

	@Override
	public String get_I() {
		return "./resources/SecondTheme/themI.png";
	}

	@Override
	public String getPipe_7() {
		return "./resources/SecondTheme/theme7.png";
	}

	@Override
	public String getPipe_J() {
		return "./resources/SecondTheme/themJ.png";
	}

	@Override
	public String getPipe_L() {
		return "./resources/SecondTheme/themeL.png";
	}

	@Override
	public String getPipe_F() {
		return "./resources/SecondTheme/themeF.png";
	}

	@Override
	public String get_s() {
		return "./resources/SecondTheme/startmario.png";
	}

	@Override
	public String get_g() {
		return "./resources/SecondTheme/STOP.png";
	}

	@Override
	public String getBackgroundImage() {
	
		return "./resources/SecondTheme/background.jpg";
	}

	@Override
	public String getMusic() {
		return "./resources/SecondTheme/MarioSong.mp3";
	}


}
