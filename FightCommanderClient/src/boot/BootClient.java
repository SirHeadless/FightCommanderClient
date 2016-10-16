package boot;

import static helper.Artist.*;

import org.lwjgl.opengl.Display;

import background.TileGrid;
import background.TileType;
import figures.Enemy;
import figures.Figure;
import figures.FirstWarrior;

public class BootClient {
	
	int[][] map = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
	};

	public TileGrid tileGrid;
	
	private ServerCommandExecutor serverCommandExecutor;
	private InputHandler inputHandler;
	
	public static void main(String[] args) {
		new BootClient();
	}
	
	@SuppressWarnings("deprecation")
	public BootClient() {

		BeginSession();	
		
	
		tileGrid = new TileGrid(map);
		tileGrid.setTile(3,3, TileType.Sand);
		Enemy e = new Enemy(QuickLoad("enemy"), tileGrid.getTile(10, 10), 64, 64, 100, 2);
		Figure Fw  = new FirstWarrior();
		Figure Fw2  = new FirstWarrior();
		tileGrid.setFigure(Fw, 5, 5);
		tileGrid.setFigure(Fw2, 10, 10);
		
		
		serverCommandExecutor = new ServerCommandExecutor(tileGrid);
		inputHandler = new InputHandler(tileGrid);
		serverCommandExecutor.start();
		inputHandler.start();
			
		while(!Display.isCloseRequested()) {

			getInput();
			
			
			
			tileGrid.Draw();
			e.Draw();
			
//			testMoves(5, 5);

//			DrawTransQuad(2*64,2*64,64,64);
//			DrawQuadTex(tileGrid.getTile(2,2).getTexture(),null, null, 2*64,2*64,64,64,Color.Blue);
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		serverCommandExecutor.interrupt();
		inputHandler.interrupt();
		
	}
	

	
	private void getInput() {
		
		
	}



}
