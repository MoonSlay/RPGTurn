interface GameInterface {
    fun startGame()
    fun endGame(winner: Character)
}

class Game : GameInterface {
    private val hero = Hero("Hero")
    private val enemy = Enemy("Goblin", 50, 15, 5)

    override fun startGame() {
        println("Battle Begins!")
        var isHeroTurn = true

        while (hero.hp > 0 && enemy.hp > 0) {
            println("\n--- New Turn ---")
            if (isHeroTurn) {
                hero.attack(enemy)
                // Addmore actions for the hero (e.g., heal)
            } else {
                enemy.attack(hero)
            }
            isHeroTurn = !isHeroTurn
        }

        endGame(if (hero.hp > 0) hero else enemy)
    }

    override fun endGame(winner: Character) {
        println("\n--- Game Over ---")
        println("${winner.name} wins!")
    }
}