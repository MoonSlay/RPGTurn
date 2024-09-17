package ph.edu.auf.delrosario.angelo.rpgturn

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var hero: Hero
    private lateinit var enemy: Enemy
    private lateinit var tvHeroStats: TextView
    private lateinit var tvEnemyStats: TextView
    private lateinit var tvGameLog: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the hero and enemy
        hero = Hero("Hero", 100, 20, 15)
        enemy = Enemy("Enemy", 80, 15, 12)

        // Hook UI elements
        tvHeroStats = findViewById(R.id.tvHeroStats)
        tvEnemyStats = findViewById(R.id.tvEnemyStats)
        tvGameLog = findViewById(R.id.tvGameLog)

        val btnAttack = findViewById<Button>(R.id.btnAttack)
        val btnDefend = findViewById<Button>(R.id.btnDefend)
        val btnHeal = findViewById<Button>(R.id.btnHeal)

        // Update stats views
        updateStats()

        // Set button listeners for actions
        btnAttack.setOnClickListener {
            val damage = hero.attack(enemy)
            tvGameLog.text = "Hero attacked Enemy for $damage damage."
            checkGameOver()
            enemyTurn()
        }

        btnDefend.setOnClickListener {
            hero.defend()
            tvGameLog.text = "Hero is defending."
            enemyTurn()
        }

        btnHeal.setOnClickListener {
            val healAmount = hero.heal()
            tvGameLog.text = "Hero healed for $healAmount HP."
            enemyTurn()
        }
    }

    // Update Hero and Enemy stats on the screen
    private fun updateStats() {
        tvHeroStats.text = "Hero: HP=${hero.hp}, DEF=${hero.defense}, ATK=${hero.attackPower}"
        tvEnemyStats.text = "Enemy: HP=${enemy.hp}, DEF=${enemy.defense}, ATK=${enemy.attackPower}"
    }

    // Check if the game is over
    private fun checkGameOver() {
        if (enemy.hp <= 0) {
            tvGameLog.text = "You defeated the enemy!"
        } else if (hero.hp <= 0) {
            tvGameLog.text = "You were defeated!"
        } else {
            updateStats()
        }
    }

    // Simulate the enemy's turn
    private fun enemyTurn() {
        if (enemy.hp > 0) {
            val randomAction = (1..3).random()
            when (randomAction) {
                1 -> {
                    val damage = enemy.attack(hero)
                    tvGameLog.append("\nEnemy attacked Hero for $damage damage.")
                }
                2 -> {
                    enemy.defend()
                    tvGameLog.append("\nEnemy is defending.")
                }
                3 -> {
                    val healAmount = enemy.heal()
                    tvGameLog.append("\nEnemy healed for $healAmount HP.")
                }
            }
            checkGameOver()
        }
    }
}