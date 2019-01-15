package sc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Field extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 112737L;
	private ArrayList<Svc> svcs =  new ArrayList<Svc>();
	private ArrayList<Mineral> minerals =  new ArrayList<Mineral>();
	private ArrayList<Center> centers =  new ArrayList<Center>();
	private Random rand = new Random(123456);
	public int XMAX = 10;
	public int YMAX = 10;
	public int step = 64;
	
	public static BufferedImage ground;
	
	public static int X_SVC_position = 6;
	public static int Y_SVC_position = 3;
	
	static {
		try {
			ground = ImageIO.read(new File("res/ground.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Field() {
		generateSvc(4, Joueurs.J1);
		generateSvc(4, Joueurs.J2);
		generateCenters(1);
	}
	
	public void generateCenters(int n) {
		Center center1 = new Center(0,0,this,0, Joueurs.J1);
		centers.add(center1);
		Center center2 = new Center(7,7,this,1, Joueurs.J2);
		centers.add(center2);
	}
	
	public void generateSvc(int n, Joueurs joueur) {
		if (joueur.equals(Joueurs.J1)) 
		{
			for (int i = 0; i < n; i++) {
				Svc svc = new Svc(0, 0, this, i, joueur);
				//Svc svc = new Svc(rand.nextInt(XMAX),rand.nextInt(YMAX),this,i);
				svcs.add(svc);
				new Thread(svc).start();
			} 
		}
		
		else {
			for (int i = 0; i < n; i++) {
				Svc svc = new Svc(9, 9, this, i, joueur);
				//Svc svc = new Svc(rand.nextInt(XMAX),rand.nextInt(YMAX),this,i);
				svcs.add(svc);
				new Thread(svc).start();
			} 
		}
	}
	
	public void generateMinerals(int n) {
		for (int i = 0; i < n; i++) {
			Mineral flower = new Mineral(rand.nextInt(XMAX),rand.nextInt(YMAX),this,i);
			minerals.add(flower);
		}
	}
	
	public void paintComponent(Graphics g) {
	  super.paintComponent(g);
	  
	  Graphics2D g2 = (Graphics2D) g;
	  
	  for (int i = 0; i < XMAX; i++) {
		for (int j = 0; j < YMAX; j++) {
			g2.drawImage(ground, i*step, j*step, null);
		}
	  }
	  
	  g2.setColor(Color.GREEN);
	  
	  // draw centers
	  for (Center center : centers) {
		 try {
			g2.drawImage(Center.getImg(), center.getX()*step, center.getY()*step, null);
		} catch (IllegalPlayerTypeArgument e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 g2.drawString(center.getNbDeposit()+"", center.getX()*step+15, center.getY()*step+15);
	  }
	  
	  // draw SVC
	  for (Svc svc : svcs) {
		 g2.drawImage(svc.getImg(), svc.getX()*step, svc.getY()*step, null);
		 g2.drawString(svc.getId()+"", svc.getX()*step+25, svc.getY()*step+10);
	  }
	}
	
	public void checkSvcOnMineral(Svc svc) {
		if(svc.isFull())
			return;
		
		for (Mineral flower : minerals) {
			if((flower.getX() == svc.getX()) && (flower.getY() == svc.getY())) {
				svc.gather(flower);
				break;
			}
		}
	}
	
	public Center getOneCenter() {
		return this.centers.get(rand.nextInt(centers.size()));
	}
	
	public Mineral getOneMineral() {
		return this.minerals.get(rand.nextInt(minerals.size()));
	}
	
	public void checkSvcOnCenter(Svc svc) {
		if(!svc.isFull()) {
			return;
		}
		
		for(Center center: centers) {
			if((center.getX() == svc.getX()) && (center.getY() == svc.getY())) {
				svc.deposit(center);
			}
			if((center.getX() == svc.getX()+1) && (center.getY() == svc.getY())) {
				svc.deposit(center);
			}
			if((center.getX() == svc.getX()) && (center.getY()+1 == svc.getY())) {
				svc.deposit(center);
			}
			if((center.getX()+1 == svc.getX()) && (center.getY()+1 == svc.getY())) {
				svc.deposit(center);
			}
		}
	}
}
