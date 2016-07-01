package com.test.amil.api.business;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.test.amil.api.business.interfaces.GameLogBusiness;
import com.test.amil.api.core.model.Match;
import com.test.amil.api.core.model.PlayerResult;
import com.test.amil.api.core.utils.AppUtils;

/**
 * Lendo log do jogo e aplicando as regras para geração do Ranking
 * @author tbdea
 */
public class GameLogBusinessImpl implements GameLogBusiness {

	private String matchStartPropBegin;
	private String matchStartPropEnd;
	private String matchClosePropBegin;
	private String matchClosePropEnd;
	private String killedKeyword;
	private String weaponKeyword;
	private String worldKeyword;
	private String byWorldKeyword;
	
	public GameLogBusinessImpl(Properties props) {
		this.matchStartPropBegin = props.getProperty("amil.log.match.start.begin");
		this.matchStartPropEnd = props.getProperty("amil.log.match.start.end");
		this.matchClosePropBegin = props.getProperty("amil.log.match.close.begin");
		this.matchClosePropEnd = props.getProperty("amil.log.match.close.end");
		this.killedKeyword = props.getProperty("amil.log.killed.keyword");
		this.weaponKeyword = props.getProperty("amil.log.weapon.keyword");
		this.worldKeyword = props.getProperty("amil.log.world.keyword");
		this.byWorldKeyword = props.getProperty("amil.log.by.keyword");
	}

	/**
	 * Lendo e gerando resultado por linha do arquivo
	 * @param filePath
	 * @return
	 * @throws ParseException
	 * @throws FileNotFoundException
	 */
	public void processLogRow(String logLine, Match match) throws ParseException, FileNotFoundException {
		String[] splitTimeAction = logLine.split("-");
		
		Date time = AppUtils.convertStringToDateTime(AppUtils.applyingValueHandling(splitTimeAction[0]));
		String action = AppUtils.applyingValueHandling(splitTimeAction[1]);
		
		if (action.startsWith(matchStartPropBegin) && action.endsWith(matchStartPropEnd)) {
			String code = action.replace(matchStartPropBegin, "").replace(matchStartPropEnd, "");

			match.setMatchStart(time);
			match.setMatchCode(AppUtils.applyingValueHandling(code));
		} else if (action.startsWith(matchClosePropBegin) && action.endsWith(matchClosePropEnd)) {
			match.setMatchEnd(time);
		} else {
			readingAction(match, action, time);
		}
	}
	

	/**
	 * Verificar o vencedor
	 * @param player
	 */
	public void checkWinnerAndAwards(Match match) {
		PlayerResult winner = null;
		for(PlayerResult player : match.getPlayers().values()) {
			checkPlayerSequenceWithoutDying(player);			
			player.setAwardOneMinute(checkOneMinuteAward(new ArrayList<Date>(player.getKills())));
			
			if (winner == null || winner.getOrderParam() < player.getOrderParam()) {
				winner = player;
			}
		}
		winner.setAwardDeathless(winner.getDeaths().size() == 0);
		match.setWinner(winner);
	}
	
	/**
	 * Verifica sequência de mortes sem morrer do jogador
	 * @param player
	 */
	private void checkPlayerSequenceWithoutDying(PlayerResult player) {
		if(player.getBiggestSequenceWithoutDying() < player.getSequenceWithoutDying()) {
			player.setBiggestSequenceWithoutDying(player.getSequenceWithoutDying());
			player.setSequenceWithoutDying(0);
		}
	}

	
	/**
	 * Verificar o prêmio de um minuto
	 * @param kills
	 * @return
	 */
	private boolean checkOneMinuteAward(List<Date> kills) {
		if(kills.size() < 5) {
			return false;			
		}
		Date start = kills.get(0);
		Date fifthKill = kills.get(4);
		long diff = fifthKill.getTime() - start.getTime();
		
		if(diff > 60000) {
			kills.remove(0);
			return checkOneMinuteAward(kills);
		}
		return true;
	}

	/**
	 * Analisando a ação para resgatar os resultados 
	 * @param players
	 * @param utils
	 * @param action
	 * @param time 
	 * @param killedKeyword
	 * @param worldKeyword
	 * @param byWorldKeyword
	 * @param weaponKeyword
	 */
	private void readingAction(Match match, String action, Date time) {
		String[] splitAction = action.split(killedKeyword);
		
		String killerNick = AppUtils.applyingValueHandling(splitAction[0]);
		String deathInformation = AppUtils.applyingValueHandling(splitAction[1]);
		String deadNick = null;

		if (killerNick.equals(worldKeyword)) {
			deadNick = AppUtils.applyingValueHandling(deathInformation.substring(0, deathInformation.indexOf(byWorldKeyword)));
		} else {
			String[] splitDeathInformation = deathInformation.split(weaponKeyword);
			
			PlayerResult killer = getPlayerResultByNickname(match, killerNick);
			killer.getKills().add(time);
			killer.addSequenceWithoutDying();
			killer.addWeapons(AppUtils.applyingValueHandling(splitDeathInformation[1]));
			
			deadNick = AppUtils.applyingValueHandling(splitDeathInformation[0]);
		}
		
		PlayerResult dead = getPlayerResultByNickname(match, deadNick);
		dead.getDeaths().add(time);
		checkPlayerSequenceWithoutDying(dead);
	}
	
	/**
	 * Resgata o jogador pelo seu nickname
	 * @param players
	 * @param nickname
	 * @return
	 */
	private PlayerResult getPlayerResultByNickname(Match match, String nickname) {
		PlayerResult player = match.getPlayers().get(nickname);
		
		if(player == null) {
			player = new PlayerResult(nickname);
			match.addPlayer(nickname, player);
		}
		
		return player;
	}
}
