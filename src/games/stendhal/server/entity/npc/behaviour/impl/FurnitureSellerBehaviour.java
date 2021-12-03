package games.stendhal.server.entity.npc.behaviour.impl;

import games.stendhal.common.grammar.Grammar;
import games.stendhal.common.grammar.ItemParserResult;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.mapstuff.block.Furniture;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.deniran.cityinterior.furnitureshop.FurnitureNPC;

import java.util.Map;

public class FurnitureSellerBehaviour extends SellerBehaviour{

    FurnitureNPC NPC;

    public FurnitureSellerBehaviour(FurnitureNPC npc, final Map<String, Integer> priceList) {
        super(priceList);
        NPC = npc;
    }

    @Override
    public boolean transactAgreedDeal(ItemParserResult res, final EventRaiser seller, final Player player) {
        String chosenItemName = res.getChosenItemName();
        int amount = res.getAmount();

        if (amount != 1) {
            seller.say("Sorry, you can only buy 1 furniture item at a time");
            return false;
        }

        int price = getCharge(res, player);
        if (player.isBadBoy()) {
            price = (int) (BAD_BOY_BUYING_PENALTY * price);
        }

        if (player.isEquipped("money", price)) {
                player.drop("money", price);

                seller.say(Integer.toString(NPC.sendX));
                seller.say(Integer.toString(NPC.sendY));

                Furniture b = new Furniture(chosenItemName);
                b.setPosition(NPC.sendX, NPC.sendY);
                NPC.sendZone.add(b);

                seller.say("Congratulations! I have delivered your furniture.");
                player.incBoughtForItem(chosenItemName, amount);
                return true;
        } else {
            seller.say("Sorry, you don't have enough money!");
            return false;
        }
    }
}
