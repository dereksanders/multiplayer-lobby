package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import master.MasterServer;

/**
 * The Class Server.
 */
public class Server implements Serializable {

	private static final long serialVersionUID = -2164975550905862549L;
	public static final int PORT = 4445;
	public static final int MAX_USERS = 5000;
	public static final String HOST = "localhost";
	public String name = "Generic Server";

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		Server s = new Server();
		s.name = args[0];
		s.runServer();
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

		registerServer();

		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server waiting for connections...");

		while (true) {

			Socket socket = serverSocket.accept();
			new ServerThread(socket).start();
		}
	}

	private void registerServer() throws UnknownHostException, IOException, ClassNotFoundException {

		Socket socket = new Socket(MasterServer.HOST, MasterServer.PORT);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

		objectOutputStream.writeObject(this);

		System.out.println((String) objectInputStream.readObject());
	}

	public class ServerThread extends Thread {

		public Socket socket = null;

		ServerThread(Socket socket) {

			this.socket = socket;
		}

		public void run() {

			try {

				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

				Client joined = (Client) objectInputStream.readObject();

				System.out.println(joined.name + " is now connected.");

				objectOutputStream.writeObject("You joined the server.");

				while (true) {

					System.out.println(objectInputStream.readObject());
				}

			} catch (ClassNotFoundException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
}