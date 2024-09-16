interface CharacterActions {
    fun attack(target: Character)
    fun defend(damage: Int)
    fun heal()
}

open class Character(
    var name: String,
    var hp: Int,
    varattackPower: Int,
    var defense: Int
) : CharacterActions {

    override fun attack(target: Character) {
        val damage = (attackPower - target.defense).coerceAtLeast(0)
        println("$name attacks ${target.name} for $damage damage!")
        target.defend(damage)
    }

    override fun defend(damage: Int) {
        hp -= damage
        println("$name takes $damage damage! HP: $hp")
    }

    override fun heal() {
        // Basic healing implementation (can be customized)val healAmount = 10
        hp += healAmount
        println("$name heals for $healAmount HP! HP: $hp")
    }
}