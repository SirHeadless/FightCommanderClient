package boot;

import static helper.Artist.HEIGHT;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.json.JSONException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import background.Tile;
import background.TileGrid;
import background.TileType;
import lombok.extern.java.Log;


import movement.Movement;
import services.ServerConnector;

@Log
public class InputHandler extends Thread {

	private TileType[] types;
	private TileGrid tileGrid;
	private int index;
	private Tile prevSelected;
	private boolean prevMouseState;
	private ServerConnector serverConnector;
	
	private boolean mousetester = false;
	private int mousetestercounter = 0;

	public InputHandler(TileGrid tileGrid) {
		this.tileGrid = tileGrid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Sand;
		this.types[2] = TileType.Water;
		this.index = 0;

		try {
			serverConnector = new ServerConnector();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {

		while (true) {
			update();
		}
	}

	public void setTile() {
		System.out.println(String.format("set Clicked Tile [%s,%s]", Mouse.getX(), Mouse.getY()));
		tileGrid.setTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor(HEIGHT - Mouse.getY() - 1) / 64,
				types[index]);
	}

	public Tile getTile() {
		System.out.println(String.format("get Clicked Tile [%s,%s]", Mouse.getX(), Mouse.getY()));
		return tileGrid.getTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor(HEIGHT - Mouse.getY() - 1) / 64);
	}

	public void update() {
		mouseClick();
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}
		}
	}

	private void mouseClick() {
		
//		Mouse.getEventButton()
		long timeStart = 0L;
		if (Mouse.isButtonDown(0) && !prevMouseState ) {
			log.info("MouseClick method executed");
			mousetester = true;
			
	        timeStart = System.currentTimeMillis();
	        System.out.println("Mouse was clicked:" + timeStart + " Milisekunden");	


			prepareField();
			onCLick();
			final long timeEnd = System.currentTimeMillis(); 
	        System.out.println("Verlaufszeit der Schleife: " + (timeEnd - timeStart) + " Milisekunden"); 
	        mousetestercounter += 1;
	        System.out.println("Mousetestercounter:" + mousetestercounter);
			System.out.println("-----------------------------------------------------------------------");
		}
		
		prevMouseState = Mouse.isButtonDown(0);
		
		if (mousetester && !prevMouseState){
			System.out.println(!prevMouseState); 
			mousetester = false;
			
		}

//		if (Mouse.isButtonDown(0)) {
//			prevMouseState = Mouse.isButtonDown(0);
//			final long timeEnd2 = System.currentTimeMillis(); 
//			SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
//			String current_time_str = time_formatter.format(System.currentTimeMillis());
//	        System.out.println(current_time_str + "Buttton ist gedrueckt: " + (timeEnd2 - timeStart) + " Milisekunden"); 
//			System.out.println("-----------------------------------------------------------------------");
//		}
	}

	private void prepareField() {
		log.info("Field gets prepared");
		tileGrid.setAllColorsNormal();
	}

	private void onCLick() {

		log.info(String.format("Evaluate click action with clicked tile %s and previous clicked %s",
				getTile().getPositionString(),
				prevSelected != null ? prevSelected.getPositionString() : "not slected"));

		if (getTile() == prevSelected) {
			log.info(String.format("Clicked tile %s is equal to previous selected %s", getTile().getPositionString(),
					prevSelected.getPositionString()));
			return;
		}

		tileGrid.setAllColorsNormal();

		if (madeFigureAction()) {
			log.info("Action for Figure was made");
			prevSelected = null;
			tileGrid.setAllColorsNormal();
			return;
		}

		if (getTile().getFigure() != null) {
			log.info(String.format("Figure on clicked tile %s", getTile().getPositionString()));

			showFigureActions();
			prevSelected = getTile();
			return;
		}

		if (getTile().getBuilding() != null) {
			log.info(String.format("Building on clicked tile %s", getTile().getPositionString()));

			showBuildingActions();
			prevSelected = null;
			return;
		}

		// log.info("Click will be validated");
		// if (getTile() != prevSelected) {
		// log.info(String.format("Clicked tile %s is UNEQUAL to previous
		// selected %s", getTile().getPositionString(),
		// prevSelected.getPositionString()));
		// if(prevSelected == null){
		// log.info("Previous selected Tile is null");
		// prevSelected = getTile();
		// if (getTile() != null) {
		// log.info("Show possible moves");
		// Movement.showPossibleMoves(tileGrid, (int) getTile().getPosX(), (int)
		// getTile().getPosY());
		// };
		// }else {
		// log.info(prevSelected.getPositionString() + " was selected before");
		// if (prevSelected.getFigure() != null && makeMove()){
		// log.info("Send request for Figure" + prevSelected.getPositionString()
		// + " to move");
		// prevSelected = null;
		// tileGrid.setAllColorsNormal();
		// } else {
		// log.info(String.format("On clicked tile %s is no figure",
		// getTile().getPositionString(), prevSelected.getPositionString()));
		// prevSelected = getTile();
		// if (getTile().getFigure() != null){
		// Movement.showPossibleMoves(tileGrid, (int) getTile().getPosX(), (int)
		// getTile().getPosY());
		// System.out.println("Figure is on the Fields");
		// // Zeichne Umriss um Figure
		// // show possible movements
		// } else if (getTile().getBuilding() != null){
		// System.out.println("Building is on the Fields");
		// // Zeige "Action" des Gebaeudes and
		// } else {
		// System.out.println("Nothing is on the Fields");
		// tileGrid.setAllColorsNormal();
		// }
		// }
		// }
		// }
	}

	private void showBuildingActions() {
		// TODO Auto-generated method stub

	}

	private void showFigureActions() {
		log.info(String.format("Show actions for figure %s", getTile().getPositionString()));
		Movement.showPossibleMoves(tileGrid, (int) getTile().getPosX(), (int) getTile().getPosY());

	}

	private boolean madeFigureAction() {
		if (prevSelected == null || prevSelected.getFigure() == null) {
			return false;
		}
		if (makeMove()) {
			return true;
		}
		log.info(String.format("No action for figure %s to tile %s possible", prevSelected.getPositionString(),
				getTile().getPositionString()));
		return false;

	}

	public boolean makeMove() {
		if (Arrays.asList(
				Movement.showPossibleMoves(tileGrid, (int) prevSelected.getPosX(), (int) prevSelected.getPosY()))
				.contains(getTile())) {
			// Here needs the connector to be added to check if the move is
			// valid or not!
			try {
				log.info(String.format("Request server for movement from %s to %s", prevSelected.getPositionString(),
						getTile().getPositionString()));
				serverConnector.sendMovementRequest(prevSelected.getPosX(), prevSelected.getPosY(), getTile().getPosX(),
						getTile().getPosY());
			} catch (JSONException | IOException e) {
				log.info(String.format("Request to move from %s to %s was not possible",
						prevSelected.getPositionString(), getTile().getPositionString()));
				e.printStackTrace();
			}
		}
		return false;
	}

	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}
}
