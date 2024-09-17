package ph.edu.auf.delrosario.angelo.rpgturn

import kotlin.random.Random

class Hero(
    name: String,
    maxHP: Int,
    hp: Int = maxHP,
    defense: Int,
    attackPower: Int,
    luck: Int,
    evasion: Int,
    var level: Int = 1,          // Hero starts at level 1
    var experience: Int = 0,     // Hero starts with 0 XP
    var experienceToNextLevel: Int = 100 // XP needed for the next level
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

    fun levelUp() {
        level++
        experienceToNextLevel += 100 // Increase XP requirement for next level
        hp += 20                      // Increase max HP
        attackPower += 5               // Increase attack power
        defense += 3                   // Increase defense
        luck += 1                      // Increase luck
        evasion += 1                   // Increase evasion
        println("Hero leveled up to Level $level!")
    }

    // Add experience points
    fun gainExperience(xp: Int) {
        experience += xp
        if (experience >= experienceToNextLevel) {
            experience -= experienceToNextLevel
            levelUp()
        }
    }

}
