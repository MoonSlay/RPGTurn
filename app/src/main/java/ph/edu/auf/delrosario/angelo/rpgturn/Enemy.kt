package ph.edu.auf.delrosario.angelo.rpgturn

// Enemy.kt
class Enemy(name: String, hp: Int, defense: Int, attackPower: Int) : Character(name, hp, defense, attackPower) {

    override fun attack(opponent: Character): Int {
        val damage = attackPower - opponent.defend()
        opponent.hp -= damage
        println("$name attacks ${opponent.name} for $damage damage.")
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
}
