package ph.edu.auf.delrosario.angelo.rpgturn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class DiceRollActivity : AppCompatActivity() {

    private lateinit var imageViewDice: ImageView
    private lateinit var btnRollDice: Button
    private lateinit var tvDiceResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_roll)

        imageViewDice = findViewById(R.id.imageViewDice)
        btnRollDice = findViewById(R.id.btnRollDice)
        tvDiceResult = findViewById(R.id.tvDiceResult)

        btnRollDice.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        val rollResult = Random.nextInt(1, 7) // Dice roll result between 1 and 6
        tvDiceResult.text = "Rolled: $rollResult"

        if (rollResult % 2 == 0) {
            // Action succeeds
            val successIntent = Intent(this, MainActivity::class.java)
            successIntent.putExtra("actionSuccess", true)
            startActivity(successIntent)
        } else {
            // Action fails
            val failIntent = Intent(this, MainActivity::class.java)
            failIntent.putExtra("actionSuccess", false)
            startActivity(failIntent)
        }
        finish() // Close the dice roll activity
    }
}
