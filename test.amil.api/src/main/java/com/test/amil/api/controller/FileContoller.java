package com.test.amil.api.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.test.amil.api.business.GameLogBusinessImpl;
import com.test.amil.api.business.interfaces.GameLogBusiness;
import com.test.amil.api.core.model.Match;
import com.test.amil.api.core.model.PlayerResult;

/**
 * Classe responsável pela manipulação do arquivo
 * @author tbdea
 *
 */
public class FileContoller {

	private GameLogBusiness gameLogBusiness;
	
	public FileContoller(Properties props) {
		this.gameLogBusiness = new GameLogBusinessImpl(props);
	}
	
	/**
	 * Lê o arquivo, processa e imprimi o Ranking
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public void processFile(String filePath) throws FileNotFoundException, ParseException {
		Match match = new Match();

		Scanner fileScanner = new Scanner(new File(filePath));
		
		while (fileScanner.hasNext()) {
			String logLine = fileScanner.nextLine();
			gameLogBusiness.processLogRow(logLine, match);
		}
		fileScanner.close();

		gameLogBusiness.checkWinnerAndAwards(match);
		printRanking(match);
		
	}
	
	/**
	 * Imprimi o Ranking
	 * @param players
	 * @param gameLogBusiness 
	 */
	private void printRanking(Match match) {		
		List<PlayerResult> listPlayers = new ArrayList<PlayerResult>(match.getPlayers().values());
		Collections.sort(listPlayers, new Comparator<PlayerResult>() {
			public int compare(PlayerResult o1, PlayerResult o2) {
				return o2.getOrderParam() - o1.getOrderParam();
			}
		});
		
		//remover o vencedor
		listPlayers.remove(0);
		System.out.println(match.toString());
		System.out.println("________________________________________________________");
		System.out.println("Winner "+match.getWinner().getNickname()
							+ "	|| Kills: " + match.getWinner().getKills().size() 
							+ "	|| Deaths: " + match.getWinner().getDeaths().size()
							+ "	|| Favourites Weapons: " + match.getWinner().getFavouritesWeapons()
							+ "	|| Best Sequence: " + match.getWinner().getBiggestSequenceWithoutDying()
							+ "	|| Award Deathless: " + awardString(match.getWinner().isAwardDeathless())
							+ "	|| Award One Minute: " + awardString(match.getWinner().isAwardOneMinute())
		);
		System.out.println("________________________________________________________");
		for (PlayerResult player : listPlayers) {
			System.out.println("Player "+player.getNickname()
								+ "	|| Kills: " + player.getKills().size() 
								+ "	|| Deaths: " + player.getDeaths().size()
								+ "	|| Best Sequence: " + player.getBiggestSequenceWithoutDying()
								+ "	|| Award One Minute: " + awardString(player.isAwardOneMinute())
								);
		}
	}
	
	/**
	 * Retorna se recebeu o 'award' ou não 
	 * @param award
	 * @return
	 */
	private String awardString(boolean award) {
		return award ? "award" : "no";
	}
}
