package ph.edu.auf.delrosario.angelo.rpgturn

abstract class Character(
    var name: String,
    var hp: Int,
    var defense: Int,
    var attackPower: Int,
    var luck: Int,
    var evasion: Int
) {
    abstract fun attack(opponent: Character): Int
    abstract fun defend(): Int
    abstract fun heal(): Int
}

