package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.extern.java.Log;

//Class to communicate with the server
@Log
public class ServerConnector {

	private static int PORT = 8901;
	private static Socket socket;

	private static String KEY_ACTION = "A";
	private static String KEY_VALUES = "V";

	private static String KEY_MOVE_STARTPOINT = "S";
	private static String KEY_MOVE_ENDPOINT = "E";

	private static String VALUE_MOVE = "Move";

	private static BufferedReader in;
	private static PrintWriter out;

	public ServerConnector() throws UnknownHostException, IOException {
		socket = new Socket("localhost", PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		out = new PrintWriter(socket.getOutputStream(), true);
	}

	public void sendMovementRequest(int startX, int startY, int endX, int endY) throws JSONException, IOException {
		String command = createMovementJson(startX, startY, endX, endY).toString();
		out.println(command);
		log.info(String.format("Request move from %s has been send", command.toString()));
	}

	private static JSONObject createMovementJson(int startX, int startY, int endX, int endY) {
		ArrayList<Integer> startPoint = new ArrayList<Integer>();
		startPoint.add(startX);
		startPoint.add(startY);

		ArrayList<Integer> endPoint = new ArrayList<Integer>();
		endPoint.add(endX);
		endPoint.add(endY);

		JSONObject moveJson = new JSONObject().put(KEY_MOVE_STARTPOINT, startPoint).put(KEY_MOVE_ENDPOINT, endPoint);
		JSONObject commandJson = new JSONObject().put(KEY_ACTION, VALUE_MOVE).put(KEY_VALUES, moveJson);

		return commandJson;

	}
	
	public static JSONObject getServerCommand() throws IOException{
		return new JSONObject(in.readLine());
	}

}
