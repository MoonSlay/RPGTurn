package ph.edu.auf.delrosario.angelo.rpgturn

data class CharacterInfo(
    val name: String,
    val description: String,
    val maxHP: Int,
    val defense: Int,
    val vitality: Int,
    val agility: Int,
    val attack: Int,
    val luck: Int
)