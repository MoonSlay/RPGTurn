package ph.edu.auf.delrosario.angelo.rpgturn

import kotlin.random.Random

class Hero(
    name: String,
    maxHP: Int,
    hp: Int = maxHP,
    defense: Int,
    vitality: Int,
    agility: Int,
    attack: Int,
    luck: Int,
    var level: Int = 1,          // Hero starts at level 1
    var experience: Int = 0,     // Hero starts with 0 XP
    var experienceToNextLevel: Int = 100 // XP needed for the next level
) : Character(name, maxHP, hp, defense,vitality, agility, attack, luck) {


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
        attack += 5               // Increase attack power
        defense += 3                   // Increase defense
        luck += 1                      // Increase luck
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

    fun biteAttack(opponent: Character): Int {
        val damage = (attack * 0.8).toInt()
        val healedHP = (damage * 0.2).toInt() // Heal 20% of damage dealt
        opponent.hp -= damage
        hp += healedHP
        println("$name uses Bite Attack on ${opponent.name} for $damage damage and heals $healedHP HP.")
        return damage
    }

    fun bloodSiphon(opponent: Character): Int {
        val damage = (attack * 1.5).toInt()
        val healedHP = (damage * 0.5).toInt() // Heal 50% of damage dealt
        opponent.hp -= damage
        hp += healedHP
        println("$name uses Blood Siphon on ${opponent.name} for $damage damage and heals $healedHP HP.")
        return damage
    }

}
