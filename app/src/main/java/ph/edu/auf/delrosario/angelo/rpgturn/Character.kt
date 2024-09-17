package ph.edu.auf.delrosario.angelo.rpgturn

abstract class Character(
    var name: String,
    var maxHP: Int,
    var hp: Int,
    var defense: Int,
    var attackPower: Int,
    var luck: Int,
    var evasion: Int
) {
    abstract fun swiftCut(opponent: Character): Int
    abstract fun forwardSlash(opponent: Character): Int
    abstract fun defend(): Int
    abstract fun heal(): Int
}

