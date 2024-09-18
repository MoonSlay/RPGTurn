package ph.edu.auf.delrosario.angelo.rpgturn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class CharacterSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_selection)

        val radioGroupClasses: RadioGroup = findViewById(R.id.radioGroupClasses)
        val editTextHeroName: EditText = findViewById(R.id.editTextHeroName)
        val btnStartGame: Button = findViewById(R.id.btnStartGame)

        btnStartGame.setOnClickListener {
            val selectedClassId = radioGroupClasses.checkedRadioButtonId
            val heroName = editTextHeroName.text.toString()

            if (selectedClassId != -1 && heroName.isNotBlank()) {
                val heroClass = findViewById<RadioButton>(selectedClassId).text.toString()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("heroName", heroName)
                intent.putExtra("heroClass", heroClass)
                startActivity(intent)
                finish()
            } else {
                // Handle error: no class selected or name empty
                Toast.makeText(this, "Please select a class and enter a name.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
