package ph.edu.auf.delrosario.angelo.rpgturn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

class CharacterSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_selection)

        setupClickListeners()

        val editTextHeroName: EditText = findViewById(R.id.editTextHeroName)
        val btnStartGame: Button = findViewById(R.id.btnStartGame)

        // Find each radio button individually
        val radioVampire: RadioButton = findViewById(R.id.radioVampire)
        val radioVanguard: RadioButton = findViewById(R.id.radioVanguard)
        val radioWizard: RadioButton = findViewById(R.id.radioWizard)
        val radioAssassin: RadioButton = findViewById(R.id.radioAssassin)

        btnStartGame.setOnClickListener {
            val heroName = editTextHeroName.text.toString()

            // Check which radio button is selected
            val selectedClass = when {
                radioVampire.isChecked -> radioVampire.text.toString()
                radioVanguard.isChecked -> radioVanguard.text.toString()
                radioWizard.isChecked -> radioWizard.text.toString()
                radioAssassin.isChecked -> radioAssassin.text.toString()
                else -> null
            }

            if (selectedClass != null && heroName.isNotBlank()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("heroName", heroName)
                intent.putExtra("heroClass", selectedClass)
                startActivity(intent)
                finish()
            } else {
                // Handle error: no class selected or name empty
                Toast.makeText(this, "Please select a class and enter a name.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        val statMap = mapOf(
            R.id.selectionImageVanguard to Triple(
                "Crimson Vanguard",
                "Meet the Crimson Vanguard, a stalwart defender on the battlefield. With an impressive health pool and near-impenetrable defense, this hero excels in withstanding enemy blows. Though their attack is modest and agility is limited, their high vitality ensures they can endure prolonged engagements. Luck is just enough to turn the tide when needed.",
                "Base Stats:\nHP: 180\nATK: 20\nDEF: 90\nVIT: 80\nAGI: 20\nLUCK: 50"
            ),
            R.id.selectionImageAssassin to Triple(
                "Shadowblade Assassin",
                "The Shadowblade Assassin is a master of stealth and agility. With lightning-fast reflexes and a devastatingly high attack, this hero can swiftly eliminate foes before they even have a chance to react. Their low health and defense make them vulnerable, but their agility and luck often help them evade danger and strike from the shadows.",
                "Base Stats:\nHP: 120\nATK: 90\nDEF: 10\nVIT: 20\nAGI: 90\nLUCK: 50"
            ),
            R.id.selectionImageWizard to Triple(
                "Water Emperor",
                "The Water Emperor commands the battlefield with a powerful blend of attack and agility. This hero balances a decent health pool with high agility, allowing them to strike quickly and decisively. While their defense and vitality are less impressive, their exceptional luck often grants them favorable outcomes in critical moments.",
                "Base Stats:\nHP: 150\nATK: 80\nDEF: 30\nVIT: 20\nAGI: 80\nLUCK: 70"
            ),
            R.id.selectionImageVampire to Triple(
                "Vampire Lord",
                "As the Vampire Lord, this hero combines raw power with unmatched agility. Their attack is formidable and their speed is astonishing, making them a fearsome adversary in battle. Despite their lower health and defense, they rely on their swift movements and high luck to outmaneuver enemies and dominate the fray.",
                "Base Stats:\nHP: 100\nATK: 90\nDEF: 20\nVIT: 20\nAGI: 90\nLUCK: 50"
            )
        )

        for ((imageViewId, info) in statMap) {
            val imageView = findViewById<ImageView>(imageViewId)
            val (name, description, stats) = info
            setStatDialog(imageView, name, description, stats)
        }
    }

    private fun setStatDialog(imageView: ImageView, name: String, description: String, stats: String) {
        imageView.setOnClickListener {
            showStatsDialog(name, description, stats)
        }
    }

    private fun showStatsDialog(name: String, description: String, stats: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(name)
        builder.setMessage("$description\n\n$stats")
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}
