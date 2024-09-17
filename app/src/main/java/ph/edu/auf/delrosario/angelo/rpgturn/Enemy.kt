package ph.edu.auf.delrosario.angelo.rpgturn

class Enemy(
    name: String,
    hp: Int,
    defense: Int,
    attackPower: Int,
    luck: Int,
    evasion: Int
) : Character(name, hp, defense, attackPower, luck, evasion) {

    override fun attack(opponent: Character): Int {
        val damage = attackPower - opponent.defend()
        opponent.hp -= damage
        println("$name attacks ${opponent.name} for $damage damage.")
        return damage
    }

    fun swiftCut(opponent: Character): Int {
        val damage = (attackPower * 1.2).toInt() - opponent.defend()
        opponent.hp -= damage
        println("$name performs Swift Cut on ${opponent.name} for $damage damage.")
        return damage
    }

    fun forwardSlash(opponent: Character): Int {
        val damage = (attackPower * 1.5).toInt() - opponent.defend()
        opponent.hp -= damage
        println("$name performs Forward Slash on ${opponent.name} for $damage damage.")
        return damage
    }

    override fun defend(): Int {
        val reducedDamage = (defense * 0.4).toInt()
        println("$name defends and reduces damage by $reducedDamage.")
        return reducedDamage
    }

    override fun heal(): Int {
        val healAmount = (hp * 0.2).toInt()
        hp += healAmount
        println("$name heals for $healAmount HP.")
        return healAmount
    }

    fun getStats(): String {
        return "Name: $name\nHP: $hp\nAttack: $attackPower\nDefense: $defense\nLuck: $luck\nEvasion: $evasion"
    }
}
