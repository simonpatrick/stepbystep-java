package com.hedwig.dp.behavioral.flyweight;

public class PoisonPotion implements Potion {

	@Override
	public void drink() {
		System.out.println("Urgh! This is poisonous. (Potion="
				+ System.identityHashCode(this) + ")");
	}

}
