/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

package games.stendhal.client.actions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import org.junit.BeforeClass;
import org.junit.Test;


import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.StatusType;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import games.stendhal.server.entity.status.PoisonStatus;
import games.stendhal.server.entity.item.scroll.MarkedScroll;


public class TeleportScrollTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        MockStendlRPWorld.get();
        MockStendlRPWorld.get().addRPZone(new StendhalRPZone("0_semos_city", 100, 100));
        Log4J.init();
    }


    /**
     * Tests for execute.
     */
    @Test
    public final void testExecute() {
        final Player bob = PlayerTestHelper.createPlayer("player");
        PlayerTestHelper.registerPlayer(bob, "0_semos_city");
        PoisonStatus poison = new PoisonStatus(1000, 1, 200);
        bob.getStatusList().inflictStatus(poison, bob);

        assertTrue(bob.hasStatus(StatusType.POISONED));

        final MarkedScroll marked = (MarkedScroll) SingletonRepository.getEntityManager().getItem("home scroll");
        bob.equip("rhand", marked);

        assertFalse(marked.onUsed(bob));
    }

}

