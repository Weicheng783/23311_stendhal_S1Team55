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
import games.stendhal.server.entity.mapstuff.sign.ShopSign;
import games.stendhal.server.entity.npc.NPC;


import java.util.ArrayList;
import java.util.List;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.ShopList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import utilities.QuestHelper;

public class FurnitureTest  {

    private static final String ZONE_NAME = "int_furniture_store";
    private static final String npcName = "Hans the Joiner";

    private static FurnitureNPC configurator;
    private static SpeakerNPC joiner;
    private ShopSign sign_sell;
    protected StendhalRPZone zone;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
         QuestHelper.setUpBeforeClass();
    }

    @Before
    public void setUp() throws Exception {
        SingletonRepository.getRPWorld().onInit();

        zone = SingletonRepository.getRPWorld().getZone(ZONE_NAME);

        configurator = FurnitureNPC.getPotionsDealerNPC();

        joiner = configurator.getNPC();
    }

    @Test
    public void ZoneExists() {
        assertNotNull(zone);
    }

    @Test
    public void NpcExists() {
        assertNotNull(zone);
        List<String> names = new ArrayList<>();
        for (NPC npc:
             zone.getNPCList()) {
            names.add(npc.getName());
        }
        assertTrue(names.contains(npcName));
    }

    @Test
    public void testItemsInShop() {
        sign_sell = configurator.getSellShopSign();
        assertNotNull(sign_sell);
        ShopList shoplist = sign_sell.getShopList();

        assertNotNull(shoplist.get("chair"));
    }


}
