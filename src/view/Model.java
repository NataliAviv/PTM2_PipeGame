package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.StringReader;

public class Model {
	public static List<String>solve(String board, int port,String host)throws Exception {
			Socket theServer = new Socket(host, port);
			System.out.println("Connected to server");
			BufferedReader inFromUser = new BufferedReader(new StringReader(board));
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());

			String line;
			while (!(line = inFromUser.readLine()).equals("done")) {
				outToServer.println(line);
				outToServer.flush();
			}
			outToServer.println("done");
			outToServer.flush();
			List<String> solution = new ArrayList<>();
			String newline;
			while (!(newline = inFromServer.readLine()).equals("done")) {
				solution.add(newline);
				System.out.println(newline);
			}
			inFromServer.close();
			outToServer.close();
			inFromUser.close();
			theServer.close();
			return solution;
		}
	
}
