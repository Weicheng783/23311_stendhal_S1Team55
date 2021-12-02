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

import java.util.Map;

import games.stendhal.common.parser.Sentence;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;

import games.stendhal.server.entity.Entity;

import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;

import games.stendhal.server.entity.player.Player;

/**
 * A mysterious mage that gives tours
 *
 * @author j41395dr
 */
public class tourNPC implements ZoneConfigurator {
	
	Integer pointer = 0;
	
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		
		final SpeakerNPC npc = new SpeakerNPC("Mysterious Mage") {
			@Override
            protected void createPath() {
            	setPath(null);
            }
			
			 final ChatCondition startofTour = new ChatCondition() {
					@Override
					public boolean fire(final Player player, final Sentence sentence, final Entity npc) {
						if (pointer == 0) {
							return true;
						} else {
							return false;
						}
					}
				};
            
            @Override
            protected void createDialog() {
                addGreeting("Hello there traveller, I am offering a #tour of the local area if you are interested?");
                addJob("I'm a simple man making his way through the galaxy. Like my father before me. ");
                addHelp("I can help you with information about the local area");
                add(ConversationStates.ATTENDING, "tour", null, ConversationStates.QUESTION_1, null,
				        new ChatAction() {
					        @Override
							public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {		        	
					        		startTour(player, npc);
					        	
					        }
				        });
                add(ConversationStates.QUESTION_1, "continue", null, ConversationStates.QUESTION_2, null,
                		new ChatAction() {
                			@Override
                			public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {		        	
                					stage2(player, npc);
                			}
                });
             
                addGoodbye("Goodbye my friend!");
            }
        };
        
		
		npc.setPosition(34, 80);
		npc.setDescription("You see a mage who seems very knowledgable");
		npc.setEntityClass("oldwizardnpc");
		npc.initHP(100);
		zone.add(npc);
	}
	
	private void startTour(Player player, EventRaiser npc) {
		
		Entity entity = npc.getEntity();
		
		player.setInvisible(true);
		
		entity.setPosition(45, 60);
		player.teleport("0_ados_city", 45,59,null,null);
		
		npc.say("To the left is Ados' sewing room, within is Ida who is a seamstress and she makes sails!");
		npc.say("To the right is a house with a funny number hehe, I do know how to make the kids laugh");
		npc.say("Tell me when you want to #continue");
		
		
	}
	
	private void stage2(Player player, EventRaiser npc) {
		Entity entity = npc.getEntity();
		
		entity.setPosition(40, 60);
		player.teleport("0_ados_city", 40,59,null,null);
	}
	
}
