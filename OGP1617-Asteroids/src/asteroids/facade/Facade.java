package asteroids.facade;

import java.util.Collection;
import java.util.Set;

import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.Vector2d;
import asteroids.model.World;
import asteroids.part2.CollisionListener;
import asteroids.part2.facade.IFacade;
import asteroids.util.ModelException;

public class Facade implements IFacade{

	@Override
	public double[] getShipPosition(Ship ship) throws ModelException{
		return ship.getPosition().asArray();
	}

	@Override
	public double[] getShipVelocity(Ship ship) throws ModelException{
		return ship.getVelocity().asArray();
	}

	@Override
	public double getShipRadius(Ship ship) throws ModelException{
		return ship.getRadius();
	}

	@Override
	public double getShipOrientation(Ship ship) throws ModelException{
		return ship.getOrientation();
	}

	@Override
	public void turn(Ship ship, double angle) throws ModelException{
		try{
			ship.turn(angle);
		}catch (AssertionError ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException{
		try{
			return ship1.getDistanceBetween(ship2);
		}catch (NullPointerException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException{
		try{
			return ship1.overlaps(ship2);
		}catch (NullPointerException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException{
		try{
			return ship1.getTimeToCollision(ship2);
		}catch (NullPointerException ex){
			throw new ModelException(ex);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException{
		try{
			return ship1.getCollisionPosition(ship2).asArray();
		}catch (NullPointerException ex){
			throw new ModelException(ex);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction, double mass) throws ModelException{
		try{
			return new Ship(x, y, xVelocity, yVelocity, direction, radius, mass);
		}catch (AssertionError ex){
			throw new ModelException(ex);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public void terminateShip(Ship ship) throws ModelException{
		ship.terminate();
	}

	@Override
	public boolean isTerminatedShip(Ship ship) throws ModelException{
		return ship.isTerminated();
	}

	@Override
	public double getShipMass(Ship ship) throws ModelException{
		return ship.getTotalMass();
	}

	@Override
	public World getShipWorld(Ship ship) throws ModelException{
		try{
			return (World) ship.getContainer();
		}catch (ClassCastException cce){
			return null;
		}
	}

	@Override
	public boolean isShipThrusterActive(Ship ship) throws ModelException{
		return ship.getThrusterStatus();
	}

	@Override
	public void setThrusterActive(Ship ship, boolean active) throws ModelException{
		if(active)
			ship.thrustOn();
		else
			ship.thrustOff();
	}

	@Override
	public double getShipAcceleration(Ship ship) throws ModelException{
		return ship.getAcceleration();
	}

	@Override
	public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius) throws ModelException{
		try{
			return new Bullet(x, y, xVelocity, yVelocity, radius, null);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public void terminateBullet(Bullet bullet) throws ModelException{
		try{
			bullet.terminate();
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public boolean isTerminatedBullet(Bullet bullet) throws ModelException{
		return bullet.isTerminated();
	}

	@Override
	public double[] getBulletPosition(Bullet bullet) throws ModelException{
		return bullet.getPosition().asArray();
	}

	@Override
	public double[] getBulletVelocity(Bullet bullet) throws ModelException{
		return bullet.getVelocity().asArray();
	}

	@Override
	public double getBulletRadius(Bullet bullet) throws ModelException{
		return bullet.getRadius();
	}

	@Override
	public double getBulletMass(Bullet bullet) throws ModelException{
		return bullet.getMass();
	}

	@Override
	public World getBulletWorld(Bullet bullet) throws ModelException{
		try{
			return (World) bullet.getContainer();
		}catch (ClassCastException e){
			return null;
		}
	}

	@Override
	public Ship getBulletShip(Bullet bullet) throws ModelException{
		try{
			return (Ship) bullet.getContainer();
		}catch (ClassCastException e){
			return null;
		}
	}

	@Override
	public Ship getBulletSource(Bullet bullet) throws ModelException{
		return bullet.getSource();
	}

	@Override
	public World createWorld(double width, double height) throws ModelException{
		return new World(width, height);
	}

	@Override
	public void terminateWorld(World world) throws ModelException{
		try{
			world.terminate();
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public boolean isTerminatedWorld(World world) throws ModelException{
		return world.isTerminated();
	}

	@Override
	public double[] getWorldSize(World world) throws ModelException{
		return new double[] { world.getWidth(), world.getHeight() };
	}

	@Override
	public Set<? extends Ship> getWorldShips(World world) throws ModelException{
		return world.getShips();
	}

	@Override
	public Set<? extends Bullet> getWorldBullets(World world) throws ModelException{
		return world.getBullets();
	}

	@Override
	public void addShipToWorld(World world, Ship ship) throws ModelException{
		try{
			ship.setContainer(world);
			world.addItem(ship);
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public void removeShipFromWorld(World world, Ship ship) throws ModelException{
		try{
			ship.setContainer(null);
			world.removeItem(ship);
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public void addBulletToWorld(World world, Bullet bullet) throws ModelException{
		try{
			bullet.setContainer(world);
			world.addItem(bullet);
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException{
		try{
			bullet.setContainer(null);
			world.removeItem(bullet);
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException{
		return ship.getBullets();
	}

	@Override
	public int getNbBulletsOnShip(Ship ship) throws ModelException{
		return ship.getNbItems();
	}

	@Override
	public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException{
		try{
			ship.loadBullet(bullet);
		}catch (NullPointerException ex){
			throw new ModelException(ex);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException{
		try{
			for(Bullet bullet : bullets)
				ship.loadBullet(bullet);
		}catch (NullPointerException ex){
			throw new ModelException(ex);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException{
		try{
			bullet.setContainer(null);
			ship.removeItem(bullet);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public void fireBullet(Ship ship) throws ModelException{
		ship.fireBullet();
	}

	@Override
	public double getTimeCollisionBoundary(Object object) throws ModelException{
		return ((Entity) object).getTimeToBoundaryCollision();
	}

	@Override
	public double[] getPositionCollisionBoundary(Object object) throws ModelException{
		return ((Entity) object).getBoundaryCollisionPosition().asArray();
	}

	@Override
	public double getTimeCollisionEntity(Object entity1, Object entity2) throws ModelException{
		try{
			return ((Entity) entity1).getTimeToCollision((Entity) entity2);
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}catch (NullPointerException e){
			throw new ModelException(e);
		}
	}

	@Override
	public double[] getPositionCollisionEntity(Object entity1, Object entity2) throws ModelException{
		try{
			return ((Entity) entity1).getCollisionPosition((Entity) entity2).asArray();
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}catch (NullPointerException e){
			throw new ModelException(e);
		}
	}

	@Override
	public double getTimeNextCollision(World world) throws ModelException{
		try{
			return world.getNextCollision().getTimeToCollision();
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public double[] getPositionNextCollision(World world) throws ModelException{
		try{
			return world.getNextCollision().getCollisionPoint().asArray();
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException{
		try{
			if(collisionListener == null)
				world.evolve(dt);
			else{
//				synchronized(collisionListener){
					world.evolve(dt, collisionListener);
//				}
			}
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public Object getEntityAt(World world, double x, double y) throws ModelException{
		return world.getEntityAt(new Vector2d(x, y));
	}

	@Override
	public Set<? extends Object> getEntities(World world) throws ModelException{
		return world.getAllEntities();
	}
}
