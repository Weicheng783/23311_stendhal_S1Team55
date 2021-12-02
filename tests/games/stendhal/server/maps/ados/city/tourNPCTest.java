package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class tourNPCTest extends ZonePlayerAndNPCTestImpl {

	private Player player;
	private SpeakerNPC npc;
	private Engine engine;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone("0_ados_city");
	}
	
	public tourNPCTest() {
		setNpcNames("Mysterious Mage");
		setZoneForPlayer("0_ados_city");
		addZoneConfigurator(new tourNPC(), "0_ados_city");
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		player = createPlayer("player");
		npc = SingletonRepository.getNPCList().get("Mysterious Mage");
		engine = npc.getEngine();
	}

	@Test
	public void NPCExistsTest() {
		engine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(npc.isTalking());
	}

}
