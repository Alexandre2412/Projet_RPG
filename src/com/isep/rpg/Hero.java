package com.isep.rpg;

import com.isep.rpg.item.Weapon;

public abstract class Hero extends Combatant {

    // ouverture type nom = value
    protected Weapon weapon;
    public Hero(String n, int h) {
        super(n, h);
    }

    // Abstrait car n'importe quel hero peut prendre un objet mais son
    // utilisation dépend du type du héro (une arme n'est pas utile à un mage)

    public void take(Item item) {

    }
    @Override
    public void loose(int hp) {
        healthPoint -= hp/armor;
        // ... équivalant à : healthPoint = healthPoint - hp;
        if (armor!=1){
            System.out.println("Le bouclier a réduit les dégats par " + armor);

        }
    }
    public int armor = 1;
    public int getBouclier(){
        return armor;
    }
    public void addProtection(){
        armor = 2;
    }
    public void looseProtection(){
       armor = 1 ;
    }
    public void setWeapon(Weapon w) {
        weapon = w;

    }
    public void fight(Combatant combatant) {
        combatant.loose(weapon.getDamagePoints());
        System.out.println(this.getName() + " attaque " + combatant.getName() + "  | Dégats causés = " + weapon.getDamagePoints());
    }
}
