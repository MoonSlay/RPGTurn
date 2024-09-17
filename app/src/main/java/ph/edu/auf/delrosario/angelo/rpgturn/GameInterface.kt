package ph.edu.auf.delrosario.angelo.rpgturn

// Game.kt
import kotlin.random.Random

interface GameInterface {
    fun playTurn(hero: Hero, enemy: Enemy)
}

class RPGGame : GameInterface {

    override fun playTurn(hero: Hero, enemy: Enemy) {
        // Hero's turn
        println("Choose action: 1. Attack 2. Defend 3. Heal")
        val action = readLine()?.toIntOrNull()

        when (action) {
            1 -> hero.attack(enemy)
            2 -> hero.defend()
            3 -> hero.heal()
            else -> println("Invalid action!")
        }

        if (enemy.hp <= 0) {
            println("${enemy.name} is defeated! You win!")
            return
        }

        // Enemy's turn (random action)
        val enemyAction = Random.nextInt(1, 4)
        when (enemyAction) {
            1 -> enemy.attack(hero)
            2 -> enemy.defend()
            3 -> enemy.heal()
        }

        if (hero.hp <= 0) {
            println("${hero.name} is defeated! You lose!")
        }
    }
}
