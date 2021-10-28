package games.stendhal.server.entity.creature;

import org.junit.Test;
import marauroa.common.Log4J;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import utilities.PlayerTestHelper;
import marauroa.common.game.RPObject.ID;
import utilities.RPClass.CreatureTestHelper;
import games.stendhal.common.constants.Nature;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;

public class CentaurTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PlayerTestHelper.generateNPCRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
		CreatureTestHelper.generateRPClasses();
		MockStendhalRPRuleProcessor.get();
		Log4J.init();
		MockStendlRPWorld.get();
	}

	@Test
	public void testCentaurSusceptability() {
		double ice = 0;
		double solar = 0;
		
		Creature solarC = SingletonRepository.getEntityManager().getCreature("solar centaur");
		solarC.setID(new ID(1, "solar_test"));
		for (Nature n1 : Nature.values())
			if (n1 == Nature.parse("ice"))
				ice = solarC.getSusceptibility(n1);
			else if (n1 == Nature.parse("fire"))
				solar = solarC.getSusceptibility(n1);
		assertTrue(ice > solar);
		
		Creature iceC = SingletonRepository.getEntityManager().getCreature("glacier centaur");
		iceC.setID(new ID(2, "glacier_test"));
		for (Nature n2 : Nature.values())
			if (n2 == Nature.parse("ice"))
				ice = iceC.getSusceptibility(n2);
			else if (n2 == Nature.parse("fire"))
				solar = iceC.getSusceptibility(n2);
		assertTrue(ice < solar);
	}

}