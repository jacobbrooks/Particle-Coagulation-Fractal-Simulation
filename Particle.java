import java.util.Random;

public class Particle{

	private boolean alive;

	private int row;
	private int col;

	private Fractal f;
	private Particle[] neighbors;
	private Cluster c;

	private Random r;

	public Particle(int row, int col, Fractal f){
		alive = false;
		this.row = row;
		this.col = col;
		this.f = f;
		neighbors = new Particle[4];
		r = new Random();
	}

	public void setCluster(Cluster c){
		this.c = c;
	}

	public Cluster getCluster(){
		return c;
	}

	public boolean inCluster(){
		if(c == null){
			return false;
		}
		return true;
	}

	public void setNeighbors(){
		if(row - 1 >= 0){
			neighbors[0] = f.particleAt(row - 1, col);
		}
		if(row + 1 < f.HEIGHT){
			neighbors[1] = f.particleAt(row + 1, col);
		}
		if(col - 1 >= 0){
			neighbors[2] = f.particleAt(row, col - 1);
		}
		if(col + 1 < f.WIDTH){
			neighbors[3] = f.particleAt(row, col + 1);
		}
	}

	public Particle neighborAt(int i){
		return neighbors[i];
	}

	public void wake(){
		alive = true;
	}

	public void kill(){
		alive = false;
	}

	public boolean isAlive(){
		return alive;
	}

	public int row(){
		return row;
	}

	public int col(){
		return col;
	}

}