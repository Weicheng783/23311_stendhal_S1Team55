/***************************************************************************
 *                     Copyright © 2020 - Arianne                          *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.deniran.cityinterior.furnitureshop;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.NPC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.ShopList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.QuestHelper;

public class FurnitureTest {

    private static final String ZONE_NAME = "int_deniran_furniture_store";
    private static final String npcName = "Hans the Joiner";
    protected StendhalRPZone zone;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        QuestHelper.setUpBeforeClass();
    }

    @Before
    public void setUp() throws Exception {
        SingletonRepository.getRPWorld().onInit();
        zone = SingletonRepository.getRPWorld().getZone(ZONE_NAME);
    }

    @Test
    public void ZoneExists() {
        assertNotNull(zone);
    }

    @Test
    public void NpcExists() {
        assertNotNull(zone);
        List<String> names = new ArrayList<>();
        for (NPC npc :
                zone.getNPCList()) {
            names.add(npc.getName());
        }
        assertTrue(names.contains(npcName));
    }

    @Test
    public void testItemsInShop() {
        final ShopList shops = SingletonRepository.getShopList();
        final Map<String, Integer> prices = shops.get("deniranfurnituresell");
        assertNotNull(prices);

        assertNotNull(prices.get("table"));
    }
}
