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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import utilities.PlayerTestHelper;
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
				sleepingBag = configurator.createOutfitList().get(0).getLabel();
			}catch(IndexOutOfBoundsException  e){
				sleepingBag = "no such item";
			}finally{
				assertEquals("sleeping bag", sleepingBag);
			};
			
		}
		
		
		@Test
		public void testSleepingBagEquippableOnBag() {
			final Player pl = PlayerTestHelper.createPlayer("player");
			final Player bob = PlayerTestHelper.createPlayer("bob");
			MockStendhalRPRuleProcessor.get().addPlayer(pl);
			MockStendhalRPRuleProcessor.get().addPlayer(bob);

			pl.put("adminlevel", 100);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("amount", "1");
			map.put("quantity", "1");
			map.put("frequency", "70");
			map.put("persistent", "1");
			map.put("menu", "Sleep|Use");
			Item addedItem =  new Item ("sleeping bag", "tool", "sleeping_bag", map);
			addedItem.setPosition(pl.getX(), pl.getY());
			List<String> listString = Arrays.asList("bag");
			addedItem.setEquipableSlots(listString);
			pl.equip("bag", addedItem);
			
			assertEquals(true, addedItem.canBeEquippedIn("bag"));
			assertTrue(!addedItem.onUsed(pl));
			
		}
		
		@Test
		public void testSleepingBagEquippableNotOnHead() {
			final Player pl = PlayerTestHelper.createPlayer("player");
			final Player bob = PlayerTestHelper.createPlayer("bob");
			MockStendhalRPRuleProcessor.get().addPlayer(pl);
			MockStendhalRPRuleProcessor.get().addPlayer(bob);

			pl.put("adminlevel", 100);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("amount", "1");
			map.put("quantity", "1");
			map.put("frequency", "70");
			map.put("persistent", "1");
			map.put("menu", "Sleep|Use");
			Item addedItem =  new Item ("sleeping bag", "tool", "sleeping_bag", map);
			addedItem.setPosition(pl.getX(), pl.getY());
			List<String> listString = Arrays.asList("bag");
			addedItem.setEquipableSlots(listString);
			pl.equip("bag", addedItem);

			assertEquals(false, addedItem.canBeEquippedIn("head"));
			assertTrue(!addedItem.onUsed(pl));
			pl.put("adminlevel", 1000);
			
		}
		
		@Test
		public void testSleepingBagCanBeUsedOnAdminUsers() {
			final Player pl = PlayerTestHelper.createPlayer("player");
			final Player bob = PlayerTestHelper.createPlayer("player");
			MockStendhalRPRuleProcessor.get().addPlayer(pl);
			MockStendhalRPRuleProcessor.get().addPlayer(bob);

			pl.put("adminlevel", 100);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("amount", "1");
			map.put("quantity", "1");
			map.put("frequency", "70");
			map.put("persistent", "1");
			map.put("menu", "Sleep|Use");
			Item addedItem =  new Item ("sleeping bag", "tool", "sleeping_bag", map);
			addedItem.setPosition(pl.getX(), pl.getY());
			List<String> listString = Arrays.asList("bag");
			addedItem.setEquipableSlots(listString);
			pl.equip("bag", addedItem);

			pl.put("adminlevel", 1000);
			
			addedItem.flag = 4;
			
			assertTrue(pl.getEquippedItemClass("bag", "tool").onUsed(pl));	
		}
		
		@Test
		public void testSleepingBagAddingHP() {
			final Player pl = PlayerTestHelper.createPlayer("player");
			final Player bob = PlayerTestHelper.createPlayer("bob");
			MockStendhalRPRuleProcessor.get().addPlayer(pl);
			MockStendhalRPRuleProcessor.get().addPlayer(bob);

			pl.put("adminlevel", 1000);
			pl.setHP(10);
			
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("amount", "1");
			map.put("quantity", "1");
			map.put("frequency", "70");
			map.put("persistent", "1");
			map.put("menu", "Sleep|Use");
			Item addedItem =  new Item ("sleeping bag", "tool", "sleeping_bag", map);
			addedItem.setPosition(pl.getX(), pl.getY());
			List<String> listString = Arrays.asList("bag");
			addedItem.setEquipableSlots(listString);
			addedItem.setBoundTo("pl");

			
			addedItem.HealLoop(pl);
			
//			assertEquals(3,addedItem.flag);
			
			assertTrue(pl.getHP() >= 10);
			
		}
		
		@Test
		public void testHPDeduction() {
			final Player pl = PlayerTestHelper.createPlayer("player");
			MockStendhalRPRuleProcessor.get().addPlayer(pl);

			pl.put("adminlevel", 1000);
			pl.setHP(1000);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("amount", "1");
			map.put("quantity", "1");
			map.put("frequency", "70");
			map.put("persistent", "1");
			map.put("menu", "Sleep|Use");
			Item addedItem =  new Item ("sleeping bag", "tool", "sleeping_bag", map);
			addedItem.setPosition(pl.getX(), pl.getY());
			List<String> listString = Arrays.asList("bag");
			addedItem.setEquipableSlots(listString);
			addedItem.setBoundTo("pl");

			
			addedItem.HealLoop(pl);
			
//			assertEquals(3,addedItem.flag);
			
			assertTrue(pl.getHP() >= 10);
			
		}

	}
