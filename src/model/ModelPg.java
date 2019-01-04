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
			break;
		case 'F':
			this.pgboard.get(i)[j] = '7';
			break;
		case 'J':
			this.pgboard.get(i)[j] = 'L';
			break;
		case 'L':
			this.pgboard.get(i)[j] = 'F';
			break;
		case '|':
			this.pgboard.get(i)[j] = '-';
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
			if (str.contains("step")) {

				countStep.set(Integer.parseInt(str.split(":")[1]));

			}
			else {
				line = str.toCharArray();
				System.out.println(line);
				this.pgboard.add(line);
			}
		}
		scan.close();

	}


	public List<String> solve() {

//		Thread th = new Thread(() -> {
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
//		});
//
//		th.start();
	}

	public void UpdateBoard() {
		this.pgboard.add("s-|7-".toCharArray());
		this.pgboard.add("|-J--".toCharArray());
		//this.pgboard.add("|7--L".toCharArray());
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
			for (int i = 0; i < this.pgboard.getSize() ; i++) {
				s = new String(this.pgboard.get(i));
				pr.println(s);
				//pr.println("time" + timer.get());
			}
			pr.println("step:" + countStep.get());
			pr.close();
			System.out.println("The level saved in levels folder");
		}
		if (file.exists())
			return true;
		else
			return false;
	}
}
