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
package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.portal.Door;
import games.stendhal.server.entity.mapstuff.sign.Sign;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPClass;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import games.stendhal.server.entity.player.Player;

public class ReverseArrowTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		QuestHelper.setUpBeforeClass();
		StendhalRPZone zone = new StendhalRPZone("int_ados_reverse_arrow");
		MockStendlRPWorld.get().addRPZone(zone);
		MockStendlRPWorld.get().addRPZone(new StendhalRPZone("0_semos_mountain_n2"));

		if (!RPClass.hasRPClass("door")) {
			Door.generateRPClass();
		}
		if (!RPClass.hasRPClass("sign")) {
			Sign.generateRPClass();
		}

	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		MockStendlRPWorld.reset();
		NPCList.get().clear();
	}


	/**
	 * Tests for getSlotName.
	 */
	
	@Test
	public void testGetSlotName() {
		assertEquals("reverse_arrow", new ReverseArrow().getSlotName());
	}

	/**
	 * Tests for addToWorld.
	 */
	@Test
	public void testAddToWorld() {

		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
	}

	/**
	 * Tests for finish.
	 */
	@Test
	public void testFinish() {
		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(arrowquest.player);
		arrowquest.finish(false, null);
		assertNotNull(arrowquest.player);

		arrowquest.finish(true, null);
		assertNull(arrowquest.player);
	}
	
	@Test
	public void testArrowReversed() {
		ReverseArrow arrowquest = new ReverseArrow();
		Player player = PlayerTestHelper.createPlayer("Ali");
		arrowquest.addToWorld();
		player.teleport(arrowquest.zone, 0, 0, null, null);
		player.onAdded(arrowquest.zone);
		arrowquest.start(player);
		// convert token postions to int to test them
		int token1X = arrowquest.tokens.get(1).getX();
		int token1Y = arrowquest.tokens.get(1).getY();
		int token2X = arrowquest.tokens.get(2).getX();
		int token2Y = arrowquest.tokens.get(2).getY();
		int token3X = arrowquest.tokens.get(3).getX();
		int token3Y = arrowquest.tokens.get(3).getY();
		int token4X = arrowquest.tokens.get(4).getX();
		int token4Y = arrowquest.tokens.get(4).getY();
		int token5X = arrowquest.tokens.get(5).getX();
		int token5Y = arrowquest.tokens.get(5).getY();
		int token6X = arrowquest.tokens.get(6).getX();
		int token6Y = arrowquest.tokens.get(6).getY();
		int token7X = arrowquest.tokens.get(7).getX();
		int token7Y = arrowquest.tokens.get(7).getY();
		int token8X = arrowquest.tokens.get(8).getX();
		int token8Y = arrowquest.tokens.get(8).getY();
		// simulate getting the correct answer
		arrowquest.tokens.get(1).setPosition(token1X - 2, token1Y + 1);
		arrowquest.tokens.get(2).setPosition(token2X - 1,token2Y + 1);
		arrowquest.tokens.get(3).setPosition(token3X - 1,token3Y + 2);
		arrowquest.tokens.get(4).setPosition(token4X - 1,token4Y + 2);
		arrowquest.tokens.get(5).setPosition(token5X - 1,token5Y + 1);
		arrowquest.tokens.get(6).setPosition(token6X - 1,token6Y + 1);
		arrowquest.tokens.get(7).setPosition(token7X - 1,token7Y + 1);
		arrowquest.tokens.get(8).setPosition(token8X + 1,token8Y + 1);
		ReverseArrow.ReverseArrowCheck check = arrowquest.new ReverseArrowCheck();
		check.onTurnReached(3);
		assertEquals(true, arrowquest.player.isQuestCompleted("reverse_arrow"));
	}
}
