package com.test.amil.api.business.interfaces;

import java.io.FileNotFoundException;
import java.text.ParseException;

import com.test.amil.api.core.model.Match;

public interface GameLogBusiness {

	public void processLogRow(String logLine, Match match) throws ParseException, FileNotFoundException ;
	public void checkWinnerAndAwards(Match match);
}
