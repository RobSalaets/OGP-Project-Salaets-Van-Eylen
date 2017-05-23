package asteroids.tests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.CollisionData;
import asteroids.model.CollisionType;
import asteroids.model.Entity;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.Vector2d;
import asteroids.model.World;
import asteroids.part3.facade.IFacade;
import asteroids.facade.Facade;
import asteroids.util.ModelException;

public class Part3ExtraTestSuite {

	private static final double EPSILON = 0.0001;

	IFacade facade;

	@Before
	public void setUp() {
		facade = new Facade();
	}

	private static void assertEqualsVector(Vector2d target, Vector2d actual, double range) {
		assertEquals(target.getX(), actual.getX(), range);
		assertEquals(target.getY(), actual.getY(), range);
	}

	@Test
	public void testCreateWorld() throws ModelException {
		World world = facade.createWorld(1000, 800);
		assertEquals(1000, facade.getWorldSize(world)[0], EPSILON);
		assertEquals(800, facade.getWorldSize(world)[1], EPSILON);
		assertTrue(facade.getWorldShips(world).isEmpty());
		assertTrue(facade.getWorldBullets(world).isEmpty());
	}

	@Test
	public void testLoadBulletOnShipOverlappingBullets() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(100, 120, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(130, 110, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		assertEquals(2, facade.getNbBulletsOnShip(ship));
	}

	@Test
	public void testEvolveShipWithActiveThruster() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship = facade.createShip(100, 120, 10, 0, 50, Math.PI, 1.1E18);
		ship = new Ship(100, 120, 10, 0, Math.PI, 50, 1.1E18, 1.1e21);
		facade.addShipToWorld(world, ship);
		facade.setThrusterActive(ship, true);
		assertEquals(1000.0, facade.getShipAcceleration(ship), EPSILON);
		assertTrue(facade.isShipThrusterActive(ship));
		facade.evolve(world, 1, null);
		assertEquals(-990, facade.getShipVelocity(ship)[0], EPSILON);
		assertEquals(0, facade.getShipVelocity(ship)[1], EPSILON);
	}

	@Test
	public void testCreateEntity() throws ModelException {
		Bullet entity1 = facade.createBullet(100.0, 200.0, 10.0, -10.0, 20.0);
		Ship entity2 = facade.createShip(200.0, 300.0, 10.0, -10.0, 20.0, 0.0, 8e16);
		assertNotNull(entity1);
		assertNotNull(entity2);
		double[] position = facade.getBulletPosition(entity1);
		assertNotNull(position);
		double[] position2 = facade.getShipPosition(entity2);
		assertNotNull(position2);
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
		assertEquals(200, position2[0], EPSILON);
		assertEquals(300, position2[1], EPSILON);
		double[] velocity = facade.getBulletVelocity(entity1);
		assertNotNull(velocity);
		assertEquals(10, velocity[0], EPSILON);
		assertEquals(-10, velocity[1], EPSILON);
		double[] velocity2 = facade.getShipVelocity(entity2);
		assertNotNull(velocity2);
		assertEquals(10, velocity2[0], EPSILON);
		assertEquals(-10, velocity2[1], EPSILON);
		assertEquals(20, facade.getBulletRadius(entity1), EPSILON);
		assertEquals(20, facade.getShipRadius(entity2), EPSILON);
		assertEquals(Bullet.BULLET_MASS_DENSITY * 4.0 / 3.0 * Math.PI * 8000, facade.getBulletMass(entity1), EPSILON);
		assertEquals(8e16, facade.getShipMass(entity2), EPSILON);
		assertEquals(Bullet.BULLET_MASS_DENSITY, entity1.getMassDensity(), EPSILON);
		assertEquals(8e16 / (4.0 / 3.0 * Math.PI * 8000), entity2.getMassDensity(), EPSILON);
	}
	//
	// @Test(expected = ModelException.class)
	// public void testCreateEntityXIsNan() throws ModelException{
	// facade.createEntity(Double.NaN, 200, 10, -10, 20, Math.PI);
	// }
	//
	// @Test(expected = ModelException.class)
	// public void testCreateEntityYIsNan() throws ModelException{
	// facade.createEntity(-2500, Double.NaN, 10, -10, 20, Math.PI);
	// }

