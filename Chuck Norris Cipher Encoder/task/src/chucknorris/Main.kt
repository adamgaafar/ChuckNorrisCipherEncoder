package chucknorris

fun main() {
    var input = ""
    do {
        println("Please input operation (encode/decode/exit):")
        when (val operation = readln()) {
            "encode" -> {
                println("Input string:")
                val textToEncode = readln()
                println("Encoded string:")
                println(encode(textToEncode))
            }

            "decode" -> {
                println("Input encoded string:")
                val textToDecode = readln()
                decode(textToDecode)
            }

            "exit" -> {
                println("Bye!")
                input = "exit"
            }

            else -> {
                println("There is no '$operation' operation")
            }
        }
        println("")
    } while (input != "exit")
}

fun encode(s: String): String {
    //encode
    return convertBinaryToUnary(stringToBinary(s))
}

fun decode(s: String): Unit {
    //decode
    if (iSValidEncoded(s)) {
        println("Decoded string:")
        println(binaryToString(convertUnaryToBinary(s.trim()).trimStart()))
    } else {
        println("Encoded string is not valid")
    }

}

fun iSValidEncoded(str: String): Boolean {
    val startBlocks = str.split(' ')
    val blocks = str.trim().split(' ')
    try {
        if (str.replace(" ", "").toLong() != 0L
            || blocks.size % 2 != 0
            || convertUnaryToBinary(str).trimStart().length % 7 != 0
        ) {
            throw Exception("Invalid Encoded String")
        }
        for (i in startBlocks.indices step 2) {
            val curr = startBlocks[i]
            if (curr != "0" && curr != "00") {
                throw Exception("Invalid Encoded String")
            }
        }
    } catch (e: Exception) {
        return false
    }
    return true
}

fun stringToBinary(word: String): String {
    val wordList = word.toList()
    var c = ""
    for (j in wordList) {
        c += convertCharToBinary(j)
    }
    return c
}

fun convertCharToBinary(i: Char): String {
    return if (i.isDigit()) {
        Integer.toBinaryString(i.toInt()).padStart(7, '0')
    } else {
        val let = i.code
        Integer.toBinaryString(let).padStart(7, '0')
    }
}

fun convertBinaryToUnary(bin: String): String {
    var result = " "
    var prevChar = ' '
    var block = " "

    for (j in bin.indices) {
        when (prevChar) {
            bin[j] -> {
                block = "0"
            }

            else -> {
                when (bin[j]) {
                    '1' -> {
                        block = " 0 0"
                    }

                    '0' -> {
                        block = " 00 0"
                    }
                }
            }
        }
        result += block
        prevChar = bin[j]
    }

    return result.trim()
}

fun convertUnaryToBinary(unary: String): String {
    var res = ""
    val unaryList = unary.split(' ')
    for (i in 1..unaryList.size step 2) {
        var tempC = " "
        val tempChar = unaryList[i - 1]
        val tempBlock = unaryList[i].toList().size
        tempC = if (tempChar == "0") {
            "1"
        } else {
            "0"
        }
        res += tempC.repeat(tempBlock)
    }
    return res
}

fun binaryToString(number: String): String {
    val binWord = number.trim().chunked(7)
    var res = ""
    for (i in binWord) {
        val cur = convertBinaryToDecimal(i.toLong())
        res += cur.toChar()
    }
    return res
}

fun convertBinaryToDecimal(num: Long): Int {
    var number = num
    var decimalNumber = 0
    var i = 0
    var remainder: Long

    while (number.toInt() != 0) {
        remainder = number % 10
        number /= 10
        decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}