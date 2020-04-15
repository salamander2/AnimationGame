package animationGame;

import java.awt.Color;
import hsa2.GraphicsConsole;

public class MainGame {
	/* This is the beginning of our animation project for Grade 11 (ICS3U/4C) in 2020 */

	public static void main(String[] args) {
		new MainGame();
	}

	final static int SCRW=1000;
	final static int SCRH=800;
	final static int SLEEP = 1;
	GraphicsConsole gc = new GraphicsConsole(SCRW,SCRH);

	Player player;

	//The game controller
	MainGame(){
		
		setup();

		while(gc.getKeyCode() != 'Q') {

			movePlayer();

			drawGraphics();

			gc.sleep(SLEEP);
		}

		//this will get run when the player Quits (or loses)
		gc.showDialog("Thanks for Playing", "The End");
		gc.close();
	}

	void setup(){
		gc.setAntiAlias(true);
		gc.setLocationRelativeTo(null);	
		gc.setTitle("Space Animation Game");
		gc.setBackgroundColor(Color.BLACK);
		gc.setColor(Color.CYAN);
		player = new Player(80, SCRH/2, 60,40);
	}

	//If a key has been pressed, move the player
	void movePlayer(){

		if (gc.isKeyDown(37)) {	//isKeyDown uses keyCodes. Left arrow
			if (player.x > 0) player.x -= player.speed;
		}
		if (gc.isKeyDown(39)) {	//right
			if (player.x < SCRW-player.width) player.x += player.speed;
		}
		if (gc.isKeyDown(38)) {	//up
			if (player.y> 0) player.y -= player.speed;
		}
		if (gc.isKeyDown(40)) {	//down
			if (player.y < SCRH-player.height) player.y += player.speed;
		}
	}
	
	void drawGraphics(){
//		gc.setColor(Color.CYAN);
		synchronized(gc){
			gc.clear();
			gc.fillRect(player.x, player.y, player.width, player.height);
		}
	}


}
