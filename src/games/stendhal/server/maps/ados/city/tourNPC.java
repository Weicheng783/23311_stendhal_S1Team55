/***************************************************************************
 *                   (C) Copyright 2003-2013 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.ados.city;

//import java.util.Arrays;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.core.events.UseListener;
//import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.SpeakerNPC;
//import games.stendhal.server.entity.RPEntity;
//import games.stendhal.server.entity.npc.SilentNPC;

/**
 * A mysterious mage that gives tours
 *
 * @author j41395dr
 */
public class tourNPC implements ZoneConfigurator {

	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		
		final SpeakerNPC npc = new SpeakerNPC("Mysterious Mage") {
			@Override
            protected void createPath() {
//                List<Node> nodes=new LinkedList<Node>();
//                nodes.add(new Path.Node(9,5));
//                nodes.add(new Path.Node(14,5));
//                setPath(nodes,true);
            	setPath(null);
            }
            
            @Override
            protected void createDialog() {
                // Lets the NPC reply with "Hallo" when a player greets him. But we could have set a custom greeting inside the ()
                addGreeting("Hello there traveller, I am offering #tour of the local area if you are interested?");
                // Lets the NPC reply when a player says "job"
                addJob("I'm a simple man making his way through the galaxy. Like my father before me. ");
                // Lets the NPC reply when a player asks for help
                addHelp("I can help you with information about the local area");
                // respond about a special trigger word
                addReply("tour","Welp, let's get started then!.");
                // use standard goodbye, but you can also set one inside the ()
                addGoodbye("Goodbye my friend!");
            }
        };
		
		npc.setPosition(34, 80);
		npc.setDescription("You see a mage who seems very knowledgable");
		npc.setEntityClass("oldwizardnpc");
		npc.initHP(100);
		zone.add(npc);
	}

}
