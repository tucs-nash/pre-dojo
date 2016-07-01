package com.test.amil.api.core.model;

import java.util.Date;
import java.util.HashMap;

/**
 * Classe da Partida
 * @author tbdea
 *
 */
public class Match {

	private String matchCode;
	private Date matchStart;
	private Date matchEnd;
	
	private PlayerResult winner;
	private HashMap<String, PlayerResult> players;
	
	public Match() {
		this.players = new HashMap<String, PlayerResult>();
	}

	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	public Date getMatchStart() {
		return matchStart;
	}
	public void setMatchStart(Date matchStart) {
		this.matchStart = matchStart;
	}
	public Date getMatchEnd() {
		return matchEnd;
	}
	public void setMatchEnd(Date matchEnd) {
		this.matchEnd = matchEnd;
	}
	
	public PlayerResult getWinner() {
		return winner;
	}

	public void setWinner(PlayerResult winner) {
		this.winner = winner;
	}

	public void addPlayer(String key, PlayerResult result) {
		this.players.put(key, result);
	}
	
	public HashMap<String, PlayerResult> getPlayers() {
		return players;
	}

	public void setPlayers(HashMap<String, PlayerResult> players) {
		this.players = players;
	}

	@Override
	public String toString() {
		return "Match "
				.concat(matchCode)
				.concat(" between ")
				.concat(matchStart.toString())
				.concat(" and ")
				.concat(matchEnd.toString());
	}
}
