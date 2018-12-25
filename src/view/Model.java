package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Model {
	public static void solve(String ip, int port, PipeDisplayer pipe) {
		try {
			Socket theServer = new Socket(ip, port);
			PrintWriter out = new PrintWriter(theServer.getOutputStream());
			char[][] data = pipe.pipeData;

			for (int i = 0; i < data.length; ++i) {
				out.println(new String(data[i]));
			}

			out.println("done");
			out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(theServer.getInputStream()));

			String line;
			while (!(line = in.readLine()).equals("done")) {
				int i = Integer.parseInt(line.split(",")[0]);
				int j = Integer.parseInt(line.split(",")[1]);
				int times = Integer.parseInt(line.split(",")[2]);
				pipe.switchCell(i, j, times);
			}

			in.close();
			out.close();
			theServer.close();
		} catch (IOException var11) {
			JOptionPane.showInputDialog(pipe, var11.getMessage());
		}

	}

}
