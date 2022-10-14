

import kotlin.random.Random

class Lotto {
    val players = mutableListOf<Person>() // определите поле, в котром будут храниться добавленные игроки `Person`
    val thrownNumbers: HashSet<Int> = hashSetOf() // определите подходящую структуру данных

    fun addPerson(person: Person) {
        players.add(person) // добавить игрока в список игроков
    }

    fun start() {
        if (players.size < 2) {
            println("Перед началом игры необходимо добавить хотя бы двух игроков")
            return
        }
        players.forEach { println(it.card.numbers.values.toString()) }
        while (true) {
            val winners = mutableListOf<Person>()
            val num = Random.nextInt(
                1,
                100
            )
            if (thrownNumbers.contains(num)) {
                continue
            } else {
                println("выброшенное число $num")
                thrownNumbers.add(num)
                for (player in players) {
                    if (player.card.numbers.values.any { it.contains(num) }) {
                        val pair = player.card.numbers.filter { it.value.contains(num) }
                        val line = pair.keys.first()
                        player.card.numbers[line]?.remove(num)
                        if (player.card.numbers[line]!!.isEmpty()) {
                            winners.add(player)
                        }
                    }
                }
                if (winners.isNotEmpty()) {
                    winners.forEach { player -> println("Победитель: ${player.name}!!!") }
               //     players.forEach { println(it.card.numbers.values.toString()) }
                    return
                }
            }

        }
    }
}

class Card(val numbers: Map<Int, MutableSet<Int>>)

class Person(val name: String) {

    val card: Card = createCard()

    private fun createCard(): Card {
        val numbers: Set<Int> = generateNumbers()

        val iterator: Iterator<Int> = numbers.iterator()
        var currentLine = 1

        val cardNumbers: MutableMap<Int, MutableSet<Int>> = mutableMapOf(
            1 to mutableSetOf(),
            2 to mutableSetOf(),
            3 to mutableSetOf()
        )

        while (iterator.hasNext()) {
            val number = iterator.next()
            cardNumbers[currentLine]?.add(number)

            if (currentLine < 3) {
                currentLine++
            } else {
                currentLine = 1
            }
        }

        return Card(cardNumbers)
    }

    private fun generateNumbers(): Set<Int> {
        val numbers: MutableSet<Int> = mutableSetOf()

        while (numbers.size < NUMBERS_COUNT) {
            numbers.add(Random.nextInt(MIN_NUMBER, MAX_NUMBER))
        }

        return numbers
    }

    private companion object {

        private const val NUMBERS_COUNT = 15
        private const val MAX_NUMBER = 100
        private const val MIN_NUMBER = 1
    }
}

//fun main(){
//    val lotto = Lotto()
//    lotto.addPerson(Person("a"))
//    lotto.addPerson(Person("b"))
//    lotto.addPerson(Person("c"))
//    lotto.addPerson(Person("d"))
//    lotto.start()
//}

