package animationGame;

import java.awt.Rectangle;

class Enemy extends Rectangle{

	int speed = 3;
	
	Enemy(int SCRW, int SCRH){
		//x is a random location between 60% and 90% of the window width
		this.x = (int)((Math.random()*0.3*SCRW) + 0.6 * SCRW);
		this.y = SCRH/2;
		width = height = 30;
		
		//set a random speed from 1-3:
		int speed = (int)(Math.random()*3 +1);
		if (Math.random() < 0.5) speed*=-1;	//50-50 chance of speed being negative

		//set the instance variable
		this.speed = speed;

		//DEBUG
		//System.out.println("Enemy speed=" + speed);
	}
}
