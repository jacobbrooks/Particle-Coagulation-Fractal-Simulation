import java.util.ArrayList;
import java.util.Random;

public class Cluster{

	private ArrayList<Particle> cluster;
	private Fractal f;
	private Random r;

	public Cluster(Fractal f){
		cluster = new ArrayList<Particle>();
		this.f = f;
		r = new Random();
	}

	public void addParticle(Particle p){
		cluster.add(p);
	}

	public int size(){
		return cluster.size();
	}

	public Particle particleAt(int i){
		return cluster.get(i);
	}

	public void walk(){
		int direction = r.nextInt(4);
		if(direction == 0){
			if(getMinRowIndex() - 1 >= 0){
				if(canWalk(0)){
					moveCluster(0);
				}
			}
		}else if(direction == 1){
			if(getMaxRowIndex() + 1 < f.HEIGHT){
				if(canWalk(1)){
					moveCluster(1);
				}
			}
		}else if(direction == 2){
			if(getMinColIndex() - 1 >= 0){
				if(canWalk(2)){
					moveCluster(2);
				}
			}
		}else if(getMaxColIndex() + 1 < f.WIDTH){
			if(canWalk(3)){
				moveCluster(3);
			}
		}
	}

	private void moveCluster(int direction){
		if(direction == 0){
			doMove(-1, 0);
		}else if(direction == 1){
			doMove(1, 0);
		}else if(direction == 2){
			doMove(0, -1);
		}else{
			doMove(0, 1);
		}
	}

	private void doMove(int rowOffset, int colOffset){
		for(Particle p: cluster){
			int row = p.row();
			int col = p.col();
			f.particleAt(row, col).kill();
		}
		for(Particle p: cluster){
			int row = p.row();
			int col = p.col();
			f.particleAt(row + rowOffset, col + colOffset).wake();
		}
	}

	private boolean canWalk(int direction){
		if(direction == 0){
			return !collision(-1, 0);
		}else if(direction == 1){
			return !collision(1, 0);
		}else if(direction == 2){
			return !collision(0, -1);
		}
		return !collision(0, 1);
	}

	private boolean collision(int rowOffset, int colOffset){
		for(Particle p: cluster){
			int row = p.row();
			int col = p.col();
			if(f.particleAt(row + rowOffset, col + colOffset).isAlive() 
				&& f.particleAt(row + rowOffset, col + colOffset).getCluster() != this){
				return true;
			}
		}
		return false;
	}

	private int getMinRowIndex(){
		int min = f.HEIGHT;
		for(Particle p: cluster){
			if(p.row() < min){
				min = p.row();
			}
		}
		return min;
	}

	private int getMinColIndex(){
		int min = f.WIDTH;
		for(Particle p: cluster){
			if(p.col() < min){
				min = p.col();
			}
		}
		return min;
	}

	private int getMaxRowIndex(){
		int max = 0;
		for(Particle p: cluster){
			if(p.row() > max){
				max = p.row();
			}
		}
		return max;
	}

	private int getMaxColIndex(){
		int max = 0;
		for(Particle p: cluster){
			if(p.col() > max){
				max = p.col();
			}
		}
		return max;
	}

}