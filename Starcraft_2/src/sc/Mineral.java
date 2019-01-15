package sc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;


public class Mineral {
	public static BufferedImage img;
	private Field field;
	private int x;
	private int y;
	public Semaphore mutex;
	private int id;
	
	public Mineral(int x, int y, Field field, int id) {
		this.x = x;
		this.y = y;
		this.field = field;
		this.mutex = new Semaphore(1);
		this.id = id;
	}
	
	static {
		try {
			img = ImageIO.read(new File("res/mineral.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getImg() {
		return img;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void startGather() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stopGather() {
		mutex.release();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
