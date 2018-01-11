import java.awt.Color;
import java.util.ArrayList;

public class Screen {
	
	private int width, height;
	private int[] pixels;
	private Thread t;
	private Fractal f;
	
	public Screen(int width, int height, Fractal f) {
		this.width = width;
		this.height = height;
		this.f = f;

		pixels = new int[width * height];

		t = new Thread(f);
		t.start();
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void render() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(f.particleAt(y, x).isAlive()){
					pixels[x + (y * width)] = 0xFFFFFF;
				}else{
					pixels[x + (y * width)] = 0x000000;
				}
			}
		}
	}

	public int pixelAt(int i){
		return pixels[i];
	}
}
