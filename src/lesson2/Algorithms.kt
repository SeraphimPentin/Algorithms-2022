@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File
import kotlin.math.ceil
import kotlin.math.sqrt

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    TODO()
}
//    val file = File(inputName).readLines().map { it.toInt() }
//    val delta = mutableListOf<Int>()
//    for (i in 0..file.size - 2) delta.add(i, file[i + 1] - file[i])
//    File(inputName).forEachLine { if (!it.matches(Regex("""[0-9]+"""))) throw IllegalArgumentException() }
//    var answer: Pair<Int, Int> = Pair(0, 0)
//    val subL = delta.take(delta.size / 2)
//    val subR = delta.takeLast(delta.size / 2)
//
//    var maxL = subL[0]
//    val maxR = subR[0]
//    for (i in subL.indices) {
//        val current = subL[i] + subL[i + 1]
//        if (subL[i] > maxL) maxL = subL[i]
//
//    }
//
////    var difference = -1
////    for (buy in 1 until file.size) {
////        val sublist = file.take(buy + 1)
////        for (sell in 1 until sublist.size) {
////            if (file[buy] - sublist[sell] > difference) {
////                difference = file[buy] - sublist[sell]
////                answer = Pair(sell + 1, buy + 1)
////            } else continue
////        }
////    }
//    return answer
//}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    TODO()
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
fun longestCommonSubstring(first: String, second: String): String {

    /*
    x, y - длинна слов
    T(n)=O(x*y),
    R(n)=O(x*y)
     */

    val firstLen = first.length
    val secondLen = second.length
    var maxLen = 0
    var term = 0
    val matrix: Array<Array<Int>> = Array(firstLen) { Array(secondLen) { 0 } }
    for (i in 1 until firstLen) {
        for (j in 1 until secondLen) {
            if (first[i - 1] == second[j - 1]) {
                matrix[i][j] += matrix[i - 1][j - 1] + 1
                if (maxLen < matrix[i][j]) {
                    term = i
                    maxLen = matrix[i][j]
                }
            }
        }
    }
    return first.substring(term - maxLen, term)
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
fun calcPrimesNumber(limit: Int): Int {
    /*
       трудоемкость: O(n)
       ресурсоемкость: O(n)
     */
    if (limit <= 1) return 0
    var count = 0
    for (i in 1..limit step 2) {
        if (isPrime(i)) count++
        else continue
    }
    return count
}

fun isPrime(x: Int): Boolean {
    val maxTest = ceil(sqrt(x.toDouble()))
    for (i in 2..maxTest.toInt()) {
        if (x % i == 0) return false
    }
    return true
}