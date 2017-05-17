package asteroids.model.programs.expressions;

import asteroids.model.Bullet;

public class BulletEntity extends EntityLiteral{
	
	public BulletEntity(Bullet bullet) {
		this.setBullet(bullet);
	}

	@Override
	public Bullet getValue() {
		return bullet;
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
