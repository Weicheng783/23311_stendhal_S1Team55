package games.stendhal.server.maps.deniran.cityinterior.potionsshop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;

import games.stendhal.server.entity.npc.SpeakerNPC;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;


public class DeniranPotionShopBlackboardsTest extends ZonePlayerAndNPCTestImpl{
	
	private static PotionsDealerNPC configurator;
	private static final String ZONE_NAME ="testzone";
	private static final String npcNAME = "Wanda";
	private static SpeakerNPC Wanda;
	private ShopSign sign_sell;
	private ShopSign sign_buy;
	
	 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	@Override
	@Before
	public void setUp() throws Exception {
		 
		setZoneForPlayer(ZONE_NAME);
		setNpcNames(npcNAME);
		configurator = PotionsDealerNPC.getPotionsDealerNPC();
		addZoneConfigurator(configurator, ZONE_NAME);
		super.setUp();
		Wanda = configurator.getNPC();
	}
	
	 

	 
	@Test
	public void testForBlackboards() {
		sign_sell = configurator.getSellShopSign();
		sign_buy = configurator.getBuyShopSign();
		assertNotNull(sign_sell);
		assertNotNull(sign_buy);
	}

}
