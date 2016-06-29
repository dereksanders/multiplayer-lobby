package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import master.MasterServer;

/**
 * The Class Client.
 */
public class Client implements Serializable {

	private static final long serialVersionUID = -6235265951918859905L;
	public String name = "Default User";

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws UnknownHostException
	 *             the unknown host exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static void main(String[] args) {

		Client c = new Client();

		if (args.length > 0) {

			c.name = args[0];
		}

		try {

			c.joinServer();

		} catch (ClassNotFoundException | IOException e) {

			System.out.println("Failed to join server.");
			e.printStackTrace();
		}
	}

	public void joinServer() throws UnknownHostException, IOException, ClassNotFoundException {

		Socket socket = new Socket(Server.HOST, Server.PORT);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

		objectOutputStream.writeObject(this);

		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println(objectInputStream.readObject());

		while (true) {

			objectOutputStream.writeObject(name + ": " + inputReader.readLine());
		}
	}
}