package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import asteroids.facade.Facade;
import asteroids.model.Ship;
import asteroids.part1.facade.IFacade;
import asteroids.util.ModelException;

public class Part1Test{

	private static final double EPSILON = 0.0001;

	IFacade facade;

	@Before
	public void setUp(){
		facade = new Facade();
	}

	@Test
	public void testCreateShip() throws ModelException{
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI);
		assertNotNull(ship);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
		double[] velocity = facade.getShipVelocity(ship);
		assertNotNull(velocity);
		assertEquals(10, velocity[0], EPSILON);
		assertEquals(-10, velocity[1], EPSILON);
		assertEquals(Math.PI, facade.getShipOrientation(ship), EPSILON);
		assertEquals(20, facade.getShipRadius(ship), EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipXIsNan() throws ModelException{
		facade.createShip(Double.NaN, 200, 10, -10, 20, Math.PI);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipYIsNan() throws ModelException{
		facade.createShip(-2500, Double.NaN, 10, -10, 20, Math.PI);
	}

	@Test
	public void testCreateShipExceedingVelocity() throws ModelException{
		Ship ship = facade.createShip(100, 200, 300001.123, -99, 20, Math.PI);
		assertNotNull(ship);
		double[] velocity = facade.getShipVelocity(ship);
		assertNotNull(velocity);
		assertEquals(0, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipRadiusNegative() throws ModelException{
		facade.createShip(100, 200, 10, -10, -20, Math.PI);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipRadiusInsufficient() throws ModelException{
		facade.createShip(100, 200, 10, -10, 9, Math.PI);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipOrientationExceeds() throws ModelException{
		facade.createShip(100, 200, 10, -10, 15, 2 * Math.PI);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipOrientationNegative() throws ModelException{
		facade.createShip(100, 200, 10, -10, 15, -Math.PI);
	}

	@Test
	public void testMove() throws ModelException{
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 0);
		facade.move(ship, 1);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(130, position[0], EPSILON);
		assertEquals(85, position[1], EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testMoveNegativeTimeDelta() throws ModelException{
		Ship ship = facade.createShip();
		facade.move(ship, -1);
	}

	@Test
	public void testTurn() throws ModelException{
		Ship ship = facade.createShip();
		facade.turn(ship, Math.PI / 4.0);
		assertEquals(Math.PI / 4.0, facade.getShipOrientation(ship), EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testTurnAngleExceeds() throws ModelException{
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 6.09876);
		facade.turn(ship, 1);
	}

	@Test
	public void testThrust() throws ModelException{
		Ship ship = facade.createShip(0, 0, 120, 0, 20, Math.PI);
		facade.thrust(ship, 123);
		double[] velocity = facade.getShipVelocity(ship);
		assertNotNull(velocity);
		assertEquals(-3, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}

	@Test
	public void testThrustNegativeAmount() throws ModelException{
		Ship ship = facade.createShip(0, 1, 2, 3, 45, 6);
		facade.thrust(ship, -1);
		double[] velocity = facade.getShipVelocity(ship);
		assertNotNull(velocity);
		assertEquals(2, velocity[0], EPSILON);
		assertEquals(3, velocity[1], EPSILON);
	}

	@Test
	public void testThrustVelocityExceeds() throws ModelException{
		Ship ship = facade.createShip(0, 0, 5, 0, 45, Math.PI / 2.0);
		facade.thrust(ship, ship.getMaxVelocity() * 2);
		double[] velocity = facade.getShipVelocity(ship);
		assertNotNull(velocity);
		assertEquals(ship.getMaxVelocity(), velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}

	@Test
	public void testDistanceBetween() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 11, 0);
		Ship ship2 = facade.createShip(25, 0, 0, 0, 12, 0);
		assertEquals(2, facade.getDistanceBetween(ship1, ship2), EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testDistanceBetweenNull() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 11, 0);
		Ship ship2 = null;
		facade.getDistanceBetween(ship1, ship2);
	}

	@Test
	public void testDistanceBetweenNegative() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 20, 0);
		Ship ship2 = facade.createShip(25, 0, 0, 0, 12, 0);
		assertEquals(-7, facade.getDistanceBetween(ship1, ship2), EPSILON);
	}

	@Test
	public void testOverlaps() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 20, 0);
		Ship ship2 = facade.createShip(25, 0, 0, 0, 12, 0);
		assertEquals(true, facade.overlap(ship1, ship2));
	}

	@Test(expected = ModelException.class)
	public void testOverlapsNull() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 11, 0);
		Ship ship2 = null;
		facade.overlap(ship1, ship2);
	}

	@Test
	public void testOverlapsBoundary() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 13, 0);
		Ship ship2 = facade.createShip(25, 0, 0, 0, 12, 0);
		assertEquals(false, facade.overlap(ship1, ship2));
	}

	@Test
	public void testTimeToCollision() throws ModelException{
		Ship ship1 = facade.createShip(-15, 12, 15, 0, 12, 0);
		Ship ship2 = facade.createShip(0, -25, 0, 13, 12, 0);
		double timeDuration = facade.getTimeToCollision(ship1, ship2);
		assertEquals(1.0, timeDuration, EPSILON);
		ship1.move(timeDuration);
		ship2.move(timeDuration);
		assertEquals(0.0, facade.getDistanceBetween(ship1, ship2), EPSILON);
	}

	@Test
	public void testTimeToCollisionNoCollision() throws ModelException{
		Ship ship1 = facade.createShip(-15, 12, -15, 0, 12, 0);
		Ship ship2 = facade.createShip(0, -25, 0, -13, 12, 0);
		double timeDuration = facade.getTimeToCollision(ship1, ship2);
		assertEquals(Double.POSITIVE_INFINITY, timeDuration, EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testTimeToCollisionNullCase() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 11, 0);
		Ship ship2 = null;
		facade.getTimeToCollision(ship1, ship2);
	}

	@Test(expected = ModelException.class)
	public void testTimeToCollisionOverlappingShips() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 25, 0);
		Ship ship2 = facade.createShip(0, 30, 0, 0, 11, 0);
		facade.getTimeToCollision(ship1, ship2);
	}
	
	@Test
	public void testCollisionPosition() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 15, 0, 15, 0);
		Ship ship2 = facade.createShip(100, 0, -15, 0, 15, 0);
		double[] position = facade.getCollisionPosition(ship1, ship2);
		assertNotNull(position);
		assertEquals(50.0, position[0], EPSILON);
		assertEquals(0, position[1], EPSILON);
	}
	
	@Test(expected = ModelException.class)
	public void testCollisionPositionNullCase() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 11, 0);
		Ship ship2 = null;
		facade.getCollisionPosition(ship1, ship2);
	}
	
	@Test
	public void testCollisionPositionNoCollision() throws ModelException{
		Ship ship1 = facade.createShip(-15, 12, -15, 0, 12, 0);
		Ship ship2 = facade.createShip(0, -25, 0, -13, 12, 0);
		assertNull(facade.getCollisionPosition(ship1, ship2));
	}
	
	@Test(expected = ModelException.class)
	public void testCollisionPositionOverlappingShips() throws ModelException{
		Ship ship1 = facade.createShip(0, 0, 0, 0, 11, 0);
		Ship ship2 = facade.createShip(0, 30, 0, 0, 50, 0);
		facade.getCollisionPosition(ship1, ship2);
	}
}
