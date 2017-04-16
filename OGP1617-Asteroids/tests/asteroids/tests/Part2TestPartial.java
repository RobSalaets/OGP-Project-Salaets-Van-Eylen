package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import asteroids.facade.Facade;
import asteroids.model.Bullet;
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
		assertEquals(8e16 / (4.0/3.0*Math.PI*8000), entity2.getMassDensity(), EPSILON);
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
}
