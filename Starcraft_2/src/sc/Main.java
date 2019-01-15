package sc;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Starcraft");
		frame.setSize(640, 665);
		frame.setResizable(false);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Field field = new Field();
		field.setFocusable(true);
		frame.setContentPane(field);
		frame.setVisible(true);
	}
}
