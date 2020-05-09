package animationGame;

import java.awt.Rectangle;

class Bullet extends Rectangle{

	int speed = 3;
	
	Bullet(int x, int y){
		this.x = x;
		this.y = y;
		width = 10;
		height = 2;
	}
}
