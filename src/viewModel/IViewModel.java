package viewModel;

import java.io.File;

public interface IViewModel{
void changeCells(int i,int j);
void load(String boardPath);
void save(File file);
void solve();


}
