package boot;

import java.io.IOException;


import org.json.JSONArray;
import org.json.JSONObject;

import figures.Figure;
import background.Tile;
import background.TileGrid;
import services.ServerConnector;

public class ServerCommandExecutor extends Thread {
	
	private TileGrid tileGrid;
	
	private static String KEY_ACTION = "A";
	private static String KEY_VALUES = "V";
	
	private static String VALUE_MOVE = "Move";
	
	private static String KEY_MOVE_STARTPOINT = "S";
	private static String KEY_MOVE_ENDPOINT = "E";

	
	public ServerCommandExecutor(TileGrid startTileGrid){
		tileGrid = startTileGrid;
	}
	
	public void run (){
		JSONObject command;
		try {	
			while (true) {				
				command = ServerConnector.getServerCommand();				
				if (command.get(KEY_ACTION).equals(VALUE_MOVE)) {
					JSONObject commandValues = command.getJSONObject(KEY_VALUES);
					JSONArray startPointArray = commandValues.getJSONArray(KEY_MOVE_STARTPOINT);
					JSONArray endPointArray = commandValues.getJSONArray(KEY_MOVE_ENDPOINT);
					makeMove(startPointArray.getInt(0), startPointArray.getInt(1), endPointArray.getInt(0), endPointArray.getInt(1));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void makeMove(int startX, int startY, int endX, int endY){
			
			Tile startTile = tileGrid.getTile(startX, startY);
			Tile endTile = tileGrid.getTile(endX, endY);
			Figure helpFigure = startTile.getFigure();
			if (helpFigure != null){
				startTile.setFigure(null);
				endTile.setFigure(helpFigure);
			}


	}

}
