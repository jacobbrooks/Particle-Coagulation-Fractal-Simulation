import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int scale;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private Fractal f;	
	private Screen screen;
	
	private BufferedImage image;
	private int[] pixels;
	
	public Display(Fractal f) {
		this.f = f;

		width = f.WIDTH;
		height = f.HEIGHT;
		scale = f.SCALE;

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height, f);
		frame = new JFrame();
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(running) {
			render();
		}
	}
	
	public JFrame frame(){
		return frame;
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		screen.render();
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixelAt(i);
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
}
