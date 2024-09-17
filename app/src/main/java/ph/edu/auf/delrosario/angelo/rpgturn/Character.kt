package ph.edu.auf.delrosario.angelo.rpgturn
// Character.kt
abstract class Character(
    var name: String,
    var hp: Int,
    var defense: Int,
    var attackPower: Int
) {
    abstract fun attack(opponent: Character): Int
    abstract fun defend(): Int
    abstract fun heal(): Int
}
