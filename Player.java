package animationGame;

import java.awt.Rectangle;

class Player extends Rectangle{

	int speed = 3;
	
	Player(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
}
