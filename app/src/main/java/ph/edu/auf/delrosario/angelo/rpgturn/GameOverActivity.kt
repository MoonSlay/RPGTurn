package ph.edu.auf.delrosario.angelo.rpgturn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        // Get whether the player won or lost from the intent
        val isWin = intent.getBooleanExtra("isWin", false)

        // Restart the game
        val btnRestartGame = findViewById<Button>(R.id.btnRestartGame)
        btnRestartGame.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // End the game over activity
        }
    }
}
