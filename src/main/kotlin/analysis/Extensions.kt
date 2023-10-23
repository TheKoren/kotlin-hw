package analysis

fun String.getFirstPart(): String {
    val regex = Regex("[^a-zA-Z0-9]+")
    val parts = this.split(regex)
    return if (parts.isNotEmpty()) parts[0] else this
}