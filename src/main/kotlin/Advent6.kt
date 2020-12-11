package advent6

import java.nio.file.Files
import java.nio.file.Paths

val input: String = Files.readString(
    Paths.get("/home/chris/code/advent/src/main/resources/6.txt")
)!!

data class Group(val people: List<Person>){

    fun groupAnyQuestions(): Set<Char> {
        return people.map{it.questions}.reduce{ allQuestions, newQuestions ->
            allQuestions.union(newQuestions)
        }
    }
    fun groupAllQuestions(): Set<Char> {
        return people.map{it.questions}.reduce{ allQuestions, newQuestions ->
            allQuestions.intersect(newQuestions)
        }
    }
}
data class Person(val questions: Set<Char>)

fun parseGroup(groupString: String): Group {
    val people = groupString.trim().split(Regex("""\n""")).map { parsePerson(it) }
    return Group(people)
}

fun parsePerson(personString: String): Person {
    val questions = personString.toCharArray().toSet()
    return Person(questions)
}

fun main() {
    val groups = input.split("\n\n").map { parseGroup(it) }

    println(groups.sumBy { it.groupAnyQuestions().size })
    println(groups.sumBy { it.groupAllQuestions().size })
}
