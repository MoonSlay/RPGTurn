package ph.edu.auf.delrosario.angelo.rpgturn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var stageLevel: Int = 1
    private lateinit var hero: Hero
    private lateinit var enemy: Enemy
    private lateinit var tvGameLog: TextView
    private lateinit var heroHpBar: ProgressBar
    private lateinit var enemyHpBar: ProgressBar
    private lateinit var llAttackOptions: LinearLayout
    private lateinit var imageVampire: ImageView
    private lateinit var imageVanguard: ImageView
    private lateinit var imageAssassin: ImageView
    private lateinit var imageWizard: ImageView
    private lateinit var imageViewBlackDragon: ImageView
    private lateinit var imageViewOrc: ImageView
    private lateinit var imageViewGoblin: ImageView
    private lateinit var imageViewLootBag: ImageView
    private lateinit var imageViewLootGoblin: ImageView
    private lateinit var imageViewLichKing: ImageView
    private lateinit var imageDice: ImageView
    private lateinit var heroNameLevelText: TextView
    private lateinit var enemyNameLevelText: TextView
    private lateinit var stageLevelTextView: TextView
    private val gameLog = mutableListOf<String>() // List to store the action log


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hook UI elements
        tvGameLog = findViewById(R.id.tvGameLog)
        heroHpBar = findViewById(R.id.heroHpBar)
        enemyHpBar = findViewById(R.id.enemyHpBar)
        imageViewBlackDragon = findViewById(R.id.imageViewBlackDragon)
        imageViewOrc = findViewById(R.id.imageViewOrc)
        imageViewGoblin = findViewById(R.id.imageViewGoblin)
        imageViewLootGoblin = findViewById(R.id.imageViewLootGoblin)
        imageViewLootBag = findViewById(R.id.imageViewLootBag)
        imageViewLichKing = findViewById(R.id.imageViewLichKing)
        imageDice = findViewById(R.id.imageDice)
        heroNameLevelText = findViewById(R.id.heroNameLevelText)
        enemyNameLevelText = findViewById(R.id.enemyNameLevelText)
        stageLevelTextView = findViewById(R.id.stage_level)
        imageVampire = findViewById(R.id.imageVampire)
        imageVanguard = findViewById(R.id.imageVanguard)
        imageAssassin = findViewById(R.id.imageAssassin)
        imageWizard = findViewById(R.id.imageWizard)


        val heroName = intent.getStringExtra("heroName") ?: "Hero"
        val heroClass = intent.getStringExtra("heroClass") ?: "Vampire"

        imageVampire.visibility = View.GONE
        imageVanguard.visibility = View.GONE
        imageAssassin.visibility = View.GONE
        imageWizard.visibility = View.GONE

        // Initialize hero object based on class
        hero = when (heroClass) {
            "Vampire" -> {
                imageVampire.visibility = View.VISIBLE
                Vampire(heroName,
                    maxHP = Random.nextInt(120, 150),
                    attack = Random.nextInt(40, 60),
                    vitality = Random.nextInt(2, 5),
                    defense = Random.nextInt(8, 16),
                    agility = Random.nextInt(8, 16),
                    luck = Random.nextInt(1, 5))
            }
            "Vanguard" -> {
                imageVanguard.visibility = View.VISIBLE
                Vanguard(heroName,
                    maxHP = Random.nextInt(180, 220),
                    attack = Random.nextInt(150, 270),
                    vitality = Random.nextInt(1, 5),
                    defense = Random.nextInt(8, 16),
                    agility = Random.nextInt(8, 16),
                    luck = Random.nextInt(1, 5))
            }
            "Wizard" -> {
                imageWizard.visibility = View.VISIBLE
                Wizard(heroName,
                    maxHP = Random.nextInt(180, 220),
                    attack = Random.nextInt(150, 270),
                    vitality = Random.nextInt(1, 5),
                    defense = Random.nextInt(8, 16),
                    agility = Random.nextInt(8, 16),
                    luck = Random.nextInt(1, 5))
            }
            "Assassin" -> {
                imageAssassin.visibility = View.VISIBLE
                Assassin(heroName,
                    maxHP = Random.nextInt(180, 220),
                    attack = Random.nextInt(150, 270),
                    vitality = Random.nextInt(1, 5),
                    defense = Random.nextInt(8, 16),
                    agility = Random.nextInt(8, 16),
                    luck = Random.nextInt(1, 5))
            }
            else -> {
                imageVampire.visibility = View.VISIBLE
                Vampire(heroName,
                    maxHP = Random.nextInt(180, 220),
                    attack = Random.nextInt(150, 270),
                    vitality = Random.nextInt(1, 5),
                    defense = Random.nextInt(8, 16),
                    agility = Random.nextInt(8, 16),
                    luck = Random.nextInt(1, 5))
            }
        }



        updateStageLevel()

        spawnNewEnemy()
        enemyNameLevelText.text = getString(R.string.name_level, enemy.name, enemy.level)

        heroHpBar.max = hero.maxHP
        heroHpBar.progress = hero.hp
        enemyHpBar.max = enemy.maxHP
        enemyHpBar.progress = enemy.hp
        updateHpBars()

        heroNameLevelText.text = getString(R.string.name_level, hero.name, hero.level)


        imageDice.setOnClickListener {
            performRandomHeroAction()
        }

        val btnAttack = findViewById<Button>(R.id.btnAttack)
        val btnDefend = findViewById<Button>(R.id.btnDefend)
        val btnHeal = findViewById<Button>(R.id.btnHeal)
        val btnShowLog = findViewById<Button>(R.id.btnShowLog)

        llAttackOptions = findViewById(R.id.llAttackOptions)

        val btnNormal = findViewById<Button>(R.id.btnNormal)
        val btnSpecial = findViewById<Button>(R.id.btnSpecial)
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

        btnNormal.setOnClickListener {
            val damage = hero.NormalSkill(enemy)
            logAction(R.string.attack_log, "biteAttack", damage)
            updateHpBars()
            checkGameOver()
            enemyTurn()
        }

        btnSpecial.setOnClickListener {
            val damage = hero.SpecialSkill(enemy)
            logAction(R.string.attack_log, "blood siphon", damage)
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
        imageVanguard.setOnClickListener {
            showStatsDialog("Crimson Vanguard", "ATK: ${hero.attack}\nDEF: ${hero.defense}\nVIT: ${hero.vitality}\nAGI: ${hero.agility}\nLUCK: ${hero.luck}")
        }
        imageVampire.setOnClickListener {
            showStatsDialog("Vampire Lord", "ATK: ${hero.attack}\nDEF: ${hero.defense}\nVIT: ${hero.vitality}\nAGI: ${hero.agility}\nLUCK: ${hero.luck}")
        }
        imageAssassin.setOnClickListener {
            showStatsDialog("Shadowblade Assassin", "ATK: ${hero.attack}\nDEF: ${hero.defense}\nVIT: ${hero.vitality}\nAGI: ${hero.agility}\nLUCK: ${hero.luck}")
        }
        imageWizard.setOnClickListener {
            showStatsDialog("Water Emperor", "ATK: ${hero.attack}\nDEF: ${hero.defense}\nVIT: ${hero.vitality}\nAGI: ${hero.agility}\nLUCK: ${hero.luck}")
        }
        imageViewBlackDragon.setOnClickListener {
            showStatsDialog("Black Dragon Stats", "ATK: ${enemy.attack}\nDEF: ${enemy.defense}\nVIT: ${enemy.vitality}\nAGI: ${enemy.agility}\nLUCK: ${enemy.luck}")
        }
        imageViewOrc.setOnClickListener {
            showStatsDialog("Orc Stats", "ATK: ${enemy.attack}\nDEF: ${enemy.defense}\nVIT: ${enemy.vitality}\nAGI: ${enemy.agility}\nLUCK: ${enemy.luck}")
        }
        imageViewGoblin.setOnClickListener {
            showStatsDialog("Goblin Stats", "ATK: ${enemy.attack}\nDEF: ${enemy.defense}\nVIT: ${enemy.vitality}\nAGI: ${enemy.agility}\nLUCK: ${enemy.luck}")
        }
        imageViewLootGoblin.setOnClickListener {
            showStatsDialog("Loot Goblin", "ATK: ${enemy.attack}\nDEF: ${enemy.defense}\nVIT: ${enemy.vitality}\nAGI: ${enemy.agility}\nLUCK: ${enemy.luck}")
        }
        imageViewLootBag.setOnClickListener {
            showStatsDialog("Loot Bag", "ATK: ${enemy.attack}\nDEF: ${enemy.defense}\nVIT: ${enemy.vitality}\nAGI: ${enemy.agility}\nLUCK: ${enemy.luck}")
        }
        imageViewLichKing.setOnClickListener {
            showStatsDialog("Lich King", "ATK: ${enemy.attack}\nDEF: ${enemy.defense}\nVIT: ${enemy.vitality}\nAGI: ${enemy.agility}\nLUCK: ${enemy.luck}")
        }


    }

    private fun updateHeroLevelDisplay() {
        heroNameLevelText.text = getString(R.string.name_level, hero.name, hero.level)
    }

    private fun updateEnemyLevelDisplay() {
        enemyNameLevelText.text = getString(R.string.name_level, enemy.name, enemy.level)
    }

    private fun spawnRandomEnemy(): Enemy {
        val levelMultiplier = hero.level
        return when ((1..6).random()) {
            1 -> Enemy("Goblin",
                level = Random.nextInt(1, 5) + levelMultiplier,
                maxHP = Random.nextInt(80, 100) * levelMultiplier,
                defense = Random.nextInt(5, 10) * levelMultiplier,
                vitality = Random.nextInt(5, 10) * levelMultiplier,
                agility = Random.nextInt(5, 10) * levelMultiplier,
                attack = Random.nextInt(10, 15) * levelMultiplier,
                luck = Random.nextInt(5, 10) * levelMultiplier)

            2 -> Enemy("Orc",
                level = Random.nextInt(1, 5) + levelMultiplier,
                maxHP = Random.nextInt(80, 100) * levelMultiplier,
                defense = Random.nextInt(5, 10) * levelMultiplier,
                vitality = Random.nextInt(5, 10) * levelMultiplier,
                agility = Random.nextInt(5, 10) * levelMultiplier,
                attack = Random.nextInt(10, 15) * levelMultiplier,
                luck = Random.nextInt(5, 10) * levelMultiplier)

            3 -> Enemy("Black Dragon",
                level = Random.nextInt(1, 5) + levelMultiplier,
                maxHP = Random.nextInt(80, 100) * levelMultiplier,
                defense = Random.nextInt(5, 10) * levelMultiplier,
                vitality = Random.nextInt(5, 10) * levelMultiplier,
                agility = Random.nextInt(5, 10) * levelMultiplier,
                attack = Random.nextInt(10, 15) * levelMultiplier,
                luck = Random.nextInt(5, 10) * levelMultiplier)
            4 -> Enemy("Loot Goblin",
                level = Random.nextInt(1, 5) + levelMultiplier,
                maxHP = Random.nextInt(80, 100) * levelMultiplier,
                defense = Random.nextInt(5, 10) * levelMultiplier,
                vitality = Random.nextInt(5, 10) * levelMultiplier,
                agility = Random.nextInt(5, 10) * levelMultiplier,
                attack = Random.nextInt(10, 15) * levelMultiplier,
                luck = Random.nextInt(5, 10) * levelMultiplier)
            5 -> Enemy("Lich King",
                level = Random.nextInt(1, 5) + levelMultiplier,
                maxHP = Random.nextInt(80, 100) * levelMultiplier,
                defense = Random.nextInt(5, 10) * levelMultiplier,
                vitality = Random.nextInt(5, 10) * levelMultiplier,
                agility = Random.nextInt(5, 10) * levelMultiplier,
                attack = Random.nextInt(10, 15) * levelMultiplier,
                luck = Random.nextInt(5, 10) * levelMultiplier)
            6 -> Enemy("Loot Bag",
                level = Random.nextInt(1, 5) + levelMultiplier,
                maxHP = Random.nextInt(80, 100) * levelMultiplier,
                defense = Random.nextInt(5, 10) * levelMultiplier,
                vitality = Random.nextInt(5, 10) * levelMultiplier,
                agility = Random.nextInt(5, 10) * levelMultiplier,
                attack = Random.nextInt(10, 15) * levelMultiplier,
                luck = Random.nextInt(5, 10) * levelMultiplier)

            else -> Enemy("Goblin",
                level = Random.nextInt(1, 5) + levelMultiplier,
                maxHP = Random.nextInt(80, 100) * levelMultiplier,
                defense = Random.nextInt(5, 10) * levelMultiplier,
                vitality = Random.nextInt(5, 10) * levelMultiplier,
                agility = Random.nextInt(5, 10) * levelMultiplier,
                attack = Random.nextInt(10, 15) * levelMultiplier,
                luck = Random.nextInt(5, 10) * levelMultiplier)
        }
    }

    // Function to log an action and update the game log display
    private fun logAction(action: String) {
        gameLog.add(action)
        tvGameLog.text = action
    }

    private fun logAction(actionId: Int, vararg formatArgs: Any) {
        val action = getString(actionId, *formatArgs)
        gameLog.add(action)
        tvGameLog.text = action
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

    private val enemyImageViews: Map<String, ImageView> by lazy {
        mapOf(
            "Black Dragon" to imageViewBlackDragon,
            "Goblin" to imageViewGoblin,
            "Orc" to imageViewOrc,
            "Loot Bag" to imageViewLootBag,
            "Loot Goblin" to imageViewLootGoblin,
            "Lich King" to imageViewLichKing
        )
    }

    // Function to spawn a new enemy with scaled stats
    private fun spawnNewEnemy() {
        // Spawn a new random enemy
        enemy = spawnRandomEnemy()

        // Hide all enemy images
        enemyImageViews.values.forEach { it.visibility = View.GONE }

        // Show the corresponding enemy image based on the spawned enemy
        enemyImageViews[enemy.name]?.visibility = View.VISIBLE

        updateEnemyLevelDisplay()

        // Update the HP bars and game log
        updateHpBars()
        logAction("A wild ${enemy.name} appeared!")
    }

    private fun enemyTurn() {
        if (enemy.hp > 0) {
            val randomAction = (1..3).random()
            when (randomAction) {
                1 -> {
                    val attackType = (1..2).random()
                    when (attackType) {
                        1 -> {
                            val damage = enemy.biteAttack(hero)
                            logAction("Enemy used Swift Cut on Hero for $damage damage.")
                            updateHpBars()
                            checkGameOver()
                        }
                        2 -> {
                            val damage = enemy.bloodSiphon(hero)
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
                    val attackType = (1..2).random()
                    when (attackType) {
                        1 -> {
                            val damage = hero.NormalSkill(enemy)
                            logAction("Hero used Swift Cut on Enemy for $damage damage.")
                            updateHpBars()
                            checkGameOver()
                            enemyTurn()
                        }
                        2 -> {
                            val damage = hero.SpecialSkill(enemy)
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

    // Check if the game is over (either hero or enemy reaches 0 HP)
    private fun checkGameOver() {
        if (enemy.hp <= 0) {
            // Hero gains experience
            val experienceGained = 50 // Amount of experience gained from defeating an enemy
            hero.gainExperience(experienceGained)
            logAction("Hero gained $experienceGained XP and leveled up to level ${hero.level}.")
            updateHeroLevelDisplay()

            // Spawn a new enemy with scaled difficulty
            spawnNewEnemy()
            stageLevel++
            updateStageLevel()


            // Continue the game after leveling up
        } else if (hero.hp <= 0) {
            navigateToGameOver(false) // Hero loses, go to Game Over screen
        }
    }
    private fun updateStageLevel() {
        stageLevelTextView.text = getString(R.string.stage_level_text, stageLevel)
    }

    private fun navigateToGameOver(isWin: Boolean) {
        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("isWin", isWin)
        startActivity(intent)
        finish()
    }
}
