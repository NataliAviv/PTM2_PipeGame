package model;

import java.io.BufferedReader;
import java.io.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class ModelPg extends Observable implements IModel {

	public ListProperty<char[]> pgboard;
	public String host;
	public int port;
	public IntegerProperty countStep = new SimpleIntegerProperty();
	private Socket serverSocket;

	// public IntegerProperty timer=new SimpleIntegerProperty();
	public ModelPg() {
		this.pgboard = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
		UpdateBoard();
		this.port = 6400;
		this.host = "localhost";

	}


//function that rotate the pipes and count the number of step
	public void switchCell(int i, int j) {
		switch (this.pgboard.get(i)[j]) {
		case '-':
			this.pgboard.get(i)[j] = '|';
			//countStep.set(countStep.get() + 1);
			break;
		case '7':
			this.pgboard.get(i)[j] = 'J';
			//countStep.set(countStep.get() + 1);
			break;
		case 'F':
			this.pgboard.get(i)[j] = '7';
			//countStep.set(countStep.get() + 1);
			break;
		case 'J':
			this.pgboard.get(i)[j] = 'L';
			//countStep.set(countStep.get() + 1);
			break;
		case 'L':
			this.pgboard.get(i)[j] = 'F';
			//countStep.set(countStep.get() + 1);
			break;
		case '|':
			this.pgboard.get(i)[j] = '-';
			//countStep.set(countStep.get() + 1);
			break;
		// start
		case 's':
			this.pgboard.get(i)[j] = 's';
			break;
		// end
		case 'g':
			this.pgboard.get(i)[j] = 'g';
			break;
		}
	}

	public void load(File level) {
		Scanner scan = null;
		try {
			scan = new Scanner(level);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!scan.equals(null)) {
			this.pgboard.clear();
		}
		while (scan.hasNext()) {
			char[] line = null;
			String str = scan.nextLine();
			line = str.toCharArray();
			System.out.println(line);
			this.pgboard.add(line);
		}
		scan.close();

	}

	public char[][] pbBoardToPaddedArray() {
		int length = pgboard.getSize() + 2;
		char[][] target = new char[length][pgboard.get(0).length + 2];
		for (int i = 0; i < pgboard.getSize(); i++) {
			System.arraycopy(pgboard.get(i), 0, target[i + 1], 1, pgboard.get(0).length);
		}
		return target;
	}

	public boolean isGoal() {
		int s_row = 0, s_col = 0;
		char[][] padded_state = pbBoardToPaddedArray();

		// search for 's'
		for (int i = 0; i < padded_state.length; i++) {
			for (int j = 0; j < padded_state[i].length; j++) {
				if (padded_state[i][j] == 's') {
					s_row = i;
					s_col = j;
					break;
				}
			}
		}

		// "walk" in each possible direction and return if reached 'g'
		return (
				checkIfGoalInDirection(padded_state, s_row + 1, s_col, 0) || // down
				checkIfGoalInDirection(padded_state, s_row, s_col + 1, 1) || // right
				checkIfGoalInDirection(padded_state, s_row - 1, s_col, 2) || // up
				checkIfGoalInDirection(padded_state, s_row, s_col - 1, 3) // left
		);
	}

	public boolean checkIfGoalInDirection(char[][] state, int row, int col, int dir) {

		if (state[row][col] == 'g') {
			return true;
		}

		switch (state[row][col]) {
			case '|': {
				switch (dir)
				{
					case 0:
					{
						return checkIfGoalInDirection(state, row + 1, col, 0);
					}
					case 2:
					{
						return checkIfGoalInDirection(state, row - 1, col, 2);
					}
					case 1:
					case 3:
					{
						return false;
					}
				}
			}
			case '-': {
				switch (dir)
				{
					case 1:
					{
						return checkIfGoalInDirection(state, row, col + 1, 1);
					}
					case 3:
					{
						return checkIfGoalInDirection(state, row, col - 1, 3);
					}
					case 0:
					case 2:
					{
						return false;
					}
				}
			}
			case '7': {
				switch (dir)
				{
					case 1:
					{
						return checkIfGoalInDirection(state, row + 1, col, 0);
					}
					case 2:
					{
						return checkIfGoalInDirection(state, row, col - 1, 3);
					}
					case 0:
					case 3:
					{
						return false;
					}
				}
			}
			case 'J': {
				switch (dir)
				{
					case 1:
					{
						return checkIfGoalInDirection(state, row - 1, col, 2);
					}
					case 0:
					{
						return checkIfGoalInDirection(state, row, col - 1, 3);
					}
					case 2:
					case 3:
					{
						return false;
					}
				}
			}
			case 'F': {
				switch (dir)
				{
					case 2:
					{
						return checkIfGoalInDirection(state, row, col + 1, 1);
					}
					case 3:
					{
						return checkIfGoalInDirection(state, row + 1, col, 0);
					}
					case 0:
					case 1:
					{
						return false;
					}
				}
			}
			case 'L': {

				switch (dir)
				{
					case 3:
					{
						return checkIfGoalInDirection(state, row - 1, col, 2);
					}
					case 0:
					{
						return checkIfGoalInDirection(state, row, col + 1, 1);
					}
					case 2:
					case 1:
					{
						return false;
					}
				}
			}
			default:
				return false;

		}
	}

	public List<String> solve() {

		List<String> solution = new ArrayList<>();
			try {
				Socket theServer = new Socket(host, port);
				PrintWriter out = new PrintWriter(theServer.getOutputStream());

				for (int i = 0; i < pgboard.size(); ++i) {
					out.println(new String(pgboard.get(i)));
				}

				out.println("done");
				out.flush();
				BufferedReader in = new BufferedReader(new InputStreamReader(theServer.getInputStream()));

				String line;
				while (!(line = in.readLine()).equals("done")) {
					solution.add(line);
				}

				in.close();
				out.close();
				theServer.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return solution;
	}

	public void UpdateBoard() {
		this.pgboard.add("s-|7-".toCharArray());
		this.pgboard.add("|-J--".toCharArray());
		this.pgboard.add("|7--L".toCharArray());
		this.pgboard.add("|L-Fg".toCharArray());
	}

	public boolean finishGame() throws IOException, InterruptedException {
		if (serverSocket != null) {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
			PrintWriter outToServer = new PrintWriter(this.serverSocket.getOutputStream());

			for (char[] line : this.pgboard.get()) {
				outToServer.println(line);
				outToServer.flush();
			}

			outToServer.println("done");
			outToServer.flush();

			String line;

			if ((line = inFromServer.readLine()).equals("done"))
				return true;
		}
		return false;

	}


	@Override
	public void update(Observable arg0, Object arg1) {

		//List<String> solution = (List<String>) arg1;

		UpdateBoard();

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
		this.port = port;
	}

	public void setHost(String host) {
		this.host = host;

	}

	public boolean isGoalState() {
		return false;
	}

	public boolean save() throws IOException {
		// Get a Calendar and set it to the current time.
		Calendar cal = Calendar.getInstance();
		cal.setTime(Date.from(Instant.now()));
		// Create a filename from a format string.
		String result = String.format("pg-%1$tY-%1$tm-%1$td-%1$tk-%1$tS-%1$tp.txt", cal);
		File file = new File("./resources/levels/" + result + ".txt");
		{
			System.out.println("Created New File To Save To");
			PrintWriter pr = new PrintWriter(file);
			String s;
			for (int i = 0; i < this.pgboard.getSize() - 1; i++) {
				s = new String(this.pgboard.get(i));
				pr.println(s);
				pr.println("step:" + countStep.get());
				//pr.println("time" + timer.get());
			}
			pr.close();
			System.out.println("The level saved in levels folder");
		}
		if (file.exists())
			return true;
		else
			return false;
	}
}
