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
	Enemy enemy;

	//The game controller
	MainGame(){
		
		setup();

		while(gc.getKeyCode() != 'Q') {

			movePlayer();
			moveEnemy();
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
		enemy = new Enemy(SCRW, SCRH);
	}

	//If a key has been pressed, move the player
	void movePlayer(){

		//arrow keys: L=37, R=39, U=38, D=40 . Use these numbers in place of 'A' 'S' ...
		if (gc.isKeyDown('A')) {	//isKeyDown uses keyCodes. Left 
			if (player.x > 0) player.x -= player.speed;
		}
		if (gc.isKeyDown('D')) {	//right
			if (player.x < SCRW-player.width) player.x += player.speed;
		}
		if (gc.isKeyDown('W')) {	//up
			if (player.y > 0) player.y -= player.speed;
		}
		if (gc.isKeyDown('S')) {	//down
			if (player.y < SCRH-player.height) player.y += player.speed;
		}
	}

	void moveEnemy() {
		enemy.y += enemy.speed;
		if (enemy.y + enemy.height < 0 && enemy.speed < 0) enemy.y = SCRH;
		if (enemy.y > SCRH && enemy.speed > 0) enemy.y = 0;
	}
	
	void drawGraphics(){
		synchronized(gc){
			gc.clear();
			gc.setColor(Color.CYAN);
			gc.fillRect(player.x, player.y, player.width, player.height);
			gc.setColor(Color.GREEN);
			gc.fillOval(enemy.x, enemy.y, enemy.width, enemy.height);
		}
	}


}
