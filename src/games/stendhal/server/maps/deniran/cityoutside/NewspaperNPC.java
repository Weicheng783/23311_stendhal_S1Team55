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
package games.stendhal.server.maps.deniran.cityoutside;

import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Outfit;
//import games.stendhal.server.core.pathfinder.FixedPath;
//import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;

public class NewspaperNPC implements ZoneConfigurator {
	private final ShopList shops = SingletonRepository.getShopList();

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		newspaperNPC(zone);
	}

	private void newspaperNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Gareth") {

			@Override
			protected void createDialog() {
				addGreeting("Newspaper! Newspaper!");
				addJob("I am a newspaper hawker who sells #newspapers. Just ask me for an #offer!");
				//get newspapers here
				new SellerAdder().addSeller(this, new SellerBehaviour(shops.get("scrolls")));

				add(
				        ConversationStates.ATTENDING,
				        Arrays.asList("news", "newspaper", "News", "Newspaper"),
				        null,
				        ConversationStates.ATTENDING,
				        "I #offer newspaper that help you to know what happen in game: #'newspaper'.",
				        null);
				add(ConversationStates.ATTENDING, Arrays.asList("news", "newspaper"), null,
				        ConversationStates.ATTENDING,
				        "Provide information about current events!", null);

				addGoodbye();
			}
		};

		npc.setOutfit(new Outfit(0, 0, 0, 0, 0));
		npc.setPosition(5, 4);
		npc.initHP(100);
		npc.setDescription("You can buy newspaper from him.");
		zone.add(npc);
	}
}
