package master;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import main.Server;

public class MasterServer {

	public static final int PORT = 4444;
	public static final String HOST = "localhost";
	public ArrayList<Server> serverList = new ArrayList<>();

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		new MasterServer().runServer();
	}

	/**
	 * Run server.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public void runServer() throws IOException, ClassNotFoundException {

		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Master Server initiated.");

		while (true) {

			Socket socket = serverSocket.accept();

			try {

				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

				objectOutputStream.writeObject("Server created successfully.");

				Server s = (Server) objectInputStream.readObject();

				this.serverList.add(s);

				System.out.println("Server \"" + s.name + "\" added to game list.");

			} catch (IOException e) {

				e.printStackTrace();

			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		}
	}
}
