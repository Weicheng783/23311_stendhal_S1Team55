package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.*;

import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.server.core.engine.SingletonRepository;
//import games.stendhal.server.entity.npc.SpeakerNPC;
//import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.maps.semos.bakery.ChefNPC;
// import games.stendhal.server.maps.semos.bakery.ShopAssistantNPC;
import utilities.PlayerTestHelper;
// import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class InvisibilityTest extends ZonePlayerAndNPCTestImpl {

	private Player player;

	@Override
	@Before
	public void setUp() throws Exception {
		player = createPlayer("abcd");
	}

	@Test
	public void InvinsibiltyTest() {
		Player player = PlayerTestHelper.createPlayer("abcd");
		assertEquals(true, player.isInvisibleToCreatures());
	}

}