	@Test
	public void testCreateEntityExceedingVelocity() throws ModelException {
		Bullet entity = facade.createBullet(100.0, 200.0, 300001.0, -10.0, 20.0);
		assertNotNull(entity);
		double[] velocity = facade.getBulletVelocity(entity);
		assertNotNull(velocity);
		assertEquals(0, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testCreateEntityRadiusNegative() throws ModelException {
		facade.createShip(200.0, 300.0, 10.0, -10.0, -200.0, 0.0, 8e16);
	}

	@Test(expected = ModelException.class)
	public void testCreateEntityRadiusInsufficient() throws ModelException {
		facade.createShip(200.0, 300.0, 10.0, -10.0, 9.0, 0.0, 8e16);
	}

	@Test
	public void testCreateEntityMassTooLow() throws ModelException {
		Ship ship = facade.createShip(200.0, 300.0, 10.0, -10.0, 500.0, 0.0, 1e8);
		assertNotNull(ship);
		assertEquals(ship.getLowestMassDensity() * 4.0 / 3.0 * Math.PI * Math.pow(500, 3), ship.getMass(), EPSILON);

	}

	@Test
	public void testCreateShip() throws ModelException {
		Ship ship = new Ship(200.0, 300.0, 10.0, -10.0, 0.0, 20.0, 8e16);
		Ship ship2 = new Ship(200.0, 300.0, 10.0, -10.0, 0.0, 20.0, 8e16, null, 1);
		assertNotNull(ship);
		assertNotNull(ship2);
		assertEquals(Ship.DEFAULT_THRUST_FORCE, ship.getThrustForce(), EPSILON);
		assertEquals(1, ship2.getThrustForce(), EPSILON);
		assertEquals(ship.getNbItems(), 0);
		assertEquals(ship2.getNbItems(), 0);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipOrientationExceeds() throws ModelException {
		facade.createShip(200.0, 300.0, 10.0, -10.0, 20.0, 7.0, 8e16);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipOrientationNegative() throws ModelException {
		facade.createShip(200.0, 300.0, 10.0, -10.0, 20.0, -1.0, 8e16);
	}

	@Test
	public void testShipTotalMass() throws ModelException {
		Ship ship = facade.createShip(200.0, 300.0, 10.0, -10.0, 20.0, 3.0, 20e20);
		Bullet bullet = new Bullet(200, 300, 0, 0, 16, null);
		assertNotNull(ship);
		assertNotNull(bullet);
		facade.loadBulletOnShip(ship, bullet);
		assertEquals(20e20 + bullet.getMass(), ship.getTotalMass(), EPSILON);
	}

	@Test
	public void testShipTotalMassAfterFiringABullet() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(200.0, 300.0, 10.0, -10.0, 20.0, 3.0, 20e20);
		facade.addShipToWorld(world, ship);
		Bullet bullet = new Bullet(200, 300, 0, 0, 16, null);
		assertNotNull(ship);
		assertNotNull(bullet);
		facade.loadBulletOnShip(ship, bullet);
		assertEquals(20e20 + bullet.getMass(), ship.getTotalMass(), EPSILON);
		facade.fireBullet(ship);
		assertEquals(20e20, ship.getTotalMass(), EPSILON);
	}

	@Test
	public void testShipAcceleration() throws ModelException {
		Ship ship = facade.createShip(200.0, 300.0, 10.0, -10.0, 20.0, 3.0, 20e20);
		Bullet bullet = new Bullet(200, 300, 0, 0, 16, null);
		assertNotNull(ship);
		assertNotNull(bullet);
		facade.loadBulletOnShip(ship, bullet);
		ship.thrustOn();
		assertEquals(Ship.DEFAULT_THRUST_FORCE / (20e20 + bullet.getMass()), ship.getAcceleration(), EPSILON);
		ship.thrustOff();
		assertEquals(0.0, ship.getAcceleration(), EPSILON);
	}

	@Test
	public void testCreateBullet() throws ModelException {
		Ship ship = facade.createShip(0, 0, 0, 0, 200, 0.0, 20e18);
		Bullet bullet = facade.createBullet(1, 1, 1, 1, 10);
		Bullet bullet2 = new Bullet(1, 1, 1, 1, 10, ship, 4);
		assertNotNull(ship);
		assertNotNull(bullet);
		assertNotNull(bullet2);
		assertEquals(Bullet.DEFAULT_MAX_BOUNDARY_COLLISIONS, bullet.getMaxBoundaryCollisions());
		assertEquals(4, bullet2.getMaxBoundaryCollisions());
		assertEquals(ship, bullet2.getSource());
		assertEqualsVector(bullet2.getPosition(), Vector2d.ZERO, EPSILON);
		assertEqualsVector(bullet2.getVelocity(), Vector2d.ZERO, EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testCreateBulletNegativeMaxBCollisions() throws ModelException {
		try {
			new Bullet(1, 1, 1, 1, 10, null, -4);
		} catch (AssertionError e) {
			throw new ModelException(e);
		}
	}

	@Test
	public void testIncrementBulletBCollisionCount() throws ModelException {
		Bullet bullet = new Bullet(0, 0, 0, 0, 15, null, 5);
		assertNotNull(bullet);
		bullet.incrementBoundaryCollisionCount();
		assertEquals(1, bullet.getBoundaryCollisionCount());
	}

	@Test
	public void testMove() throws ModelException {
		Ship ship = facade.createShip(500, 500, 0, 123, 200, 0.0, 20e18);
		ship.move(1);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(500, position[0], EPSILON);
		assertEquals(623, position[1], EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testMoveNegativeTimeDelta() throws ModelException {
		Ship ship = facade.createShip(500, 500, 0, 0, 200, 0.0, 20e18);
		try {
			ship.move(-1);
		} catch (IllegalArgumentException e) {
			throw new ModelException(e);
		}
	}

	@Test
	public void testTurn() throws ModelException {
		Ship ship = facade.createShip(500, 500, 0, 0, 200, 0.0, 20e18);
		facade.turn(ship, Math.PI / 4.0);
		assertEquals(Math.PI / 4.0, facade.getShipOrientation(ship), EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testTurnAngleExceeds() throws ModelException {
		Ship ship = facade.createShip(500, 500, 0, 0, 200, 0.0, 20e18);
		facade.turn(ship, 20);
	}

	@Test
	public void testThrust() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 0, 0, 200, 0.0, 20e18);
		facade.addShipToWorld(world, ship);
		ship.thrustOn();
		facade.evolve(world, 1, null);
		double[] velocity = facade.getShipVelocity(ship);
		assertNotNull(velocity);
		assertEquals(ship.getAcceleration(), velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testThrustNegativeAmount() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 0, 0, 200, 0.0, 20e18);
		facade.addShipToWorld(world, ship);
		ship.thrustOn();
		facade.evolve(world, -1, null);
	}

	@Test
	public void testThrustVelocityExceeds() throws ModelException {
		Ship ship = facade.createShip(500, 500, 299999, 0, 200, 0.0, 5e14);
		World world = facade.createWorld(1e13, 1000);
		facade.addShipToWorld(world, ship);
		ship.thrustOn();
		facade.evolve(world, 100, null);
		double[] velocity = facade.getShipVelocity(ship);
		assertNotNull(velocity);
		assertEquals(ship.getMaxVelocity(), velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}

	@Test
	public void testDistanceBetween() throws ModelException {
		Ship ship1 = facade.createShip(0, 0, 0, 0, 200, 0.0, 20e18);
		Ship ship2 = facade.createShip(400, 0, 0, 0, 100, 0.0, 20e18);
		assertEquals(100, facade.getDistanceBetween(ship1, ship2), EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testDistanceBetweenNull() throws ModelException {
		Ship ship1 = facade.createShip(500, 500, 0, 0, 200, 0.0, 20e18);
		Ship ship2 = null;
		facade.getDistanceBetween(ship1, ship2);
	}

	@Test
	public void testOverlaps() throws ModelException {
		Ship ship1 = facade.createShip(0, 0, 0, 0, 200, 0.0, 20e18);
		Ship ship2 = facade.createShip(400, 0, 0, 0, 500, 0.0, 20e18);
		assertTrue(facade.overlap(ship1, ship2));
	}

	@Test(expected = ModelException.class)
	public void testOverlapsNull() throws ModelException {
		Ship ship1 = facade.createShip(500, 500, 0, 0, 200, 0.0, 20e18);
		Ship ship2 = null;
		facade.overlap(ship1, ship2);
	}

	@Test
	public void testOverlapsWithAnyEntitiesWithActualOverlaps() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(500, 500, 10, 5, 50);
		facade.addBulletToWorld(world, bullet1);
		assertTrue(world.overlapsWithAnyEntity(bullet2).size() > 0);
	}

	@Test
	public void testOverlapsWithAnyEntitiesWithoutOverlaps() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 0, 10, 100, 0, 1.1E18);
		Ship ship1 = facade.createShip(200, 200, 0, 10, 100, 0, 1.1E18);
		Ship ship2 = facade.createShip(800, 800, 0, 10, 50, 0, 1.1E18);
		Ship ship3 = facade.createShip(300, 300, 0, 10, 20, 0, 1.1E18);
		facade.addShipToWorld(world, ship);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.addShipToWorld(world, ship3);
		assertTrue(world.overlapsWithAnyEntity(ship).size() == 0);
	}

	@Test
	public void testOverlapsWithAnyEntitiesWithoutAssociationWithTheSameWorld() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		World world2 = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 0, 10, 100, 0, 1.1E18);
		Ship ship2 = facade.createShip(200, 200, 0, 10, 100, 0, 1.1E18);
		facade.addShipToWorld(world, ship);
		facade.addShipToWorld(world2, ship2);
		assertTrue(world.overlapsWithAnyEntity(ship2).size() == 0);
		assertTrue(world2.overlapsWithAnyEntity(ship).size() == 0);
	}
	
	@Test
	public void testOverlapsWithAnyEntitiesFromPositionAndRadius() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		World world2 = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 0, 10, 100, 0, 1.1E18);
		Ship ship2 = facade.createShip(200, 200, 0, 10, 100, 0, 1.1E18);
		facade.addShipToWorld(world, ship);
		facade.addShipToWorld(world2, ship2);
		assertTrue(world.overlapsWithAnyEntity(new Vector2d(500, 505), 30).size() == 1);
		assertTrue(world2.overlapsWithAnyEntity(new Vector2d(200, 505), 300).size() == 1);
	}

	@Test
	public void testCollisionInterShip() throws ModelException {
		World world = facade.createWorld(10000, 10000);
		Ship ship1 = new Ship(500, 500, 100, 0, 0, 100, 20e20, world, 20e20);
		Ship ship2 = new Ship(1000, 500, -200, 0, Math.PI, 100, 20e20, world, 20e20);
		CollisionData cd = world.getNextEntityCollision();
		assertNotNull(cd);
		assertEquals(CollisionType.INTER_ENTITY, cd.getCollisionType());
		assertEquals(new Vector2d(700, 500), cd.getCollisionPoint());
		assertEquals(1.0, cd.getTimeToCollision(), EPSILON);
		assertTrue(cd.getColliders().contains(ship2));
		assertTrue(cd.getColliders().contains(ship1));
		assertEquals(world.getNextCollision(), cd);
		assertEquals(new Vector2d(700, 500), ship1.getCollisionPosition(ship2));
		assertEquals(1.0, ship2.getTimeToCollision(ship1), EPSILON);
		world.evolve(2);
		assertEqualsVector(new Vector2d(400, 500), ship1.getPosition(), EPSILON);
		assertEqualsVector(new Vector2d(900, 500), ship2.getPosition(), EPSILON);
		assertEqualsVector(new Vector2d(-200, 0), ship1.getVelocity(), EPSILON);
		assertEqualsVector(new Vector2d(100, 0), ship2.getVelocity(), EPSILON);
	}

	@Test
	public void testCollisionInterShipWithThrusters() throws ModelException {
		World world = facade.createWorld(10000, 10000);
		Ship ship1 = new Ship(200, 500, 100, 0, 0, 100, 20e20, world, 100e20);
		Ship ship2 = new Ship(1000, 500, -200, 0, Math.PI, 100, 20e20, world, 100e20);
		ship1.thrustOn();
		ship2.thrustOn();
		world.evolve(1);
		assertEqualsVector(new Vector2d(300, 500), ship1.getPosition(), EPSILON);
		assertEqualsVector(new Vector2d(800, 500), ship2.getPosition(), EPSILON);
		assertEqualsVector(new Vector2d(105, 0), ship1.getVelocity(), EPSILON);
		assertEqualsVector(new Vector2d(-205, 0), ship2.getVelocity(), EPSILON);
		CollisionData cd = world.getNextEntityCollision();
		assertNotNull(cd);
		assertEquals(CollisionType.INTER_ENTITY, cd.getCollisionType());
		assertEqualsVector(new Vector2d(501.61290323, 500), cd.getCollisionPoint(), EPSILON);
		assertEquals(0.967741935, cd.getTimeToCollision(), EPSILON);
		assertTrue(cd.getColliders().contains(ship2));
		assertTrue(cd.getColliders().contains(ship1));
		assertEquals(world.getNextCollision(), cd);
		assertEqualsVector(new Vector2d(501.61290323, 500), ship1.getCollisionPosition(ship2), EPSILON);
		assertEquals(0.967741935, ship2.getTimeToCollision(ship1), EPSILON);
	}

	@Test
	public void testCollisionInterEntity() throws ModelException {
		World world = facade.createWorld(10000, 10000);
		Ship ship1 = new Ship(200, 500, 100, 0, 0, 100, 20e20, world, 100e20);
		Ship ship2 = new Ship(1000, 500, -150, 0, Math.PI, 100, 20e20, world, 100e20);
		Bullet bullet = new Bullet(605, 500, -5, 0, 5, world);
		world.evolve(1);
		assertEqualsVector(new Vector2d(300, 500), ship1.getPosition(), EPSILON);
		assertEqualsVector(new Vector2d(850, 500), ship2.getPosition(), EPSILON);
		assertEqualsVector(new Vector2d(600, 500), bullet.getPosition(), EPSILON);
		CollisionData cd = world.getNextEntityCollision();
		assertNotNull(cd);
		assertEquals(CollisionType.INTER_ENTITY, cd.getCollisionType());
		assertEqualsVector(new Vector2d(600, 500), cd.getCollisionPoint(), EPSILON);
		assertEquals(1.0, cd.getTimeToCollision(), EPSILON);
		assertEquals(world.getNextCollision(), cd);
		assertTrue(cd.getColliders().contains(ship2));
		assertTrue(cd.getColliders().contains(bullet));
		assertEqualsVector(new Vector2d(600, 500), bullet.getCollisionPosition(ship2), EPSILON);
		assertEquals(1.0, ship2.getTimeToCollision(bullet), EPSILON);
		world.evolve(2.0);
		assertTrue(ship2.isTerminated());
		assertTrue(bullet.isTerminated());
		assertEquals(CollisionType.BOUNDARY, world.getNextCollision().getCollisionType());
	}

	@Test
	public void testCollisionBoundary() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = new Ship(200, 500, 0, 100, 0, 100, 20e20, world, 100e20);
		Bullet bullet = new Bullet(500, 500, 0, 200, 10, world);
		CollisionData cd = world.getNextBoundaryCollision();
		assertNotNull(cd);
		assertEquals(CollisionType.BOUNDARY, cd.getCollisionType());
		assertEqualsVector(new Vector2d(500, 1000), cd.getCollisionPoint(), EPSILON);
		assertEquals(2.45, cd.getTimeToCollision(), EPSILON);
		assertEquals(world.getNextCollision(), cd);
		assertTrue(cd.getColliders().contains(bullet));
		assertEqualsVector(new Vector2d(500, 1000), bullet.getBoundaryCollisionPosition(), EPSILON);
		assertEquals(2.45, bullet.getTimeToBoundaryCollision(), EPSILON);
		assertEqualsVector(new Vector2d(200, 1000), ship.getBoundaryCollisionPosition(), EPSILON);
		assertEquals(4.0, ship.getTimeToBoundaryCollision(), EPSILON);
		world.evolve(2.5 + 4.95 * (Bullet.DEFAULT_MAX_BOUNDARY_COLLISIONS - 1));
		assertTrue(bullet.isTerminated());
	}

	@Test
	public void testCollisionUndefined() throws ModelException {
		World world1 = facade.createWorld(1000, 1000);
		Ship stationaryShip = new Ship(500, 500, 0, 0, 0, 100, 20e20, world1, 100e20);
		new Bullet(500, 700, 450, 0, 50, world1);
		new Bullet(200, 500, -50, 0, 50, world1);
		Bullet unBoundedBullet = new Bullet(2000, -5000, 500, 0, 50, null);
		Bullet loadedBullet = new Bullet(0, 0, 500, 0, 50, new Ship(500, 105, 5, 0, 0, 100, 20e20, world1, 100e20));
		assertEquals(CollisionData.UNDEFINED_COLLISION, stationaryShip.getBoundaryCollisionData());
		assertEquals(CollisionData.UNDEFINED_COLLISION, world1.getNextEntityCollision());
		assertEquals(CollisionData.UNDEFINED_COLLISION, unBoundedBullet.getBoundaryCollisionData());
		assertEquals(CollisionData.UNDEFINED_COLLISION, loadedBullet.getBoundaryCollisionData());
	}

	@Test
	public void testCollisionBulletSource() throws ModelException {
		World world1 = facade.createWorld(1000, 1000);
		Ship ship = new Ship(500, 500, 0, 0, 0, 100, 20e20, world1, 100e20);
		Bullet bullet = new Bullet(0, 0, 450, 0, 50, ship);
		assertEquals(world1.getNextCollision(), CollisionData.UNDEFINED_COLLISION);
		ship.fireBullet();
		assertTrue(world1.hasAsItem(bullet));
		world1.evolve(world1.getNextBoundaryCollision().getTimeToCollision() + EPSILON);
		CollisionData cd = world1.getNextCollision();
		assertTrue(cd.getColliders().contains(ship));
		assertTrue(cd.getColliders().contains(bullet));
		assertEquals(ship, bullet.getSource());
		world1.evolve(cd.getTimeToCollision() + EPSILON);
		assertEquals(0, bullet.getBoundaryCollisionCount());
		assertTrue(!world1.hasAsItem(bullet));
		assertTrue(ship.hasAsItem(bullet));
		assertTrue(!ship.isTerminated());
		assertTrue(!bullet.isTerminated());
	}

	@Test
	public void testTimeToCollisionNoCollision() throws ModelException {
		Ship ship1 = facade.createShip(200.0, 300.0, 10.0, -10.0, 100.0, 0.0, 8e18);
		Ship ship2 = facade.createShip(400.0, 300.0, 10.0, -10.0, 100.0, 0.0, 8e18);
		World world = facade.createWorld(5000, 5000);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		double timeDuration = facade.getTimeCollisionEntity(ship1, ship2);
		assertEquals(Double.POSITIVE_INFINITY, timeDuration, EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testTimeToCollisionNullCase() throws ModelException {
		Ship ship1 = facade.createShip(200.0, 300.0, 10.0, -10.0, 200.0, 0.0, 8e16);
		Bullet bullet1 = null;
		facade.getTimeCollisionEntity(ship1, bullet1);
	}

	@Test(expected = ModelException.class)
	public void testTimeToCollisionOverlappingShips() throws ModelException {
		Ship ship1 = facade.createShip(200.0, 300.0, 10.0, -10.0, 200.0, 0.0, 8e16);
		Ship ship2 = facade.createShip(205.0, 300.0, 10.0, -10.0, 200.0, 0.0, 8e16);
		facade.getTimeCollisionEntity(ship1, ship2);
	}

	@Test(expected = ModelException.class)
	public void testCollisionPositionNullCase() throws ModelException {
		Ship ship1 = facade.createShip(200.0, 300.0, 10.0, -10.0, 200.0, 0.0, 8e16);
		Ship ship2 = null;
		facade.getPositionCollisionEntity(ship1, ship2);
	}

	@Test
	public void testCollisionPositionNoCollision() throws ModelException {
		Ship ship1 = facade.createShip(200.0, 300.0, 10.0, -10.0, 100.0, 0.0, 8e18);
		Ship ship2 = facade.createShip(500.0, 300.0, 10.0, -10.0, 100.0, 0.0, 8e18);
		World world = facade.createWorld(5000, 5000);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		assertNull(facade.getPositionCollisionEntity(ship1, ship2));
	}

	@Test(expected = ModelException.class)
	public void testCollisionPositionOverlappingShips() throws ModelException {
		Ship ship1 = facade.createShip(300.0, 300.0, 10.0, -10.0, 200.0, 0.0, 8e18);
		Ship ship2 = facade.createShip(500.0, 300.0, 10.0, -10.0, 200.0, 0.0, 8e18);
		facade.getCollisionPosition(ship1, ship2);
	}

	@Test
	public void testLoadNormalBullets() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(300, 300, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(250, 250, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		facade.loadBulletOnShip(ship, bullet3);
		facade.loadBulletOnShip(ship, bullet4);
		assertEquals(4, facade.getNbBulletsOnShip(ship));
	}

	@Test(expected = ModelException.class)
	public void testLoadInvalidBullets() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Ship ship2 = facade.createShip(200, 300, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(400, 400, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		facade.loadBulletOnShip(ship, bullet3);
		facade.loadBulletOnShip(ship, bullet4);
		facade.loadBulletOnShip(ship2, bullet1);
		facade.loadBulletOnShip(ship2, bullet2);
	}

	@Test
	public void testLoadListOfBullets() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(100, 120, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(400, 400, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		Set<Bullet> bullets = new HashSet<Bullet>();
		bullets.add(bullet1);
		bullets.add(bullet2);
		bullets.add(bullet3);
		bullets.add(bullet4);
		facade.loadBulletsOnShip(ship, bullets);
		assertEquals(4, facade.getNbBulletsOnShip(ship));
		assertEquals(ship, bullet1.getContainer());
		assertEquals(ship, bullet2.getContainer());
		assertEquals(ship, bullet3.getContainer());
		assertEquals(ship, bullet4.getContainer());
	}

	@Test
	public void testFireBulletWhenShipIsNotAPartOffAWorld() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(300, 300, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(250, 250, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		Set<Bullet> bullets = new HashSet<Bullet>();
		bullets.add(bullet1);
		bullets.add(bullet2);
		bullets.add(bullet3);
		bullets.add(bullet4);
		facade.loadBulletsOnShip(ship, bullets);
		facade.fireBullet(ship);
		assertEquals(ship, bullet1.getContainer());
		assertEquals(ship, bullet2.getContainer());
		assertEquals(ship, bullet3.getContainer());
		assertEquals(ship, bullet4.getContainer());
	}

	@Test
	public void testFireBulletWhenShipHasNoBullets() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 100, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Set<Bullet> bullets = new HashSet<Bullet>();
		facade.loadBulletsOnShip(ship, bullets);
		int oldNbItemsInWorld = world.getNbItems();
		facade.fireBullet(ship);
		assertEquals(oldNbItemsInWorld, world.getNbItems());
	}

	@Test
	public void testFireBulletWhenFiredBulletIsOutsideWorldBoundaries() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(900, 100, 10, 5, 80, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(900, 100, 10, 5, 50);
		facade.loadBulletOnShip(ship, bullet1);
		facade.fireBullet(ship);
		assertTrue(bullet1.isTerminated());
	}

	@Test
	public void testFireBulletWhichResultsInImmediateCollisionWithAnotherShip() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 50, 0, 1.0E20);
		Ship ship2 = facade.createShip(600, 500, 10, 5, 45, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		facade.addShipToWorld(world, ship2);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 20);
		facade.loadBulletOnShip(ship, bullet1);
		assertTrue(ship.hasAsItem(bullet1));
		assertTrue(world.overlapsWithAnyEntity(bullet1).size() > 0);
		facade.fireBullet(ship);
		assertTrue(bullet1.isTerminated());
		assertTrue(ship2.isTerminated());
	}

	@Test
	public void testFireBullet() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 50, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 20);
		facade.loadBulletOnShip(ship, bullet1);
		facade.fireBullet(ship);
		assertEquals(world, bullet1.getContainer());
		assertTrue(world.hasAsItem(bullet1));
	}

	@Test
	public void testFireBulletMultipleBullets() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 200, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(450, 450, 10, 5, 20);
		Bullet bullet2 = facade.createBullet(480, 480, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(440, 440, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(500, 500, 10, 5, 30);
		Set<Bullet> bullets = new HashSet<Bullet>();
		bullets.add(bullet1);
		bullets.add(bullet2);
		bullets.add(bullet3);
		bullets.add(bullet4);
		facade.loadBulletsOnShip(ship, bullets);
		facade.fireBullet(ship);
		facade.fireBullet(ship);
		facade.fireBullet(ship);
		facade.fireBullet(ship);
		for (Bullet b : bullets)
			assertTrue(b.isTerminated());
	}

	@Test
	public void testGetEntities_Ships_Bullets() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 50, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(200, 200, 10, 5, 20);
		Bullet bullet2 = facade.createBullet(700, 700, 10, 5, 30);
		facade.addBulletToWorld(world, bullet1);
		facade.addBulletToWorld(world, bullet2);
		Set<Bullet> bullets = new HashSet<Bullet>();
		bullets.add(bullet1);
		bullets.add(bullet2);
		Set<Ship> ships = new HashSet<Ship>();
		ships.add(ship);
		Set<Entity> entities = new HashSet<Entity>();
		entities.add(ship);
		entities.add(bullet1);
		entities.add(bullet2);
		assertEquals(bullets, world.getBullets());
		assertEquals(ships, world.getShips());
		assertEquals(entities, world.getAllEntities());
	}

	@Test
	public void testGetEntities_Ships_Bullets_WhenBulletsAreLoaded() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 50, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(200, 200, 10, 5, 20);
		Bullet bullet2 = facade.createBullet(700, 700, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(500, 500, 10, 5, 20);
		Bullet bullet4 = facade.createBullet(500, 500, 10, 5, 30);
		facade.addBulletToWorld(world, bullet1);
		facade.addBulletToWorld(world, bullet2);
		facade.loadBulletOnShip(ship, bullet3);
		facade.loadBulletOnShip(ship, bullet4);
		Set<Bullet> bullets = new HashSet<Bullet>();
		bullets.add(bullet1);
		bullets.add(bullet2);
		Set<Ship> ships = new HashSet<Ship>();
		ships.add(ship);
		Set<Entity> entities = new HashSet<Entity>();
		entities.add(ship);
		entities.add(bullet1);
		entities.add(bullet2);
		assertEquals(bullets, world.getBullets());
		assertEquals(ships, world.getShips());
		assertEquals(entities, world.getAllEntities());
	}

	@Test
	public void testContainerAssociationByConstructor() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Ship shipContainer = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		assertTrue(worldContainer.hasProperItems());
		assertTrue(shipContainer.hasProperItems());
		Bullet bulletItem = new Bullet(200, 200, 200, 200, 5, worldContainer);
		Bullet bulletItem2 = new Bullet(200, 200, 200, 200, 5, shipContainer);
		Ship shipItem = new Ship(800, 800, 800, 800, 3, 30, 12e18, worldContainer, 1);
		assertTrue(worldContainer.hasProperItems());
		assertTrue(shipContainer.hasProperItems());
		assertTrue(worldContainer.hasAsItem(bulletItem));
		assertTrue(worldContainer.hasAsItem(shipItem));
		assertTrue(shipContainer.hasAsItem(bulletItem2));
	}

	@Test
	public void testContainerAddItem() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Ship shipContainer = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		Bullet bulletItem = new Bullet(400, 400, 200, 200, 5, null);
		Bullet bulletItem2 = new Bullet(450, 450, 200, 200, 5, null);
		Ship shipItem = new Ship(800, 800, 800, 800, 3, 30, 12e18, null, 1);
		facade.addShipToWorld(worldContainer, shipItem);
		facade.loadBulletOnShip(shipContainer, bulletItem);
		facade.loadBulletOnShip(shipContainer, bulletItem2);
		assertTrue(worldContainer.hasProperItems());
		assertTrue(shipContainer.hasProperItems());
		assertTrue(shipContainer.hasAsItem(bulletItem));
		assertTrue(worldContainer.hasAsItem(shipItem));
		assertTrue(shipContainer.hasAsItem(bulletItem2));
	}

	@Test(expected = ModelException.class)
	public void testContainerAddItemNull() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		try {
			worldContainer.addItem(null);
		} catch (IllegalArgumentException ex) {
			throw new ModelException(ex);
		}
	}

