package sc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;

public class Center {
	public static BufferedImage img1;
	public static BufferedImage img2;
	private Field field;
	private int x;
	private int y;
	private int id;
	private int nbDeposit;
	public Semaphore sema;
	private static Joueurs j;
	
	public Center(int x, int y, Field field, int id, Joueurs j) {
		this.x = x;
		this.y = y;
		this.field = field;
		this.id = id;
		this.sema = new Semaphore(2);
		this.j=j;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	static {
		try {
			img1 = ImageIO.read(new File("res/center1.png"));
			img2 = ImageIO.read(new File("res/center2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getImg() throws IllegalPlayerTypeArgument {
		if(j.equals(Joueurs.J1)){return img1;}
		else{return img2;}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getNbDeposit() {
		return nbDeposit;
	}

	public void setNbDeposit(int nbDeposit) {
		this.nbDeposit = nbDeposit;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void startDeposit() {
		try {
			this.sema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stopDeposit() {
		nbDeposit+=1;
		this.sema.release();
	}
}