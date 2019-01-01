package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.locks.ReentrantLock;
import java.io.StringReader;

public class SolverClient extends Observable{
	 int m_port;
	 String m_host;
	 ReentrantLock solverLock;
	public SolverClient(int port, String host){
		
		m_port = port;
		m_host = host;
		solverLock = new ReentrantLock();
	}

	public void solve(String board)throws Exception {

		Thread th = new Thread(()-> {
			try {
				Socket theServer = new Socket(m_host, m_port);
				//System.out.println("Connected to server");
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
				
				//only one will update the view at the same time
				solverLock.lock();
				this.notifyObservers(solution);
				this.setChanged();
				solverLock.unlock();
				
			}
			catch(Exception e)
			{
				
			}
			});
		
		th.start();
		}
	
}
