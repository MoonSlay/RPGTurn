package ph.edu.auf.delrosario.angelo.rpgturn

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.content.Intent

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
        hero = Hero("Hero", 100, 20, 15, 10, 5, 5)
        enemy = Enemy("Enemy", 80, 15, 12, 8, 4, 6)

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
            appendToGameLog("Hero attacked Enemy for $damage damage.")
            checkGameOver()
            enemyTurn()
        }

        btnDefend.setOnClickListener {
            hero.defend()
            appendToGameLog("Hero is defending.")
            enemyTurn()
        }

        btnHeal.setOnClickListener {
            val healAmount = hero.heal()
            appendToGameLog("Hero healed for $healAmount HP.")
            enemyTurn()
        }
    }

    private fun updateStats() {
        tvHeroStats.text = "Hero: HP=${hero.hp}, DEF=${hero.defense}, ATK=${hero.attackPower}, MR=${hero.magicResistance}, LUCK=${hero.luck}, EVA=${hero.evasion}"
        tvEnemyStats.text = "Enemy: HP=${enemy.hp}, DEF=${enemy.defense}, ATK=${enemy.attackPower}, MR=${enemy.magicResistance}, LUCK=${enemy.luck}, EVA=${enemy.evasion}"
    }

    private fun checkGameOver() {
        if (enemy.hp <= 0) {
            navigateToGameOver(true)
        } else if (hero.hp <= 0) {
            navigateToGameOver(false)
        } else {
            updateStats()
        }
    }

    private fun enemyTurn() {
        if (enemy.hp > 0) {
            val randomAction = (1..3).random()
            when (randomAction) {
                1 -> {
                    val damage = enemy.attack(hero)
                    appendToGameLog("Enemy attacked Hero for $damage damage.")
                }
                2 -> {
                    enemy.defend()
                    appendToGameLog("Enemy is defending.")
                }
                3 -> {
                    val healAmount = enemy.heal()
                    appendToGameLog("Enemy healed for $healAmount HP.")
                }
            }
            checkGameOver()
        }
    }

    private fun navigateToGameOver(isWin: Boolean) {
        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("isWin", isWin)
        startActivity(intent)
        finish() // End the main activity
    }

    private fun appendToGameLog(message: String) {
        tvGameLog.append("\n$message")
    }
}
