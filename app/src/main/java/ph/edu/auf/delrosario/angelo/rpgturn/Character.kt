package ph.edu.auf.delrosario.angelo.rpgturn

abstract class Character(
    var name: String,
    var maxHP: Int,
    var hp: Int,
    var defense: Int,
    var vitality: Int,
    var agility: Int,
    var attack: Int,
    var luck: Int,
) {
    abstract fun defend(): Int
    abstract fun heal(): Int
}

