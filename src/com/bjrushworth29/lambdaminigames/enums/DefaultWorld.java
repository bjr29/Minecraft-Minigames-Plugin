package com.bjrushworth29.lambdaminigames.enums;

public enum DefaultWorld {
	HUB("world_hub"),
	SUMO("world_sumo"),
	GAMES("world_void"),
	DUELS("world_duels");

	private final String value;

	DefaultWorld(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
