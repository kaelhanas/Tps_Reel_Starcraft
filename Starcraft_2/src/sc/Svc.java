package sc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Svc implements Runnable {
	public BufferedImage img;
	public static BufferedImage imgSvcEmpty1;
	public static BufferedImage imgSvcEmpty2;
	public static BufferedImage imgSvcFull;
	private Field field;
	private Random rand = new Random();
	private int x;
	private int y;
	private int id;
	private boolean isFull;
	private Mineral target = null;
	private Joueurs j;

	static {
		try {
			imgSvcEmpty1 = ImageIO.read(new File("res/marine1.png"));
			imgSvcEmpty2 = ImageIO.read(new File("res/marine2.png"));
			imgSvcFull = ImageIO.read(new File("res/svc-full.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Svc(int x, int y, Field field, int id, Joueurs j) {
		this.x = x;
		this.y = y;
		this.field = field;
		this.id = id;
		if (j.equals(Joueurs.J1)) {
			this.img = imgSvcEmpty1;
		} else if (j.equals(Joueurs.J2)) {
			this.img = imgSvcEmpty2;
		}
		this.isFull = false;
	}

	@Override
	public void run() {
		try {
			while (true) {
				// svc behavior
				Thread.sleep(500);
				//tryToMoveWithTarget();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public boolean isFull() {
		return this.isFull;
	}

	private void tryToMoveWithTarget() {
		if (!isFull) {
			if (this.target == null) {
				target = this.field.getOneMineral();
			}

			int choice = rand.nextInt(2);
			if (choice == 0 && x != target.getX()) {
				if (x < target.getX() && x + 1 < field.XMAX)
					x++;
				else if (x - 1 >= 0)
					x--;

			} else if (choice == 1) {
				if (y < target.getY() && y + 1 < field.YMAX)
					y++;
				else if (y - 1 >= 0)
					y--;
			}
		} else {
			int choice = rand.nextInt(2);
			if (choice == 0 && x != field.getOneCenter().getX()) {
				if (x < field.getOneCenter().getX() && x + 1 < field.XMAX)
					x++;
				else if (x - 1 >= 0)
					x--;

			} else if (choice == 1) {
				if (y < field.getOneCenter().getY() && y + 1 < field.YMAX)
					y++;
				else if (y - 1 >= 0)
					y--;
			}
		}

		// refresh the field
		field.repaint();

		// check mineral
		field.checkSvcOnMineral(this);

		// check center
		field.checkSvcOnCenter(this);
	}

	private void tryToMoveRandom() {
		int choice = rand.nextInt(4);
		if (choice == 0) {
			if (x + 1 < field.XMAX)
				x++;

		} else if (choice == 1) {
			if (y + 1 < field.YMAX)
				y++;

		} else if (choice == 2) {
			if (x > 0)
				x--;

		} else if (choice == 3) {
			if (y > 0)
				y--;
		}

		// check mineral
		field.checkSvcOnMineral(this);

		// check center
		field.checkSvcOnCenter(this);
	}

	public BufferedImage getImg() {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void gather(Mineral mineral) {
		System.out.println("I'm svc " + id + ", I want to gather mineral " + mineral.getId());
		mineral.startGather();
		System.out.println("I'm svc " + id + ", I start to gather mineral " + mineral.getId());

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		isFull = true;
		img = imgSvcFull;
		target = null;
		System.out.println("I'm svc " + id + ", I stop to gather mineral " + mineral.getId());
		mineral.stopGather();
	}

	public void deposit(Center center) {
		System.out.println("I'm svc " + id + ", I want to deposit " + center.getId());
		center.startDeposit();
		System.out.println("I'm svc " + id + ", I start to deposit on center " + center.getId());

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		isFull = false;
		if (j.equals(Joueurs.J1)) {
			this.img = imgSvcEmpty1;
		} else if (j.equals(Joueurs.J2)) {
			this.img = imgSvcEmpty2;
		}

		System.out.println("I'm svc " + id + ", I stop to deposit on center " + center.getId());
		center.stopDeposit();
	}
}
