package games.stendhal.server.events;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class BestiaryEventTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		MockStendlRPWorld.reset();
	}
	@Test
	public void test() {
		//StendhalRPWorld world = SingletonRepository.getRPWorld();
		PlayerTestHelper.generateCreatureRPClasses();
		final Player player = PlayerTestHelper.createPlayer("player");
		
		//kill a creature
		player.setSoloKill("rat");
		BestiaryEvent event = new BestiaryEvent(player);
		List<String> enemies = Arrays.asList(event.get("enemies").split(";"));
		assertEquals(1,enemies.size());
		//kill another to see if recognised
		player.setSoloKill("angel");
		event = new BestiaryEvent(player);		
		enemies = Arrays.asList(event.get("enemies").split(";"));
		assertEquals(2,enemies.size());
		//kill a not exist creature should not be counted in
		player.setSoloKill("gareth");
		event = new BestiaryEvent(player);		
		enemies = Arrays.asList(event.get("enemies").split(";"));
		assertEquals(2,enemies.size());
	}

}