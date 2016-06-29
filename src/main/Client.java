package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The Class Client.
 */
public class Client {

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
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

		if (args.length > 0) {

			String name = args[0];
			Socket socket = new Socket(Server.HOST, Server.PORT);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

			objectOutputStream.writeObject(name);

			BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println(objectInputStream.readObject());

			while (true) {

				objectOutputStream.writeObject(name + ": " + inputReader.readLine());
			}

		} else {

			System.out.println("Usage: Client <name>");
		}
	}
}