package analysis

/**
 * Extension functions for various data types.
 */
// This extension function is used to extract the first part of a string by splitting it based on non-alphanumeric characters.
fun String.getFirstPart(): String {
    val regex = Regex("[^a-zA-Z0-9]+")
    val parts = this.split(regex)
    return if (parts.isNotEmpty()) parts[0] else this
}