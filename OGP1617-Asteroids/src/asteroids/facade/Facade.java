package asteroids.facade;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.Vector2d;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.ProgramFactory;
import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.part2.CollisionListener;
import asteroids.part3.facade.IFacade;
import asteroids.model.Program;
import asteroids.part3.programs.IProgramFactory;
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
		}catch(AssertionError ex){
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
		}catch(NullPointerException e){
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
		}catch(NullPointerException e){
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
		if(((Entity) object).getBoundaryCollisionPosition() == null)
			return null;
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
			Vector2d result = ((Entity) entity1).getCollisionPosition((Entity) entity2);
			return result == null ? null : result.asArray();
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
			if(world.getNextCollision().getCollisionPoint() == null)
				return null;
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
				world.evolve(dt, collisionListener);
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

	@Override
	public int getNbStudentsInTeam(){
		return 2;
	}

	@Override
	public Set<? extends Asteroid> getWorldAsteroids(World world) throws ModelException{
		return world.getAsteroids();
	}

	@Override
	public void addAsteroidToWorld(World world, Asteroid asteroid) throws ModelException{
		try{
			asteroid.setContainer(world);
			world.addItem(asteroid);
			
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}	
	}

	@Override
	public void removeAsteroidFromWorld(World world, Asteroid asteroid) throws ModelException{
		try{
			asteroid.setContainer(null);
			world.removeItem(asteroid);
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}	
	}

	@Override
	public Set<? extends Planetoid> getWorldPlanetoids(World world) throws ModelException{
		return world.getPlanetoids();
	}

	@Override
	public void addPlanetoidToWorld(World world, Planetoid planetoid) throws ModelException{
		try{
			planetoid.setContainer(world);
			world.addItem(planetoid);
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public void removePlanetoidFromWorld(World world, Planetoid planetoid) throws ModelException{
		try{
			planetoid.setContainer(null);
			world.removeItem(planetoid);
		}catch (IllegalArgumentException e){
			throw new ModelException(e);
		}	
	}

	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity, double yVelocity, double radius) throws ModelException{
		try{
			return new Asteroid(x, y, xVelocity, yVelocity, radius, null);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}catch(AssertionError ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public void terminateAsteroid(Asteroid asteroid) throws ModelException{
		asteroid.terminate();	
	}

	@Override
	public boolean isTerminatedAsteroid(Asteroid asteroid) throws ModelException{
		return asteroid.isTerminated();
	}

	@Override
	public double[] getAsteroidPosition(Asteroid asteroid) throws ModelException{
		return asteroid.getPosition().asArray();
	}

	@Override
	public double[] getAsteroidVelocity(Asteroid asteroid) throws ModelException{
		return asteroid.getVelocity().asArray();
	}

	@Override
	public double getAsteroidRadius(Asteroid asteroid) throws ModelException{
		return asteroid.getRadius();
	}

	@Override
	public double getAsteroidMass(Asteroid asteroid) throws ModelException{
		return asteroid.getMass();
	}

	@Override
	public World getAsteroidWorld(Asteroid asteroid) throws ModelException{
		try{
			return (World) asteroid.getContainer();
		}catch (ClassCastException cce){
			return null;
		}
	}
	
	@Override
	public Planetoid createPlanetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) throws ModelException{
		try{
			return new Planetoid(x, y, xVelocity, yVelocity, radius, null, totalTraveledDistance);
		}catch (IllegalArgumentException ex){
			throw new ModelException(ex);
		}catch(AssertionError ex){
			throw new ModelException(ex);
		}
	}

	@Override
	public void terminatePlanetoid(Planetoid planetoid) throws ModelException{
		planetoid.terminate();
		
	}

	@Override
	public boolean isTerminatedPlanetoid(Planetoid planetoid) throws ModelException{
		return planetoid.isTerminated();
	}

	@Override
	public double[] getPlanetoidPosition(Planetoid planetoid) throws ModelException{
		return planetoid.getPosition().asArray();
	}

	@Override
	public double[] getPlanetoidVelocity(Planetoid planetoid) throws ModelException{
		return planetoid.getVelocity().asArray();
	}

	@Override
	public double getPlanetoidRadius(Planetoid planetoid) throws ModelException{
		return planetoid.getRadius();
	}

	@Override
	public double getPlanetoidMass(Planetoid planetoid) throws ModelException{
		return planetoid.getMass();
	}

	@Override
	public double getPlanetoidTotalTraveledDistance(Planetoid planetoid) throws ModelException{
		return planetoid.getTotalTraveledDistance();
	}

	@Override
	public World getPlanetoidWorld(Planetoid planetoid) throws ModelException{
		try{
			return (World) planetoid.getContainer();
		}catch (ClassCastException cce){
			return null;
		}
	}

	@Override
	public Program getShipProgram(Ship ship) throws ModelException{
		return ship.getProgram();
	}

	@Override
	public void loadProgramOnShip(Ship ship, Program program) throws ModelException{
		ship.setProgram(program);
	}

	@Override
	public List<Object> executeProgram(Ship ship, double dt) throws ModelException{
		try{
			return ship.executeProgram(dt);			
		}catch(ProgramExecutionTimeException e){
			throw new ModelException(e);
		}catch(ExpressionEvaluationException e){
			throw new ModelException(e);
		}
	}

	@Override
	public IProgramFactory<?, ?, ?, ? extends Program> createProgramFactory() throws ModelException{
		return new ProgramFactory();
	}
}
