package com.isep.rpg;

public abstract class Combatant {

    public Combatant(String n, int h) {
        name = n;
        healthPoint = h;
    }

    public String getName() {
        return name;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void loose(int hp) {
        healthPoint -= hp;
        // ... équivalant à : healthPoint = healthPoint - hp;
    }

    // Chaque "vrai" combatant (non "abstract") implémente un code différent
    // pour la méthode "fight"
    public abstract void fight(Combatant combatant);

    private final String name;
    protected int healthPoint;

}