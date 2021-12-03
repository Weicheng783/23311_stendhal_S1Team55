package games.stendhal.server.entity.item;

import java.util.*;

import games.stendhal.common.NotificationType;
import games.stendhal.server.core.engine.PlayerList;
import games.stendhal.server.core.engine.StendhalRPRuleProcessor;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.PrivateTextEvent;


public class newspaper extends OwnedItem {

	public newspaper(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		setMenu("Read|Use");
	}

	public newspaper(final newspaper item) {
		super(item);
	}

	@Override
	public boolean onUsed(final RPEntity user) {
		if (!(user instanceof Player)) {
			return false;
		}

		final Player player = (Player) user;

		if (!super.onUsed(player)) {
			player.sendPrivateText(NotificationType.CLIENT, "this is  " + getOwner() + "'s newspaper");
			return false;
		}


		PlayerList list = StendhalRPRuleProcessor.get().getOnlinePlayers();


		player.addEvent(new PrivateTextEvent(NotificationType.CLIENT, String.valueOf(list.getAllPlayers())));



		int level = 0;
		Player NewsPlayer = null;
		for (Player p : list.getAllPlayers()) {

			if(p.getLevel()>=level)
			{
				level = p.getLevel();
				NewsPlayer = p;
			}
		} 



		//newspaper report kill which monster when you read the newspaper in text.
		player.addEvent(new PrivateTextEvent(NotificationType.CLIENT, "newspaper"+ "\n"));
			player.addEvent(new PrivateTextEvent(NotificationType.CLIENT, "this monster killed by:"
					+NewsPlayer.getName() + NewsPlayer.describe().substring(8 + NewsPlayer.getName().length())
					+"\n" + "The monster he killed is : " + "rat" + "\n"
			));


			player.notifyWorldAboutChanges();

			return true;
		}

	@Override
	public void setOwner(final String name) {
		put("search", name);
	}

	@Override
	public String getOwner() {
		return get("search");
	}
}