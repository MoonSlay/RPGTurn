package ph.edu.auf.delrosario.angelo.rpgturn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var hero: Hero
    private lateinit var enemy: Enemy
    private lateinit var tvGameLog: TextView
    private lateinit var heroHpBar: ProgressBar
    private lateinit var enemyHpBar: ProgressBar
    private lateinit var llAttackOptions: LinearLayout
    private lateinit var imageViewHero: ImageView
    private lateinit var imageViewEnemy: ImageView
    private lateinit var imageDice: ImageView
    private val gameLog = mutableListOf<String>() // List to store the action log

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize hero and enemy objects
        hero = Hero("Angelo",
            maxHP = Random.nextInt(180, 220),
            attackPower = Random.nextInt(10, 20),
            defense = Random.nextInt(8, 16),
            luck = Random.nextInt(1, 5),
            evasion = Random.nextInt(3, 8))

        enemy = Enemy(
            "Black Dragon",
            maxHP = Random.nextInt(80, 120),
            attackPower = Random.nextInt(10, 20),
            defense = Random.nextInt(8, 16),
            luck = Random.nextInt(1, 5),
            evasion = Random.nextInt(3, 8)
        )

        // Hook UI elements
        tvGameLog = findViewById(R.id.tvGameLog)
        heroHpBar = findViewById(R.id.heroHpBar)
        enemyHpBar = findViewById(R.id.enemyHpBar)
        imageViewHero = findViewById(R.id.imageViewHero)
        imageViewEnemy = findViewById(R.id.imageViewEnemy)
        imageDice = findViewById(R.id.imageDice)

        heroHpBar.max = hero.maxHP
        heroHpBar.progress = hero.hp
        enemyHpBar.max = enemy.maxHP
        enemyHpBar.progress = enemy.hp
        updateHpBars()

        imageDice.setOnClickListener {
            performRandomHeroAction()
        }

        val btnAttack = findViewById<Button>(R.id.btnAttack)
        val btnDefend = findViewById<Button>(R.id.btnDefend)
        val btnHeal = findViewById<Button>(R.id.btnHeal)
        val btnShowLog = findViewById<Button>(R.id.btnShowLog)

        llAttackOptions = findViewById(R.id.llAttackOptions)

        val btnSwiftCut = findViewById<Button>(R.id.btnSwiftCut)
        val btnForwardSlash = findViewById<Button>(R.id.btnForwardSlash)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Set button listeners
        btnAttack.setOnClickListener {
            llAttackOptions.visibility = View.VISIBLE
            imageDice.visibility = View.GONE
            btnAttack.visibility = View.GONE
            btnDefend.visibility = View.GONE
            btnHeal.visibility = View.GONE
        }

        btnBack.setOnClickListener {
            llAttackOptions.visibility = View.GONE
            imageDice.visibility = View.VISIBLE
            btnAttack.visibility = View.VISIBLE
            btnDefend.visibility = View.VISIBLE
            btnHeal.visibility = View.VISIBLE
        }

        btnSwiftCut.setOnClickListener {
            val damage = hero.swiftCut(enemy)
            logAction("Hero performed Swift Cut on Enemy for $damage damage.")
            updateHpBars()
            checkGameOver()
            enemyTurn()
        }

        btnForwardSlash.setOnClickListener {
            val damage = hero.forwardSlash(enemy)
            logAction("Hero performed Forward Slash on Enemy for $damage damage.")
            updateHpBars()
            checkGameOver()
            enemyTurn()
        }

        btnDefend.setOnClickListener {
            hero.defend()
            logAction("Hero defended against the attack.")
            checkGameOver()
            enemyTurn()
        }

        btnHeal.setOnClickListener {
            val healAmount = hero.heal()
            logAction("Hero healed $healAmount HP.")
            updateHpBars()
            checkGameOver()
            enemyTurn()
        }

        btnShowLog.setOnClickListener {
            showFullLogDialog()
        }

        // Set image click listeners to show stats in modal
        imageViewHero.setOnClickListener {
            showStatsDialog("Hero Stats", "HP: ${hero.hp}\nDEF: ${hero.defense}\nATK: ${hero.attackPower}\nLUCK: ${hero.luck}\nEVA: ${hero.evasion}")
        }

        imageViewEnemy.setOnClickListener {
            showStatsDialog("Enemy Stats", "HP: ${enemy.hp}\nDEF: ${enemy.defense}\nATK: ${enemy.attackPower}\nLUCK: ${enemy.luck}\nEVA: ${enemy.evasion}")
        }
    }

    // Function to log an action and update the game log display
    private fun logAction(action: String) {
        gameLog.add(action)  // Add the action to the log
        tvGameLog.text = action  // Display the latest action
    }

    private fun showFullLogDialog() {
        val logText = gameLog.joinToString("\n")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Log")
        builder.setMessage(logText)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    // Method to show stats in a dialog
    private fun showStatsDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    // Updates the HP stats and HP bars
    private fun updateHpBars() {
        heroHpBar.max = hero.maxHP
        heroHpBar.progress = hero.hp
        enemyHpBar.max = enemy.maxHP
        enemyHpBar.progress = enemy.hp
    }

    // Check if the game is over (either hero or enemy reaches 0 HP)
    private fun checkGameOver() {
        if (enemy.hp <= 0) {
            // Hero gains experience
            val experienceGained = 50 // Amount of experience gained from defeating an enemy
            hero.gainExperience(experienceGained)
            logAction("Hero gained $experienceGained XP and leveled up to level ${hero.level}.")

            // Spawn a new enemy with scaled difficulty
            spawnNewEnemy()

            // Continue the game after leveling up
        } else if (hero.hp <= 0) {
            navigateToGameOver(false) // Hero loses, go to Game Over screen
        }
    }

    // Function to spawn a new enemy with scaled stats
    private fun spawnNewEnemy() {
        val levelMultiplier = hero.level
        enemy = Enemy(
            "Enemy",
            maxHP = Random.nextInt(80, 120) * levelMultiplier,      // Scale HP by hero level
            attackPower = Random.nextInt(10, 20) * levelMultiplier, // Scale Attack Power
            defense = Random.nextInt(8, 16) * levelMultiplier,      // Scale Defense
            luck = Random.nextInt(1, 5) + levelMultiplier,          // Scale Luck slightly
            evasion = Random.nextInt(3, 8) + levelMultiplier        // Scale Evasion slightly
        )
        updateHpBars() // Update HP bars to reflect the new enemy stats
        logAction("A new enemy appeared!")
    }

    // Add XP and level-up messages to the game log
    private fun logLevelUpMessage() {
        logAction("Hero leveled up to level ${hero.level}!")
    }

    private fun enemyTurn() {
        if (enemy.hp > 0) {
            val randomAction = (1..3).random()
            when (randomAction) {
                1 -> {
                    val randomAction = (1..2).random()
                    when (randomAction) {
                        1 -> {
                            val damage = enemy.swiftCut(hero)
                            logAction("Enemy used Swift Cut on Hero for $damage damage.")
                            updateHpBars()
                            checkGameOver()
                        }
                        2 -> {
                            val damage = enemy.forwardSlash(hero)
                            if (damage == 0) {
                                logAction("Enemy is charging Forward Slash.")
                            } else {
                                logAction("Enemy used Forward Slash on Hero for $damage damage.")
                            }
                            updateHpBars()
                            checkGameOver()
                        }
                    }
                }
                2 -> {
                    enemy.defend()
                    logAction("Enemy defended against the attack.")
                }
                3 -> {
                    val healAmount = enemy.heal()
                    logAction("Enemy healed $healAmount HP.")
                    updateHpBars()
                    checkGameOver()
                }
            }
        }
    }

    private fun performRandomHeroAction() {
        if (hero.hp > 0) {
            val randomAction = (1..3).random()
            when (randomAction) {
                1 -> {
                    val randomAction = (1..2).random()
                    when (randomAction) {
                        1 -> {
                            val damage = hero.swiftCut(enemy)
                            logAction("Hero used Swift Cut on Enemy for $damage damage.")
                            updateHpBars()
                            checkGameOver()
                            enemyTurn()
                        }
                        2 -> {
                            val damage = hero.forwardSlash(enemy)
                            logAction("Hero used Forward Slash on Enemy for $damage damage.")
                            updateHpBars()
                            checkGameOver()
                            enemyTurn()
                        }
                    }
                }
                2 -> {
                    hero.defend()
                    enemyTurn()
                    logAction("Hero defended against the attack.")
                }
                3 -> {
                    val healAmount = hero.heal()
                    logAction("Hero healed $healAmount HP.")
                    updateHpBars()
                    checkGameOver()
                    enemyTurn()
                }
            }
        }
    }

    private fun navigateToGameOver(isWin: Boolean) {
        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("isWin", isWin)
        startActivity(intent)
        finish()
    }
}
