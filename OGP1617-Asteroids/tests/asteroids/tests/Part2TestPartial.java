package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asteroids.facade.Facade;
import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part2.facade.IFacade;
import asteroids.util.ModelException;

public class Part2TestPartial{

	private static final double EPSILON = 0.0001;

	IFacade facade;

	@Before
	public void setUp(){
		facade = new Facade();
	}

	@Test
	public void testCreateWorld() throws ModelException{
		World world = facade.createWorld(1000, 800);
		assertEquals(1000, facade.getWorldSize(world)[0], EPSILON);
		assertEquals(800, facade.getWorldSize(world)[1], EPSILON);
		assertTrue(facade.getWorldShips(world).isEmpty());
		assertTrue(facade.getWorldBullets(world).isEmpty());
	}

	@Test
	public void testLoadBulletOnShipOverlappingBullets() throws ModelException{
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(100, 120, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(130, 110, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		assertEquals(2, facade.getNbBulletsOnShip(ship));
	}

	@Test
	public void testEvolveShipWithActiveThruster() throws ModelException{
		World world = facade.createWorld(5000, 5000);
		Ship ship = facade.createShip(100, 120, 10, 0, 50, Math.PI, 1.1E18);
		facade.addShipToWorld(world, ship);
		facade.setThrusterActive(ship, true);
		assertEquals(1000.0, facade.getShipAcceleration(ship), EPSILON);
		assertTrue(facade.isShipThrusterActive(ship));
		facade.evolve(world, 1, null);
		assertEquals(-990, facade.getShipVelocity(ship)[0], EPSILON);
		assertEquals(0, facade.getShipVelocity(ship)[1], EPSILON);
	}

	@Test
	public void testCreateEntity() throws ModelException{
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
	public void testCreateEntityExceedingVelocity() throws ModelException{
		Bullet entity = facade.createBullet(100.0, 200.0, 300001.0, -10.0, 20.0);
		assertNotNull(entity);
		double[] velocity = facade.getBulletVelocity(entity);
		assertNotNull(velocity);
		assertEquals(0, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testCreateEntityRadiusNegative() throws ModelException{
		facade.createShip(200.0, 300.0, 10.0, -10.0, -200.0, 0.0, 8e16);
	}

	@Test(expected = ModelException.class)
	public void testCreateEntityRadiusInsufficient() throws ModelException{
		facade.createShip(200.0, 300.0, 10.0, -10.0, 9.0, 0.0, 8e16);
	}

	public void testCreateEntityMassTooLow() throws ModelException{
		Ship ship = facade.createShip(200.0, 300.0, 10.0, -10.0, 500.0, 0.0, 1e8);
		assertNotNull(ship);
		assertEquals(Ship.getLowestMassDensity() * 4.0 / 3.0 * Math.PI * 25e6, ship.getMass(), EPSILON);

	}

	@Test(expected = ModelException.class)
	public void testCreateShipOrientationExceeds() throws ModelException{
		facade.createShip(200.0, 300.0, 10.0, -10.0, 20.0, 7.0, 8e16);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipOrientationNegative() throws ModelException{
		facade.createShip(200.0, 300.0, 10.0, -10.0, 20.0, -1.0, 8e16);
	}
	

	// @Test(expected = AssertionError.class)
	// public void testBoundaryCollisionWithACollision() throws ModelException{
	//
	// World world = facade.createWorld(1000, 1000);
	// Ship ship = facade.createShip(950, 950, 0, 10, 100, 0, 1.1E18);
	// facade.addShipToWorld(world, ship);
	// }

	@Test
	public void testBoundaryCollisionWithNoCollision() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 0, 10, 100, 0, 1.1E18);
		facade.addShipToWorld(world, ship);
		assertFalse(world.isEntityCollidingBounds(ship.getPosition(), ship.getRadius()));
	}

	@Test
	public void testOverlapsWithAnyEntitiesWithActualOverlaps() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(500, 500, 10, 5, 50);
		facade.addBulletToWorld(world, bullet1);
		assertTrue(world.overlapsWithAnyEntity(bullet2).size() > 0);
	}

	@Test
	public void testOverlapsWithAnyEntitiesWithoutOverlaps() throws ModelException{
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
	public void testOverlapsWithAnyEntitiesWithoutAssociationWithTheSameWorld() throws ModelException{
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
	public void testLoadNormalBullets() throws ModelException{
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(400, 400, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		facade.loadBulletOnShip(ship, bullet3);
		facade.loadBulletOnShip(ship, bullet4);
		assertEquals(4, facade.getNbBulletsOnShip(ship));
	}

	@Test(expected = ModelException.class)
	public void testLoadInvalidBullets() throws ModelException{
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
	public void testLoadListOfBullets() throws ModelException{
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 50);
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
	public void testFireBulletWhenShipIsNotAPartOffAWorld() throws ModelException{
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(400, 400, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
		Set<Bullet> bullets = new HashSet<Bullet>();
		bullets.add(bullet1);
		bullets.add(bullet2);
		bullets.add(bullet3);
		bullets.add(bullet4);
		facade.loadBulletsOnShip(ship, bullets);
		ship.setContainer(null);
		facade.fireBullet(ship);
		assertEquals(ship, bullet1.getContainer());
		assertEquals(ship, bullet2.getContainer());
		assertEquals(ship, bullet3.getContainer());
		assertEquals(ship, bullet4.getContainer());
	}

	@Test
	public void testFireBulletWhenShipHasNoBullets() throws ModelException{
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
	public void testFireBulletWhenFiredBulletIsOutsideWorldBoundaries() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(900, 100, 10, 5, 80, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(900, 100, 10, 5, 50);
		facade.loadBulletOnShip(ship, bullet1);
		facade.fireBullet(ship);
		assertTrue(bullet1.isTerminated());
	}

	@Test
	public void testFireBulletWhichResultsInImmediateCollisionWithAnotherShip() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 50, 0, 1.0E20);
		Ship ship2 = facade.createShip(600, 500, 10, 5, 45, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		facade.addShipToWorld(world, ship2);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 20);
		facade.loadBulletOnShip(ship, bullet1);
		assertTrue(ship.hasAsItem(bullet1));
		facade.fireBullet(ship);
		assertTrue(bullet1.overlaps(ship2));
		assertEquals(null, bullet1.getContainer());
		// TODO een test die te maken heeft met de opgetreden collision
	}

	@Test
	public void testFireBullet() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 50, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(200, 200, 10, 5, 20);
		facade.loadBulletOnShip(ship, bullet1);
		facade.fireBullet(ship);
		assertEquals(world, bullet1.getContainer());
		assertTrue(world.hasAsItem(bullet1));
	}

	@Test
	public void testFireBulletMultipleBullets() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 10, 5, 50, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(200, 200, 10, 5, 20);
		Bullet bullet2 = facade.createBullet(400, 400, 10, 5, 30);
		Bullet bullet3 = facade.createBullet(300, 300, 10, 5, 30);
		Bullet bullet4 = facade.createBullet(200, 200, 10, 5, 30);
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
		for(Bullet b : bullets)
			assertTrue(b.isTerminated());
	}

	@Test
	public void testGetEntities_Ships_Bullets() throws ModelException{
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
	public void testGetEntities_Ships_Bullets_WhenBulletsAreLoaded() throws ModelException{
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

}
