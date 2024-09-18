package ph.edu.auf.delrosario.angelo.rpgturn

class Assassin(
    name: String,
    maxHP: Int,
    hp: Int = maxHP,
    defense: Int,
    vitality: Int,
    agility: Int,
    attack: Int,
    luck: Int
) : Hero(name, maxHP, hp, defense, vitality, agility, attack, luck) {

    override fun NormalSkill(opponent: Character): Int {
        val damage = (attack * 1.3).toInt() // High damage but less healing
        opponent.hp -= damage
        println("$name uses Stealth Attack on ${opponent.name} for $damage damage.")
        return damage
    }

    override fun SpecialSkill(opponent: Character): Int {
        val damage = (attack * 1.5).toInt() // High damage
        opponent.hp -= damage
        println("$name uses Shadow Dagger on ${opponent.name} for $damage damage.")
        return damage
    }
}