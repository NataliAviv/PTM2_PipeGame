package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import client.SolverClient;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class ModelPg extends Observable implements IModel {

	private SolverClient m_solver;
	private ListProperty<char[]> pgboard;
	public BooleanProperty win;
	public String host;
	public int port;
	public ModelPg(SolverClient solver)
	{
		m_solver = solver;
		m_solver.addObserver(this);
		this.pgboard = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
		// initializeBoard();
		this.port = 6400;
		this.host = "localhost";
		this.win = new SimpleBooleanProperty();
	}

	char[][] pipeData = { { 's', '-', '7', ' ' }, { ' ', '|', 'L', '7' }, { '-', 'F', ' ', '|' },
			{ '7', 'F', '-', 'J' }, { ' ', 'g', ' ', '-' } };

	public void changeCells(int i, int j) {
		switch (this.pipeData[i][j]) {
		case '-':
			this.pipeData[i][j] = '|';
			break;
		case '7':
			this.pipeData[i][j] = 'J';
			break;
		case 'F':
			this.pipeData[i][j] = '7';
			break;
		case 'J':
			this.pipeData[i][j] = 'L';
			break;
		case 'L':
			this.pipeData[i][j] = 'F';
			break;
		case '|':
			this.pipeData[i][j] = '-';
		}
	}

	public void load(String boardPath) {
		 List<char[]> PgBoardBuilder = new ArrayList<char[]>();
	        BufferedReader reader;
	        try {

	        reader = new BufferedReader(new FileReader(boardPath));
	        String line;
	        while ((line = reader.readLine()) != null)
	        	PgBoardBuilder.add(line.toCharArray());
	        this.pgboard.setAll(PgBoardBuilder.toArray(new char[PgBoardBuilder.size()][]));
	        reader.close();
	        }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		this.setChanged();
		this.notifyObservers();
	}


	public void solve() {
		String str = "";
		for (int i = 0; i < pgboard.getSize(); i++) {
			str += new String(pgboard.getValue().get(i));
			str += '\n';
		}
		str += "done" + '\n';
		try {
			m_solver.solve(str);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	public void UpdateBoard(List<String> solution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		List<String> solution = (List<String>) arg1;

		UpdateBoard(solution);

		this.setChanged();
		this.notifyObservers();
	}

	public ListProperty<char[]> getPgboard() {
		return pgboard;
	}

	public void setPgboard(ListProperty<char[]> pgboard) {
		this.pgboard = pgboard;
	}

	public void setPort(int port) {
		this.port=port;
	}

	public void setHost(String host) {
		this.host=host;

	}

	public boolean isGoalState() {
		return false;
	}

	public void save(File file) {
		try {
			PrintWriter write = new PrintWriter(file);
			for (int i = 0; i < this.pgboard.size(); i++) 
				write.println(new String(this.pgboard.get(i)));
			
			//write.println("time:" + time.get());
			//write.println("step:" + numSteps.get());
			write.flush();
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
