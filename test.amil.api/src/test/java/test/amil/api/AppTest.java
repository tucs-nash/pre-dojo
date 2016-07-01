package test.amil.api;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.test.amil.api.business.GameLogBusinessImpl;
import com.test.amil.api.business.interfaces.GameLogBusiness;
import com.test.amil.api.core.AppCore;
import com.test.amil.api.core.model.Match;
import com.test.amil.api.core.utils.AppUtils;

import junit.framework.TestCase;

public class AppTest {

	private GameLogBusiness business;
	
	@Before
	public void setUp() {
		this.business = new GameLogBusinessImpl(AppCore.getInstance().getProperties());
	}
	
	@After
	public void destroy() {
		this.business = null;
		AppCore.destroy();
	}
	
	@Test
	public void testLog() throws FileNotFoundException, ParseException {
		List<String> rows = new ArrayList<String>();
		rows.add("23/04/2013 15:34:22 - New match 11348965 has started");
		rows.add("23/04/2013 15:36:04 - Gabis killed Albert using M16");
		rows.add("23/04/2013 15:36:07 - Gabis killed Kelly using M16");
		rows.add("23/04/2013 15:36:08 - Gabis killed Jeh using AK47");
		rows.add("23/04/2013 15:36:08 - Jeh killed Gabis using AK47");
		rows.add("23/04/2013 15:37:01 - Gabis killed Nick using AK47");
		rows.add("23/04/2013 15:37:03 - Gabis killed Roman using AK47");
		rows.add("23/04/2013 15:45:35 - <WORLD> killed Albert by DROWN");
		rows.add("23/04/2013 15:49:22 - Match 11348965 has ended");
		
		Match match = new Match();
		for (String row : rows) {
			this.business.processLogRow(row, match);
		}
		
		this.business.checkWinnerAndAwards(match);
		
		Date start = AppUtils.convertStringToDateTime("23/04/2013 15:34:22");
		Date end = AppUtils.convertStringToDateTime("23/04/2013 15:49:22");
		
		TestCase.assertEquals(6, match.getPlayers().size());
		TestCase.assertEquals("11348965", match.getMatchCode());
		TestCase.assertEquals(start, match.getMatchStart());
		TestCase.assertEquals(end, match.getMatchEnd());
		
		TestCase.assertTrue(match.getPlayers().containsKey("Gabis"));
		TestCase.assertFalse(match.getPlayers().containsKey("<WORLD>"));
		
		TestCase.assertEquals("Gabis", match.getWinner().getNickname());
		TestCase.assertEquals(3, match.getWinner().getBiggestSequenceWithoutDying());
		TestCase.assertFalse(match.getWinner().isAwardDeathless());
		TestCase.assertTrue(match.getWinner().isAwardOneMinute());
		
		TestCase.assertEquals(5, match.getPlayers().get("Gabis").getKills().size());
		TestCase.assertEquals(2, match.getPlayers().get("Albert").getDeaths().size());
	}	
}
