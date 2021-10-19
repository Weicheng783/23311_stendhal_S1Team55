package games.stendhal.server.maps.nalwor.forest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;
import utilities.ZonePlayerAndNPCTestImpl;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import utilities.QuestHelper;


public class DojoNPCTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String REPLY_TRAIN = "At your level of experience, your attack strength is too high to train here at this time.";
	private static final String REPLY_HELLO = "This is the assassins' dojo.";

	private static final String ZONE_NAME = "dojoZone";
	private static final String NPC_NAME = "Omura Sumitada";
	
	private Player player;
	private SpeakerNPC npc;
	private Engine engine;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public DojoNPCTest() {
		setNpcNames(NPC_NAME);
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new Dojo(), ZONE_NAME);
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		player = createPlayer("player");
		npc = SingletonRepository.getNPCList().get(NPC_NAME);
		engine = npc.getEngine();
	}


	private void startConversation() {
		engine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(npc.isTalking());
		assertEquals(REPLY_HELLO, getReply(npc));
	}
	
	private void checkReply(String question, String expectedReply) {
		engine.step(player, question);
		assertTrue(npc.isTalking());
		assertEquals(expectedReply, getReply(npc));
	}

	
	@Test
	public void testATKTooHigh() {
		startConversation();
		checkReply("fee", REPLY_TRAIN);
	}


	
		
}
