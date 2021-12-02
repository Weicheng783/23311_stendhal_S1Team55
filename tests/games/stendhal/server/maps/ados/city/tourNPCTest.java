package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import utilities.ZonePlayerAndNPCTestImpl;

public class tourNPCTest extends ZonePlayerAndNPCTestImpl {

	private Player player;
	private SpeakerNPC npc;

	@Override
	@Before
	public void setUp() throws Exception {
		player = createPlayer("abcd");
	}

	@Test
	public void NPCExistsTest() {
//		Player player = PlayerTestHelper.createPlayer("abcd");
		npc = SingletonRepository.getNPCList().get("Mysterious Mage");
		assertNotNull(npc);
	}

}
