package movement;

import java.util.ArrayList;
import java.util.Arrays;


import background.Color;
import background.Tile;
import background.TileGrid;
import helper.MoveHelper;

public class Movement {

	private static TileGrid tileGrid;

	private static int tileGridWidth;
	private static int tileGridHeight;

	private static int[][] stepGrid;


	/*
	 * Returns a Matrix that for the possible moves. Lay the middle of this
	 * Matrix over the parameter x and y and all values >= 0 are possible moves.
	 */
	private static int[][] getAllPossibleMovements(TileGrid tileGrid, int x, int y) {

		// setTileGrid(tileGrid);

		if (tileGrid.getTile(x, y).getFigure() == null) {
			int[][] back = { { Integer.MIN_VALUE } };
			return back;
		}

		int maxSteps = tileGrid.getTile(x, y).getFigure().getMaxMovements();

		String figureName = tileGrid.getTile(x, y).getFigure().getName();

		initGrid((maxSteps * 2) + 1, (maxSteps * 2) + 1);
		try {
			setStepGrid(x, y, maxSteps, maxSteps, maxSteps, figureName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stepGrid;

	}

	/*
	 * Defines a global variable int[][] where the middle point has the number
	 * of maximumSteps and the other Fields are calculated how many steps are
	 * left to go to an other Field. All values <0 are not available.
	 */
	private static void setStepGrid(int tileGridx, int tileGridy, int helperx, int helpery, int StepNumber,
			String fighterName) throws Exception {
		stepGrid[helperx][helpery] = Math.max(stepGrid[helperx][helpery], StepNumber);

		if (StepNumber > 0) {
			if (tileGridx < tileGridWidth - 1) {
				setStepGrid(tileGridx + 1, tileGridy, helperx + 1, helpery,
						StepNumber - MoveHelper.getNeededSteps(fighterName,
								tileGrid.getTile(tileGridx + 1, tileGridy).getType().getTextureName()),
						fighterName);
			}
			if (tileGridy < tileGridHeight - 1) {
				setStepGrid(tileGridx, tileGridy + 1, helperx, helpery + 1,
						StepNumber - MoveHelper.getNeededSteps(fighterName,
								tileGrid.getTile(tileGridx, tileGridy + 1).getType().getTextureName()),
						fighterName);
			}
			if (tileGridx > 0) {
				setStepGrid(tileGridx - 1, tileGridy, helperx - 1, helpery,
						StepNumber - MoveHelper.getNeededSteps(fighterName,
								tileGrid.getTile(tileGridx - 1, tileGridy).getType().getTextureName()),
						fighterName);
			}
			if (tileGridy > 0) {
				setStepGrid(tileGridx, tileGridy - 1, helperx, helpery - 1,
						StepNumber - MoveHelper.getNeededSteps(fighterName,
								tileGrid.getTile(tileGridx, tileGridy - 1).getType().getTextureName()),
						fighterName);
			}

		}
	}

	public static void initGrid(int sizex, int sizey) {
		int[][] helperGrid = new int[sizex][sizey];
		for (int[] row : helperGrid) {
			Arrays.fill(row, Integer.MIN_VALUE);
		}
		stepGrid = helperGrid;
	}

	public static TileGrid getTileGrid() {
		return tileGrid;
	}

	public static void setTileGrid(TileGrid tileGrid) {
		Movement.tileGridWidth = tileGrid.map.length;
		Movement.tileGridHeight = tileGrid.map[0].length;
		Movement.tileGrid = tileGrid;
	}

	/*
	 * Takes the TileGrid and the coordinates of a Tile with a Figure and
	 * returns all possible Tiles where the Figure can Move and colors them blue
	 */
	public static Tile[] showPossibleMoves(TileGrid tileGrid, int x, int y) {
		setTileGrid(tileGrid);

		ArrayList<Tile> possibleTiles = new ArrayList<Tile>();
		int[][] movementMatrix = Movement.getAllPossibleMovements(tileGrid, x, y);
		int mid = 0;
		if (tileGrid.getTile(x, y).getFigure() != null) {
			mid = tileGrid.getTile(x, y).getFigure().getMaxMovements();
		}

		for (int i = 0; i < movementMatrix.length; i++) {
			for (int j = 0; j < movementMatrix[i].length; j++) {
				int tileGridPosX = x + j - mid;
				int tileGridPosY = y + i - mid;
				if (movementMatrix[j][i] >= 0 && tileGrid.getTile(tileGridPosX, tileGridPosY).getFigure() == null) {
					tileGrid.getTile(tileGridPosX, tileGridPosY).setColor(Color.Blue);
					possibleTiles.add(tileGrid.getTile(tileGridPosX, tileGridPosY));
				}
			}
		}
		return possibleTiles.toArray(new Tile[0]);

	}

}
