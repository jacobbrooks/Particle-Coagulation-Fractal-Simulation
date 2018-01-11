import javax.swing.JFrame;

public class Main{

	public static void main(String[] args){

		int height = Integer.parseInt(args[0]);
		int width = Integer.parseInt(args[1]);
		int scale = Integer.parseInt(args[2]);
		int sparsity = Integer.parseInt(args[3]);
		int iterations = Integer.parseInt(args[4]);

		Fractal f = new Fractal(height, width, scale, sparsity, iterations);

		Display d = new Display(f);
		d.frame().setResizable(false);
		d.frame().setTitle("Fractal");
		d.frame().add(d);
		d.frame().pack();
		d.frame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d.frame().setLocationRelativeTo(null);
		d.frame().setVisible(true);
		d.start();

	}

}