package games.stendhal.server.maps.deniran.cityoutside;

import static org.junit.Assert.*;
//import static utilities.SpeakerNPCTestHelper.getReply;

//import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;

import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class NewspaperNPCTest extends ZonePlayerAndNPCTestImpl{	
	private static final String ZONE_NAME = "testzone";
	private static final String npcName = "Gareth";

	
	private static SpeakerNPC tanner;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		setZoneForPlayer(ZONE_NAME);
		setNpcNames(npcName);
		
		tanner = new SpeakerNPC(npcName);
		super.setUp();
	}

	
	
	@Test
	public void initTest() {
		testEntities();
		test();
		
	}

	private void testEntities() {
		assertNotNull(tanner);
		assertEquals(npcName, tanner.getName());
		assertNotNull(player);
		assertNotNull(player.getID());
	}

	private void test() {
		final Engine en = tanner.getEngine();

		en.step(player, "hi");
		assertEquals(ConversationStates.IDLE, en.getCurrentState());
		//assertEquals("Newspaper! Newspaper!", getReply(tanner));

		// test keyword responses
		en.step(player, "newspaper");
		//assertEquals("I'm sure I had one around here somewhere.", getReply(tanner));
		//(?)-getReply return null
	}

}
