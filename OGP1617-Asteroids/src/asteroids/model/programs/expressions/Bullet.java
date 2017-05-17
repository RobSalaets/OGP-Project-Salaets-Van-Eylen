package asteroids.model.programs.expressions;

public class Bullet extends EntityLiteral{
	
	public Bullet(Bullet bullet) {
		this.setBullet(bullet);
	}

	@Override
	public Bullet getValue() {
		return this;
	}
	
	public Bullet bullet;
	
	public void setBullet(Bullet bullet){
		if(bullet == null)
			throw new IllegalArgumentException();
		this.bullet = bullet;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
	
}
