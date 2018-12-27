package Theme;

public class FirstTheme implements ThemeDisplayer	{

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
	
}