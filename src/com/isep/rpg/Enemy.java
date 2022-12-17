package com.isep.rpg;

public abstract class Enemy extends Combatant {

    protected int damage;
    public Enemy(String name, int hp, int damage) {
        super(name, hp);
        this.damage = damage;
    }

    @Override
    public void fight(Combatant combatant) {
        combatant.loose(damage);
        System.out.println(this.getName() + " attaque ⚔ " + combatant.getName() + "  | Dégats subies = " + damage/((Hero) combatant).getBouclier());
    }

}


