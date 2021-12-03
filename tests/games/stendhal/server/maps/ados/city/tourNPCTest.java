package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;
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
	
	private void checkReply(String question, String expectedReply) {
		engine.step(player, question);
//		assertTrue(npc.isTalking());
		assertEquals(expectedReply, getReply(npc));
	}

	@Test
	public void NPCExistsTest() {
		engine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(npc.isTalking());
	}
	
	@Test
	public void canStartTour() {
		engine.setCurrentState(ConversationStates.ATTENDING);
		checkReply("tour","Tell me when you want to #continue");
	}
	
	@Test
	public void StartInvinsibiltyTest() {
		Player player = PlayerTestHelper.createPlayer("abcd");
		engine.setCurrentState(ConversationStates.ATTENDING);
		engine.step(player, "tour");
		assertEquals(true, player.isInvisibleToCreatures());
	}
	
	@Test
	public void EndInvinsibiltyTest() {
		Player player = PlayerTestHelper.createPlayer("abcd");
		engine.setCurrentState(ConversationStates.ATTENDING);
		engine.step(player, "tour");
		engine.setCurrentState(ConversationStates.ATTENDING);
		engine.step(player, "end");
		assertEquals(false, player.isInvisibleToCreatures());
	}

}