	@Test(expected = ModelException.class)
	public void testContainerAddItemTerminated() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Bullet bullet = new Bullet(200, 200, 200, 200, 5, worldContainer);
		bullet.terminate();
		facade.addBulletToWorld(worldContainer, bullet);
	}

	@Test(expected = ModelException.class)
	public void testContainerAddItemWorldTerminated() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Bullet bullet = new Bullet(200, 200, 200, 200, 5, worldContainer);
		worldContainer.terminate();
		facade.addBulletToWorld(worldContainer, bullet);
	}

	@Test(expected = ModelException.class)
	public void testContainerAddItemOutOfBounds() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Bullet bullet = new Bullet(-1, -2, 200, 200, 5, null);
		facade.addBulletToWorld(worldContainer, bullet);
	}

	@Test(expected = ModelException.class)
	public void testContainerAddItemInvalidType() throws ModelException {
		Ship shipContainer = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		Ship shipItem = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		try {
			shipItem.setContainer(shipContainer);
			shipContainer.addItem(shipItem);
		} catch (IllegalArgumentException ex) {
			throw new ModelException(ex);
		}
	}

	@Test(expected = ModelException.class)
	public void testContainerAddItemOverlapping() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		new Bullet(500, 500, 200, 200, 5, worldContainer);
		Ship shipItem = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		facade.addShipToWorld(worldContainer, shipItem);
	}

	@Test(expected = ModelException.class)
	public void testContainerAddItemSame() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Bullet bullet = new Bullet(500, 500, 200, 200, 5, worldContainer);
		facade.addBulletToWorld(worldContainer, bullet);
	}

	@Test
	public void testContainerRemoveItem() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Ship shipContainer = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		Bullet bulletItem = new Bullet(200, 200, 200, 200, 5, worldContainer);
		Bullet bulletItem2 = new Bullet(200, 200, 200, 200, 5, shipContainer);
		Ship shipItem = new Ship(800, 800, 800, 800, 3, 30, 12e18, worldContainer, 1);
		facade.addShipToWorld(worldContainer, shipContainer);
		assertTrue(worldContainer.hasProperItems());
		assertTrue(shipContainer.hasProperItems());
		bulletItem.setContainer(null);
		shipItem.setContainer(null);
		shipContainer.setContainer(null);
		worldContainer.removeItem(bulletItem);
		worldContainer.removeItem(shipItem);
		worldContainer.removeItem(shipContainer);
		assertTrue(worldContainer.hasProperItems());
		assertTrue(!worldContainer.hasAsItem(bulletItem));
		assertTrue(!worldContainer.hasAsItem(shipItem));
		assertTrue(!worldContainer.hasAsItem(shipContainer));
		assertTrue(shipContainer.hasAsItem(bulletItem2));
		bulletItem2.setContainer(null);
		shipContainer.removeItem(bulletItem2);
		assertTrue(shipContainer.hasProperItems());
		assertTrue(!shipContainer.hasAsItem(bulletItem2));
	}

	@Test(expected = ModelException.class)
	public void testContainerRemoveItemNull() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		try {
			worldContainer.removeItem(null);
		} catch (IllegalArgumentException ex) {
			throw new ModelException(ex);
		}
	}

	@Test(expected = ModelException.class)
	public void testContainerRemoveItemHasContainer() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Bullet bulletItem = new Bullet(200, 200, 200, 200, 5, worldContainer);
		try {
			worldContainer.removeItem(bulletItem);
		} catch (IllegalArgumentException ex) {
			throw new ModelException(ex);
		}
	}

	@Test(expected = ModelException.class)
	public void testContainerRemoveItemHasOtherContainer() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Ship shipContainer = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		Bullet bulletItem = new Bullet(200, 200, 200, 200, 5, shipContainer);
		try {
			worldContainer.removeItem(bulletItem);
		} catch (IllegalArgumentException ex) {
			throw new ModelException(ex);
		}
	}

	@Test
	public void testTerminateBullet() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Ship shipContainer = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		Bullet bulletItem = new Bullet(200, 200, 200, 200, 5, worldContainer);
		Bullet bulletItem2 = new Bullet(500, 500, 200, 200, 5, shipContainer);
		bulletItem.terminate();
		bulletItem2.terminate();
		assertTrue(bulletItem.isTerminated());
		assertNull(bulletItem.getContainer());
		assertNull(bulletItem.getSource());
		assertTrue(!worldContainer.hasAsItem(bulletItem));
		assertEquals(0, worldContainer.getNbItems());
		assertTrue(bulletItem2.isTerminated());
		assertNull(bulletItem2.getContainer());
		assertNull(bulletItem2.getSource());
		assertTrue(!shipContainer.hasAsItem(bulletItem2));
		assertEquals(0, shipContainer.getNbItems());
	}

	@Test
	public void testTerminateShip() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Ship ship = new Ship(800, 800, 800, 800, 3, 30, 12e18, worldContainer, 1);
		Bullet bulletItem = new Bullet(200, 200, 200, 200, 5, ship);
		Bullet bulletItem2 = new Bullet(200, 200, 200, 200, 5, ship);
		ship.terminate();
		assertTrue(ship.isTerminated());
		assertTrue(ship.isTerminated());
		assertNull(ship.getContainer());
		assertTrue(!worldContainer.hasAsItem(ship));
		assertEquals(0, worldContainer.getNbItems());
		assertTrue(!ship.hasAsItem(bulletItem));
		assertTrue(!ship.hasAsItem(bulletItem2));
		assertEquals(0, ship.getNbItems());
		assertTrue(!worldContainer.isTerminated());
		for (Bullet b : ship.getBullets())
			assertTrue(!b.isTerminated());
	}

	@Test
	public void testTerminateWorld() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Ship ship = new Ship(800, 800, 800, 800, 3, 30, 12e18, worldContainer, 1);
		Bullet bulletItem = new Bullet(200, 200, 200, 200, 5, worldContainer);
		new Bullet(200, 200, 200, 200, 5, ship);
		worldContainer.terminate();
		assertTrue(worldContainer.isTerminated());
		assertTrue(worldContainer.isTerminated());
		assertEquals(0, worldContainer.getNbItems());
		assertTrue(!worldContainer.hasAsItem(bulletItem));
		assertTrue(!worldContainer.hasAsItem(ship));
		assertNull(ship.getContainer());
		assertNull(bulletItem.getContainer());
		assertTrue(!ship.isTerminated());
		assertTrue(!bulletItem.isTerminated());
	}

	@Test
	public void testBoundsWorld() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		assertTrue(worldContainer.isInBounds(new Vector2d(50, 50), 50));
		assertTrue(worldContainer.isInBounds(new Vector2d(9950, 9950), 50));
		assertTrue(!worldContainer.isInBounds(Vector2d.ZERO, 5));
		assertTrue(!worldContainer.isInBounds(new Vector2d(15000, 500), 2e6));
	}

	@Test
	public void testBoundsShip() throws ModelException {
		Ship shipContainer = facade.createShip(500, 500, 0, 0, 200, 0, 15e20);
		assertTrue(shipContainer.isInBounds(new Vector2d(500, 500), 200));
		assertTrue(shipContainer.isInBounds(new Vector2d(600, 500), 100));
		assertTrue(!shipContainer.isInBounds(Vector2d.ZERO, 5));
		assertTrue(!shipContainer.isInBounds(new Vector2d(500, 500), 2e6));
	}

	@Test
	public void testIsCollidingBounds() throws ModelException {
		World worldContainer = facade.createWorld(10000, 10000);
		Ship ship = new Ship(800, 800, 800, 800, 3, 30, 12e18, worldContainer, 1);
		worldContainer.evolve(worldContainer.getNextBoundaryCollision().getTimeToCollision());
		assertTrue(worldContainer.isEntityCollidingBounds(ship.getPosition(), ship.getRadius()));
		worldContainer.evolve(1);
		assertTrue(!worldContainer.isEntityCollidingBounds(ship.getPosition(), ship.getRadius()));
	}

	@Test
	public void testVector2d() throws ModelException {
		Vector2d a = new Vector2d(1, 2.2);
		assertEqualsVector(new Vector2d(0, Math.PI + 2.2), a.add(new Vector2d(-1, Math.PI)), EPSILON);
		assertEqualsVector(new Vector2d(-5, 4.4), a.mul(new Vector2d(-5, 2)), EPSILON);
		assertEqualsVector(new Vector2d(0.1, 0.22), a.mul(0.1), EPSILON);
		assertEqualsVector(new Vector2d(6, 0.2), a.sub(new Vector2d(-5, 2)), EPSILON);
		assertEqualsVector(new Vector2d(Math.cos(Math.PI / 6), Math.sin(Math.PI / 6)),
				new Vector2d(Math.sqrt(3), 1).normalize(), EPSILON);
		assertEquals(1, a.dot(Vector2d.X_AXIS), EPSILON);
		assertEquals(Math.sqrt(1 + 2.2 * 2.2), a.getLength(), EPSILON);
		assertEquals(1 + 2.2 * 2.2, a.getLengthSquared(), EPSILON);
		assertEquals(-1, Vector2d.intersect(a, Vector2d.X_AXIS, Vector2d.ZERO, Vector2d.Y_AXIS), EPSILON);
		assertEquals(1,
				Vector2d.intersect(new Vector2d(2, 3), new Vector2d(1, 2), new Vector2d(4, 6), new Vector2d(1, 1)),
				EPSILON);
	}

	@Test
	public void testGetSource() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(300, 300, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(250, 250, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		facade.loadBulletOnShip(ship, bullet3);
		facade.loadBulletOnShip(ship, bullet4);
		assertEquals(ship, bullet1.getSource());
		assertEquals(ship, bullet2.getSource());
		assertEquals(ship, bullet3.getSource());
		assertEquals(ship, bullet4.getSource());
	}

	@Test
	public void testGetSourceWhenBulletFired() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(300, 300, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(250, 250, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		facade.loadBulletOnShip(ship, bullet3);
		facade.loadBulletOnShip(ship, bullet4);
		facade.fireBullet(ship);
		assertEquals(ship, bullet1.getSource());
		facade.fireBullet(ship);
		assertEquals(ship, bullet2.getSource());
		facade.fireBullet(ship);
		assertEquals(ship, bullet3.getSource());
		facade.fireBullet(ship);
		assertEquals(ship, bullet4.getSource());
	}

	@Test
	public void testGetSourceWhenBulletTerminated() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(300, 300, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(250, 250, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		facade.loadBulletOnShip(ship, bullet3);
		facade.loadBulletOnShip(ship, bullet4);
		facade.terminateBullet(bullet1);
		facade.terminateBullet(bullet2);
		facade.terminateBullet(bullet3);
		facade.terminateBullet(bullet4);
		assertNull(bullet1.getSource());
		assertNull(bullet2.getSource());
		assertNull(bullet3.getSource());
		assertNull(bullet4.getSource());
	}

	@Test
	public void testGetOtherCollidingEntity() throws ModelException {
		World world = facade.createWorld(10000, 10000);
		Ship ship1 = new Ship(500, 500, 100, 0, 0, 100, 20e20, world, 20e20);
		Ship ship2 = new Ship(1000, 500, -200, 0, Math.PI, 100, 20e20, world, 20e20);
		CollisionData data = world.getNextEntityCollision();
		assertNotNull(data);
		assertEquals(1.0, data.getTimeToCollision(), EPSILON);
		assertTrue(data.getColliders().contains(ship2));
		assertTrue(data.getColliders().contains(ship1));
		assertEquals(world.getNextCollision(), data);
		assertEquals(ship1, data.getOther(ship2));
		assertEquals(ship2, data.getOther(ship1));
	}

	@Test
	public void testGetEntitiesByFilter() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 50, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(200, 200, 10, 5, 20);
		Bullet bullet2 = facade.createBullet(700, 700, 10, 5, 30);
		facade.addBulletToWorld(world, bullet1);
		facade.addBulletToWorld(world, bullet2);
		Asteroid asteroid = facade.createAsteroid(50, 50, 10, 5, 10);
		Planetoid planetoid = facade.createPlanetoid(300, 300, 10, 5, 10, 200);
		facade.addAsteroidToWorld(world, asteroid);
		facade.addPlanetoidToWorld(world, planetoid);
		Set<Bullet> bullets = new HashSet<Bullet>();
		bullets.add(bullet1);
		bullets.add(bullet2);
		Set<Ship> ships = new HashSet<Ship>();
		ships.add(ship);
		Set<Entity> entities = new HashSet<Entity>();
		Set<Asteroid> asteroids = new HashSet<Asteroid>();
		asteroids.add(asteroid);
		Set<Planetoid> planetoids = new HashSet<Planetoid>();
		planetoids.add(planetoid);
		entities.add(ship);
		entities.add(bullet1);
		entities.add(bullet2);
		entities.add(asteroid);
		entities.add(planetoid);
		assertEquals(bullets, world.getEntitiesByFilter(a -> a instanceof Bullet));
		assertEquals(ships, world.getEntitiesByFilter(a -> a instanceof Ship));
		assertEquals(entities, world.getEntitiesByFilter(a -> a instanceof Entity));
		assertEquals(asteroids, world.getEntitiesByFilter(a -> a instanceof Asteroid));
		assertEquals(planetoids, world.getEntitiesByFilter(a -> a instanceof Planetoid));
	}

	@Test
	public void testTerminateAsteroidInWorld() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Asteroid asteroid = facade.createAsteroid(500, 500, 500, 500, 20);
		facade.addAsteroidToWorld(world, asteroid);
		facade.terminateAsteroid(asteroid);
		assertTrue(facade.isTerminatedAsteroid(asteroid));
		assertNull(facade.getAsteroidWorld(asteroid));
		assertTrue(facade.getWorldAsteroids(world).isEmpty());
	}

	@Test
	public void testTerminatePlanetoidInWorldSmallRadius() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Planetoid planetoid = facade.createPlanetoid(500, 500, 500, 500, 29, 0);
		facade.addPlanetoidToWorld(world, planetoid);
		facade.terminatePlanetoid(planetoid);
		assertTrue(facade.isTerminatedPlanetoid(planetoid));
		assertNull(facade.getPlanetoidWorld(planetoid));
		assertTrue(facade.getWorldPlanetoids(world).isEmpty());
	}

	@Test
	public void testTerminatePlanetoidInWorldBigRadius() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Planetoid planetoid = facade.createPlanetoid(500, 500, 500, 500, 45, 0);
		facade.addPlanetoidToWorld(world, planetoid);
		facade.terminatePlanetoid(planetoid);
		assertTrue(facade.isTerminatedPlanetoid(planetoid));
		assertNull(facade.getPlanetoidWorld(planetoid));
		assertTrue(facade.getWorldPlanetoids(world).isEmpty());
		assertEquals(2, facade.getWorldAsteroids(world).size());
	}

	@Test
	public void testTerminatePlanetoidNoWorldBigRadius() throws ModelException {
		Planetoid planetoid = facade.createPlanetoid(500, 500, 500, 500, 45, 0);
		facade.terminatePlanetoid(planetoid);
		assertTrue(facade.isTerminatedPlanetoid(planetoid));
		assertNull(facade.getPlanetoidWorld(planetoid));
	}

	@Test
	public void testOverlapsCircle() throws ModelException {
		Entity e = facade.createAsteroid(0, 0, 0, 0, 10);
		assertTrue(e.overlapsCircle(new Vector2d(5, 0), 2));
		assertTrue(!e.overlapsCircle(new Vector2d(11, 0), 1));
	}
	
	@Test
	public void testGetEntityAt() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Planetoid planetoid = facade.createPlanetoid(500, 500, 500, 500, 45, 0);
		facade.addPlanetoidToWorld(world, planetoid);
		planetoid.setPosition(200, 200);
		assertEquals(planetoid, world.getEntityAt(new Vector2d(200,200)));
	}
}
