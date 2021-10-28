package games.stendhal.server.maps.quests;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.semos.library.HistorianGeographerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;
import static utilities.SpeakerNPCTestHelper.getReply;


public class MeetZynnTest {

    private Player player = null;
    private SpeakerNPC npc = null;
    private Engine en = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ItemTestHelper.generateRPClasses();
        QuestHelper.setUpBeforeClass();
    }

    @Before
    public void setUp() {
        StendhalRPZone zone = new StendhalRPZone("admin_test");
        new HistorianGeographerNPC().configureZone(zone, null);
        player = PlayerTestHelper.createPlayer("garry");
    }

    @Test
    public void testQuest() {

        npc = SingletonRepository.getNPCList().get("Zynn Iwuhos");
        en = npc.getEngine();
        AbstractQuest quest = new MeetZynn();
        quest.addToWorld();
        en.step(player, "hi");
        assertEquals("Hi, potential reader! Here you can find records of the history of Semos, and lots of interesting facts about this island of Faiumoni. If you like, I can give you a quick introduction to its #geography and #history! I also keep up with the #news, so feel free to ask me about that.",getReply(npc));
		
        //Test the "history" dialog 10 times and see if you get 50 experiences.
		assertEquals(0, player.getXP());
		for(int i = 0;i<10;i++) {
			en.step(player, "history");
		}
		assertEquals(50, player.getXP());
		
		//kill rat and see if experience is gained and the amount of experience gained is normal
		player.setSoloKill("rat");
	}
	


}
