package advent4

import java.nio.file.Files
import java.nio.file.Paths

val input: String = Files.readString(
    Paths.get("/home/chris/code/advent/src/main/resources/day4input.txt")
)!!

data class Passport(
    val birthYear: Int?, val issueYear: Int?, val expirationYear: Int?, val height: String?,
    val hairColour: String?, val eyeColour: String?, val passportId: String?, val countryId: String?
) {
    fun fieldsPresent(): Boolean =
        birthYear != null && issueYear != null && expirationYear != null &&
                height != null && hairColour != null && eyeColour != null && passportId != null

    fun isValid(): Boolean =
        fieldsPresent() &&
                birthYear!! >= 1920 && birthYear <= 2002 &&
                issueYear!! >= 2010 && issueYear <= 2020 &&
                expirationYear!! >= 2020 && issueYear <= 2030 &&
                validHeight() &&
                validHairColour() &&
                validEyeColour() &&
                validPassportNo()


    fun validHeight(): Boolean {
        return if (height != null) {
            if (height.endsWith("cm")) {
                val heightValue = height.removeSuffix("cm").toIntOrNull()
                heightValue != null && heightValue >= 150 && heightValue <= 193
            } else if (height.endsWith("in")) {
                val heightValue = height.removeSuffix("in").toIntOrNull()
                heightValue != null && heightValue >= 59 && heightValue <= 76
            } else false
        } else false
    }

    fun validHairColour(): Boolean {
        return hairColour?.matches(Regex("""#[0-9a-f]{6,6}""")) == true
    }

    val validEyeColours = listOf<String>("amb","blu","brn","gry","grn","hzl","oth")
    fun validEyeColour(): Boolean =
        validEyeColours.contains(eyeColour)

    fun validPassportNo(): Boolean =
        passportId?.matches(Regex("""[0-9]{9,9}""")) == true

}

fun parsePassport(passportString: String): Passport {
    val fields: List<String> = passportString.replace("\n", " ").trim().split(Regex("""\s"""))
    val fMap: Map<String, String> = fields.map {
        val keyValue = it.split(""":""")
        Pair(keyValue[0], keyValue[1])
    }.toMap()

    return Passport(
        birthYear = fMap["byr"]?.toInt(), issueYear = fMap["iyr"]?.toInt(), expirationYear = fMap["eyr"]?.toInt(),
        height = fMap["hgt"], hairColour = fMap["hcl"], eyeColour = fMap["ecl"], passportId = fMap["pid"],
        countryId = fMap["cid"]
    )
}


fun main() {
    val passports = input.split("\n\n").map { parsePassport(it) }

    passports.forEach{
        if (it.validHeight()) {
            println("${it.height}")
        }
    }

    println(passports.count { it.isValid() })
}

