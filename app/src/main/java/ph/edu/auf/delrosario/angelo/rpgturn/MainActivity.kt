package ph.edu.auf.delrosario.angelo.rpgturn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var hero: Hero
    private lateinit var enemy: Enemy
    private lateinit var tvHeroStats: TextView
    private lateinit var tvEnemyStats: TextView
    private lateinit var tvGameLog: TextView
    // Nested button layouts
    private lateinit var llAttackOptions: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the hero and enemy
        hero = Hero("Hero", 100, 20, 15, 5, 10)
        enemy = Enemy("Enemy", 80, 15, 12, 3, 7)

        // Hook UI elements
        tvHeroStats = findViewById(R.id.tvHeroStats)
        tvEnemyStats = findViewById(R.id.tvEnemyStats)
        tvGameLog = findViewById(R.id.tvGameLog)

        val btnAttack = findViewById<Button>(R.id.btnAttack)
        val btnDefend = findViewById<Button>(R.id.btnDefend)
        val btnHeal = findViewById<Button>(R.id.btnHeal)


        llAttackOptions = findViewById(R.id.llAttackOptions)

        val btnSwiftCut = findViewById<Button>(R.id.btnSwiftCut)
        val btnForwardSlash = findViewById<Button>(R.id.btnForwardSlash)


        // Update stats views
        updateStats()

        // Set button listeners
        btnAttack.setOnClickListener {
            llAttackOptions.visibility = View.VISIBLE
        }


        btnSwiftCut.setOnClickListener {
            val damage = hero.swiftCut(enemy)
            tvGameLog.text = "Hero performed Swift Cut on Enemy for $damage damage."
            checkGameOver()
            enemyTurn()
        }

        btnForwardSlash.setOnClickListener {
            val damage = hero.forwardSlash(enemy)
            tvGameLog.text = "Hero performed Forward Slash on Enemy for $damage damage."
            checkGameOver()
            enemyTurn()
        }


        // Additional buttons: Defend and Heal
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
        tvHeroStats.text = "Hero: HP=${hero.hp}, DEF=${hero.defense}, ATK=${hero.attackPower},  LUCK=${hero.luck}, EVA=${hero.evasion}"
        tvEnemyStats.text = "Enemy: HP=${enemy.hp}, DEF=${enemy.defense}, ATK=${enemy.attackPower},  LUCK=${enemy.luck}, EVA=${enemy.evasion}"
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

