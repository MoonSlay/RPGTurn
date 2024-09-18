package ph.edu.auf.delrosario.angelo.rpgturn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CharacterSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_selection)

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
}
