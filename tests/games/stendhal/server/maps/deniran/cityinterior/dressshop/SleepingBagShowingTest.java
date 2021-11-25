//package games.stendhal.server.maps.deniran.cityinterior.dressshop;
//
//import static org.junit.Assert.*;
//
//import java.util.List;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//
package games.stendhal.server.maps.deniran.cityinterior.dressshop;

import games.stendhal.server.core.engine.StendhalRPZone;
//
//import games.stendhal.server.maps.deniran.cityinterior.dressshop.OutfitLenderNPC.DeniranOutfit;
//
//import utilities.ZonePlayerAndNPCTestImpl;
//
//public class SleepingBagShowingTest extends ZonePlayerAndNPCTestImpl{
//	private static final String ZONE_NAME = "int_deniran_dress_shop";
//	public final StendhalRPZone ZONE = new StendhalRPZone(ZONE_NAME);

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//
//	public SleepingBagShowingTest() {
//		System.out.println("come here");
//		OutfitLenderNPC npctesting = new OutfitLenderNPC();
//		List<DeniranOutfit> shoped = npctesting.initShop(ZONE);
//		assertNotNull(shoped);
//	}
//	@Test
//	public void showingSleepingBag() {
//		
//	}
//}
	


import static org.junit.Assert.*;

//import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;

import games.stendhal.server.entity.npc.SpeakerNPC;
//import games.stendhal.server.maps.deniran.cityinterior.dressshop.OutfitLenderNPC.DeniranOutfit;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;


public class SleepingBagShowingTest extends ZonePlayerAndNPCTestImpl{
		
		private OutfitLenderNPC configurator;
		private static final String ZONE_NAME ="int_deniran_dress_shop";
		private static final String npcNAME = "Pierre";
		private static SpeakerNPC Pierre;
		private ShopSign sign_sell;
		private ShopSign sign_buy;
		public final StendhalRPZone ZONE = new StendhalRPZone(ZONE_NAME);
		
		 
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
			configurator = OutfitLenderNPC.getOutfitLenderNPC();
			addZoneConfigurator(configurator, ZONE_NAME);
			super.setUp();
//			Pierre = configurator.getNPC();
		}
		
		/* 
		 * Achieved the second goal of testing,
		 * testing the sleepingBag existence in the target shop.
		 * */
		@Test
		public void testExistenceofSleepingBagInShop() {
//			sign_sell = configurator.initShop(ZONE);
//			sign_buy = configurator.getBuyShopSign();
//			assertNotNull((configurator.OutfitType.toString()) );
//			assertNotNull(sign_buy);
			sign_sell = configurator.outfitCatalog(configurator.createOutfitList());
//			assertNotNull(configurator.createOutfitList().get(0).getLabel());
//			System.out.println(configurator.createOutfitList().get(0).getLabel());
			String sleepingBag = "";
			try {
				sleepingBag = configurator.createOutfitList().get(3).getLabel();
			}catch(IndexOutOfBoundsException  e){
				sleepingBag = "no such item";
			}finally{
				assertEquals("sleeping bag", sleepingBag);
			};
			
			
		}

	}
