package ph.edu.auf.delrosario.angelo.rpgturn

class Vampire(
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
        val damage = (attack * 1.0).toInt()
        val healedHP = (damage * 0.4).toInt()
        opponent.hp -= damage
        hp += healedHP
        println("$name uses Enhanced Bite Attack on ${opponent.name} for $damage damage and heals $healedHP HP.")
        return damage
    }

    override fun SpecialSkill(opponent: Character): Int {
        val damage = (attack * 1.7).toInt()
        val healedHP = (damage * 0.6).toInt()
        opponent.hp -= damage
        hp += healedHP
        println("$name uses Enhanced Blood Siphon on ${opponent.name} for $damage damage and heals $healedHP HP.")
        return damage
    }
}
