import java.util.ArrayList;
import java.util.Random;

public class Fractal implements Runnable{

	public final int HEIGHT;
	public final int WIDTH;
	public final int SCALE;
	public final int ITERATIONS;
	public final int SPARSITY;

	private Particle[][] field;
	private ArrayList<Cluster> clusters;

	private Random r;

	public Fractal(int height, int width, int scale, int sparsity, int iterations){
		HEIGHT = height;
		WIDTH = width;
		SCALE = scale;
		ITERATIONS = iterations;
		SPARSITY = sparsity;

		field = new Particle[height][width];
		clusters = new ArrayList<Cluster>();

		r = new Random();

		initField();
		initParticles();
		initClusters();
	}

	public void run(){
		begin();
	}

	public void begin(){
		for(int i = 0; i < ITERATIONS; i++){
			for(Cluster c: clusters){
				c.walk();
			}

			if(clusters.size() == 1){
				generateParticles();
			}

			for(int j = 0; j < HEIGHT; j++){
				for(int k = 0; k < WIDTH; k++){
					field[j][k].setCluster(null);
				}
			}
			
			clusters = new ArrayList<Cluster>();
			initClusters();
		}
	}

	public Particle particleAt(int i, int j){
		return field[i][j];
	}

	private void generateParticles(){
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				if(!field[i][j].isAlive()){
					int lifeProbability = r.nextInt(40);
					if(lifeProbability == 0){
						field[i][j].wake();
					}
				}
			}
		}
	}

	private void initField(){
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				field[i][j] = new Particle(i, j, this);
			}
		}   
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				field[i][j].setNeighbors();
			}
		}
	}

	private void initParticles(){
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				int lifeProbability = r.nextInt(SPARSITY);
				if(lifeProbability == 0){
					field[i][j].wake();
				}
			}
		}
	}

	private void initClusters(){
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				Cluster c = new Cluster(this);
				addToCluster(field[i][j], c);
				if(c.size() > 0){
					clusters.add(c);
				}
			}
		}
	}

	private void addToCluster(Particle p, Cluster c){
		if(!p.inCluster() && p.isAlive()){
			p.setCluster(c);
			c.addParticle(p);
			for(int i = 0; i < 4; i++){
				if(p.neighborAt(i) != null){
					addToCluster(p.neighborAt(i), c);
				}
			}
		}
	}

}

