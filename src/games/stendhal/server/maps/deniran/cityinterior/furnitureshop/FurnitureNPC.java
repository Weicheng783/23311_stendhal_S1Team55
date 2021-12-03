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

import java.util.*;

import games.stendhal.common.grammar.Grammar;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.mapstuff.block.Furniture;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.NPCEmoteAction;
import games.stendhal.server.entity.npc.action.PlaySoundAction;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.behaviour.adder.BuyerAdder;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.BuyerBehaviour;
import games.stendhal.server.entity.npc.behaviour.impl.FurnitureSellerBehaviour;
import games.stendhal.server.entity.npc.condition.PlayerNextToCondition;
import games.stendhal.server.entity.player.Player;

public class FurnitureNPC implements ZoneConfigurator {
    private SpeakerNPC npc;
    private static FurnitureNPC instance;
    public StendhalRPZone sendZone;
    public int sendX;
    public int sendY;
    public Map<String, Integer> prices;

    @Override
    public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
        buildNPC(zone);
    }

    public Map<String, Integer> getPrices() {
        return prices;
    }

    private void buildNPC(final StendhalRPZone zone) {
        Furniture b = new Furniture("chair1");
        b.setPosition(9, 9);
        zone.add(b);

        npc = new SpeakerNPC("Hans the Joiner");

        final ShopList shops = SingletonRepository.getShopList();
        final Map<String, Integer> pricesSell = shops.get("deniranfurnituresell");
        prices = pricesSell;

        new SellerAdder().addSeller(npc, new FurnitureSellerBehaviour(this, pricesSell), false);

        npc.addGreeting("Welcome to Deniran's furniture store.");
        npc.addJob("I manage this furniture store. Ask me about my #prices.");
        npc.addHelp("If you would like to buy something, ask me about my #prices, and set your delivery location with #zone and #coords");

        npc.add(ConversationStates.ANY,
                Arrays.asList("price", "prices"),
                null,
                ConversationStates.ATTENDING,
                null,
                new ChatAction() {
                    @Override
                    public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
                        final int sellCount = pricesSell.size();

                        final StringBuilder sb = new StringBuilder("I sell");
                        int idx = 0;
                        for (final String itemName : pricesSell.keySet()) {
                            if (sellCount > 1 && idx == sellCount - 1) {
                                sb.append(" and");
                            }
                            sb.append(" ").append(Grammar.plural(itemName)).append(" for ").append(Integer.toString(pricesSell.get(itemName)));
                            if (sellCount > 1 && idx < sellCount - 1) {
                                sb.append(",");
                            }
                            idx++;
                        }

                        raiser.say(sb.toString());
                    }
                });

        npc.add(ConversationStates.ATTENDING, Collections.singleton("zone"), ConversationStates.ZONE_LOGGED, null, new ChatAction() {
            @Override
            public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
                String[] parts = sentence.getOriginalText().split(" ");
                if (parts.length != 2) {
                    npc.say("Sorry, I didn't get that1");
                    npc.setCurrentState(ConversationStates.ATTENDING);
                } else if (!Objects.equals(parts[0], "zone")) {
                    npc.say("Sorry, I didn't get that2");
                    npc.setCurrentState(ConversationStates.ATTENDING);
                } else {
                    StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(parts[1]);
                    if (zone == null) {
                        npc.say("Sorry, that's not a valid zone");
                        npc.setCurrentState(ConversationStates.ATTENDING);
                    } else {
                        sendZone = zone;
                        npc.say("OK, set zone to " + parts[1] + ". Now set coordinates using #coords");
                    }
                }

            }
        });

        npc.add(ConversationStates.ZONE_LOGGED, Collections.singleton("coords"), ConversationStates.BUY_PRICE_OFFERED, null, new ChatAction() {
            @Override
            public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
                String[] parts = sentence.getOriginalText().split(" ");
                if (parts.length != 3) {
                    npc.say("Sorry, I didn't get that1");
                    npc.setCurrentState(ConversationStates.ZONE_LOGGED);
                } else if (!Objects.equals(parts[0], "coords")) {
                    npc.say("Sorry, I didn't get that2");
                    npc.setCurrentState(ConversationStates.ZONE_LOGGED);
                } else if (!parts[1].matches("-?\\d+") && !parts[2].matches("-?\\d+")) {
                    npc.say("Sorry, those aren't valid coordinates");
                    npc.setCurrentState(ConversationStates.ZONE_LOGGED);
                } else {
                    if (Integer.parseInt(parts[1]) > sendZone.getWidth() || Integer.parseInt(parts[2]) > sendZone.getHeight()) {
                        npc.say("Sorry, that location is out of bounds");
                        npc.setCurrentState(ConversationStates.ZONE_LOGGED);
                    } else {
                        sendX = Integer.parseInt(parts[1]);
                        sendY = Integer.parseInt(parts[2]);
                        npc.say("OK, set coordinates to " + parts[1] + " " + parts[2] + ".");
                    }
                }

            }
        });

        npc.add(ConversationStates.ANY, ConversationPhrases.GOODBYE_MESSAGES,
                ConversationStates.IDLE, "Bye!", null);

        npc.setPosition(6, 5);
        npc.setOutfit(new Outfit("body=1,head=0,mouth=2,eyes=1,dress=46,mask=1,hair=3"));

        zone.add(npc);
    }

    public static FurnitureNPC getFurnitureDealerNPC() {
        if (instance != null) {
            return instance;
        } else {
            instance = new FurnitureNPC();
        }
        return instance;

    }

    public SpeakerNPC getNPC() {
        return npc;
    }
}