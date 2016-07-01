package com.test.amil.api.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Classe do Jogador
 * @author tbdea
 *
 */
public class PlayerResult {

	private String nickname;
	private List<Date> kills;
	private List<Date> deaths;
	
	private boolean awardDeathless;
	private boolean awardOneMinute;
	
	private int sequenceWithoutDying;
	private int biggestSequenceWithoutDying;

	private Date oneMinuteStart;
	private int oneMinuteKills;

	private HashMap<String, Integer> weapons;
	
	public PlayerResult() {
		this.kills = new ArrayList<Date>();
		this.deaths = new ArrayList<Date>();
	}
	
	public PlayerResult(String nickname) {
		this();
		this.nickname = nickname;

	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public List<Date> getKills() {
		return kills;
	}
	public void setKills(List<Date> kills) {
		this.kills = kills;
	}
	public List<Date> getDeaths() {
		return deaths;
	}
	public void setDeaths(List<Date> amountDeaths) {
		this.deaths = amountDeaths;
	}

	public boolean isAwardDeathless() {
		return awardDeathless;
	}

	public void setAwardDeathless(boolean awardDeathless) {
		this.awardDeathless = awardDeathless;
	}

	public boolean isAwardOneMinute() {
		return awardOneMinute;
	}

	public void setAwardOneMinute(boolean awardOneMinute) {
		this.awardOneMinute = awardOneMinute;
	}

	public int getSequenceWithoutDying() {
		return sequenceWithoutDying;
	}
	public void setSequenceWithoutDying(int sequenceWithoutDying) {
		this.sequenceWithoutDying = sequenceWithoutDying;
	}

	public void addSequenceWithoutDying() {
		this.sequenceWithoutDying++;
	}
	public Date getOneMinuteStart() {
		return oneMinuteStart;
	}

	public void setOneMinuteStart(Date oneMinuteStart) {
		this.oneMinuteStart = oneMinuteStart;
	}

	public int getBiggestSequenceWithoutDying() {
		return biggestSequenceWithoutDying;
	}
	public void setBiggestSequenceWithoutDying(int biggestSequenceWithoutDying) {
		this.biggestSequenceWithoutDying = biggestSequenceWithoutDying;
	}
	public int getOneMinuteKills() {
		return oneMinuteKills;
	}
	public void setOneMinuteKills(int oneMinuteKills) {
		this.oneMinuteKills = oneMinuteKills;
	}
	public void addOneMinuteKills() {
		this.oneMinuteKills++;
	}
	public HashMap<String, Integer> getWeapons() {
		return weapons;
	}
	public void setWeapons(HashMap<String, Integer> weapons) {
		this.weapons = weapons;
	}
	public void addWeapons(String weapon) {
		if (this.weapons == null) {
			this.weapons = new HashMap<String, Integer>();
			this.weapons.put(weapon, 1);
		} else {
			Integer amount = this.weapons.get(weapon);
			
			if (amount == null) {
				this.weapons.put(weapon, 1);
			} else {
				this.weapons.put(weapon, ++amount);
			}
		}
	}

	/**
	 * Resgata as armas mais usadas pelo jogador
	 * @return
	 */
	public String getFavouritesWeapons() {
		if (this.weapons == null) {
			return "";
		}
		
		String favouritesWeapons = null;
		Integer moreUsed = 0;
		for (String weapon : this.weapons.keySet()) {
			Integer amountWeapon = this.weapons.get(weapon);
		
			if (amountWeapon > moreUsed) {
				favouritesWeapons = weapon;
				moreUsed = amountWeapon;
			} else if (amountWeapon.equals(moreUsed)) {
				favouritesWeapons = favouritesWeapons.concat(",").concat(weapon);				
			}
		}
		return favouritesWeapons;
	}
	
	/**
	 * Parametro usado para identificar a order do ranking (Do vencedor para o perdedor)
	 * @return
	 */
	public int getOrderParam() {
		return this.kills.size();
	}
}
