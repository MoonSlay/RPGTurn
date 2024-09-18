package ph.edu.auf.delrosario.angelo.rpgturn

import kotlin.random.Random

class Enemy(
    name: String,
    maxHP: Int,
    hp: Int = maxHP,
    defense: Int,
    attackPower: Int,
    luck: Int,
    evasion: Int
) : Character(name, maxHP, hp, defense, attackPower, luck, evasion) {
    override fun swiftCut(opponent: Character): Int {
        val damage = (attackPower * 1.2).toInt() - opponent.defend()
        opponent.hp -= damage
        println("$name performs Swift Cut on ${opponent.name} for $damage damage.")
        return damage
    }

    override fun forwardSlash(opponent: Character): Int {
        val crit = Random.nextInt(0, 3)
        val luckcrit = luck * 0.1 + crit
        val damage = attackPower * luckcrit.toInt() - opponent.defend()
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
        val healAmount = Random.nextInt(2, 6)
        val healedHP = if (hp + healAmount > maxHP) {
            maxHP - hp
        }
        else {
            healAmount
        }
        hp += healedHP
        println("$name heals for $healedHP HP.")
        return healedHP
    }

}

