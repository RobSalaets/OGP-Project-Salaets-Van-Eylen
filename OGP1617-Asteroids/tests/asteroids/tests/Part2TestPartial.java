package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.facade.Facade;
import asteroids.part2.facade.IFacade;
import asteroids.util.ModelException;

public class Part2TestPartial {

	private static final double EPSILON = 0.0001;

	IFacade facade;

	@Before
	public void setUp() {

		facade = new Facade();
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
		facade.addShipToWorld(world, ship);
		facade.setThrusterActive(ship, true);
		assertEquals(1000.0, facade.getShipAcceleration(ship), EPSILON);
		assertTrue(facade.isShipThrusterActive(ship));
		facade.evolve(world, 1, null);
		assertEquals(-990, facade.getShipVelocity(ship)[0], EPSILON);
		assertEquals(0, facade.getShipVelocity(ship)[1], EPSILON);
	}

	@Test
	public void testBoundary() throws ModelException{

		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 0, -10, 100, 0, 1.1E18);
		facade.addShipToWorld(world, ship);
		facade.evolve(world, 45, null);
	}
	
	//@Test(expected = AssertionError.class)
	//public void testBoundaryCollisionWithACollision() throws ModelException{
	//
	//	World world = facade.createWorld(1000, 1000);
	//	Ship ship = facade.createShip(950, 950, 0, 10, 100, 0, 1.1E18);
	//	facade.addShipToWorld(world, ship);
	//}
	
	@Test 
	public void testBoundaryCollisionWithNoCollision() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 500, 0, 10, 100, 0, 1.1E18);
		facade.addShipToWorld(world, ship);
		assertFalse(world.isEntityColliding(ship.getPosition(), ship.getRadius()));
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
		Ship ship1 = facade.createShip(200,200, 0, 10, 100, 0, 1.1E18);
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
		Ship ship2 = facade.createShip(200,200, 0, 10, 100, 0, 1.1E18);
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
	
	@Test
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
		assertEquals(4, facade.getNbBulletsOnShip(ship));
		assertEquals(ship,bullet1.getContainer());
		assertEquals(ship,bullet2.getContainer());
		assertEquals(ship,bullet3.getContainer());
		assertEquals(ship,bullet4.getContainer());
		assertEquals(0, facade.getNbBulletsOnShip(ship2));
		assertFalse(bullet1.getContainer() == ship2);
		assertFalse(bullet2.getContainer() == ship2);
		
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
		assertEquals(ship,bullet1.getContainer());
		assertEquals(ship,bullet2.getContainer());
		assertEquals(ship,bullet3.getContainer());
		assertEquals(ship,bullet4.getContainer());
	}
	
	@Test
	public void testFireBulletWhenShipIsNotAPartOffAWorld() throws ModelException
{
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
		assertEquals(ship,bullet1.getContainer());
		assertEquals(ship,bullet2.getContainer());
		assertEquals(ship,bullet3.getContainer());
		assertEquals(ship,bullet4.getContainer());
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
		assertEquals(oldNbItemsInWorld,world.getNbItems());
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
		Ship ship2= facade.createShip(600, 500, 10, 5, 45, 0, 1.0E20);
		facade.addShipToWorld(world, ship);
		facade.addShipToWorld(world, ship2);
		Bullet bullet1 = facade.createBullet(500, 500, 10, 5, 20);
		facade.loadBulletOnShip(ship, bullet1);
		assertTrue(ship.hasAsItem(bullet1));
		facade.fireBullet(ship);
		assertTrue(bullet1.overlaps(ship2));
		assertEquals(null,bullet1.getContainer());
		//TODO een test die te maken heeft met de opgetreden collision
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
		assertEquals(world, bullet1.getContainer());
		assertTrue(world.hasAsItem(bullet1));
		assertEquals(world, bullet2.getContainer());
		assertTrue(world.hasAsItem(bullet2));
		assertEquals(world, bullet3.getContainer());
		assertTrue(world.hasAsItem(bullet3));
		assertEquals(world, bullet4.getContainer());
		assertTrue(world.hasAsItem(bullet4));
		
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
		assertEquals(bullets,world.getBullets());
		assertEquals(ships,world.getShips());
		assertEquals(entities,world.getAllEntities());
		
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
		assertEquals(bullets,world.getBullets());
		assertEquals(ships,world.getShips());
		assertEquals(entities,world.getAllEntities());
		
		}

	
}




