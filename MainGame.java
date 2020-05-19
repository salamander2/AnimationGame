package animationGame;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import hsa2.GraphicsConsole;

public class MainGame {
	/* This is the beginning of our animation project for Grade 11 (ICS3U/4C) in 2020 */

	public static void main(String[] args) {
		new MainGame();
	}

	final static int SCRW=1000;
	final static int SCRH=800;
	final static int SLEEP = 1;
	final static int MAXENEMIES = 10;
	final static int MAXBULLETS = 7;
	final static int SHOOTINGDELAY = 100; //milliseconds

	//status constants
	final static int PLAYING = 1;
	final static int QUIT = 2;
	final static int LOSE = 3;
	final static int WIN = 4;

	GraphicsConsole gc = new GraphicsConsole(SCRW,SCRH);

	Player player;
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	ArrayList<Bullet> bulletList = new ArrayList<Bullet>();

	//The game controller
	MainGame(){
		
		setup();

		int status = PLAYING;
		while(status == PLAYING) {

			if (gc.isKeyDown(' ') ) shoot();
			moveBullets();
			checkCollision2();
			movePlayer();
			checkCollision();
			for(Enemy en : enemyList) {
				moveEnemy(en);
			}
			drawGraphics();

			if (gc.getKeyCode() == 27) status = QUIT;  //ESCAPE key now quits. I kept hitting 'Q' by mistake
			if ( checkWin() ) status = WIN;
			if (player.lives < 1) status = LOSE;
			gc.sleep(SLEEP);
		}

		//this will get run when the player Quits (or loses)
		switch(status) {
		case LOSE:
			gc.showDialog("You lose. Thanks for Playing", "The End");
			break;
		case WIN:
			gc.showDialog("You win! Thanks for Playing", "WINNER! (next level coming soon ...");
			break;
		case QUIT:
			gc.showDialog("Thanks for Playing", "The End");
			break;
		}
		gc.close();
	}

	void setup(){
		gc.setAntiAlias(true);
		gc.setLocationRelativeTo(null);	
		gc.setTitle("Space Animation Game");
		gc.setFont(new Font("Arial", Font.PLAIN, 20));
		gc.setBackgroundColor(Color.BLACK);
		//gc.setColor(Color.CYAN);
		player = new Player(80, SCRH/2, 60,40);
		for (int i=0; i<MAXENEMIES; i++){
			enemyList.add( new Enemy(SCRW, SCRH) );
		}
	}

	long lastTime = System.currentTimeMillis();
	void shoot(){
		//this controls how many bullets can be on the screen at one time
		if (bulletList.size() >= MAXBULLETS) return;

		//this controls the firing delay
		long now = System.currentTimeMillis();
		int delay = (int)(now - lastTime);
		if (delay < SHOOTINGDELAY) return;
		lastTime = now;

		bulletList.add(new Bullet(player.x+player.width, player.y));

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

	//if the player wins, return TRUE, else return FALSE.
	//for now, the player wins if he reaches the right side of the screen.
	boolean checkWin() {
		if (player.x + player.width >= SCRW) return true;
		if (enemyList.size() == 0) return true;
		return false;
	}

	void moveBullets() {
		for (Bullet b : bulletList) {
			b.x += b.speed;
		}
		//remove bullets that are offscreen
		for (int i=bulletList.size()-1; i >= 0; i--) {
			Bullet b = bulletList.get(i);
			if (b.x > SCRW) bulletList.remove(i);
		}
	}

	void moveEnemy(Enemy enemy) {
		enemy.y += enemy.speed;
		if (enemy.y + enemy.height < 0 && enemy.speed < 0) enemy.y = SCRH;
		if (enemy.y > SCRH && enemy.speed > 0) enemy.y = 0;
	}

	//This checks the collision between the player and the enemy. If an enemy touches you you lose a life (or health)
	//Later we'll be checking collisions between lasers/bullets and enemies, so I need to come up with a better name for this.	
	void checkCollision() {
		for(Enemy enemy : enemyList) {
			if (player.intersects(enemy)) {
				player.lives--;
				player.x = 100;  // if we don't do this, then the player keeps colliding with the enemy and loses all lives right away.
				//enemy = new Enemy(SCRW, SCRH);	//recreate the enemy with new random settings
				enemyList.remove(enemy);
				break;	//this must happen if we use "remove" while inside a for-each loop
			}
		}
	}

	void checkCollision2() {
		//for (Bullet b: bulletList) {
		for (int i=bulletList.size()-1; i >= 0; i--) {
			Bullet b = bulletList.get(i);
			for (Enemy en : enemyList) {
				if (b.intersects(en)) {
					enemyList.remove(en);
					bulletList.remove(b);
					break;
				}
			}	
		}
	}
	
	void drawGraphics(){
		synchronized(gc){
			gc.clear();
			gc.setColor(Color.YELLOW);
			gc.drawString("Lives = " + player.lives, 50,100);
			gc.setColor(Color.CYAN);
			gc.fillRect(player.x, player.y, player.width, player.height);
			gc.setColor(Color.GREEN);
			for(Enemy enemy : enemyList) {
				gc.fillOval(enemy.x, enemy.y, enemy.width, enemy.height);
			}
			gc.setColor(Color.RED);
			for (Bullet b : bulletList) {
				gc.drawRect(b.x, b.y, b.width, b.height);
			}
		}
		gc.setTitle("" + bulletList.size());
	}


}
