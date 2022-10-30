package encryptdecrypt

import java.io.File

fun main(args: Array<String>) {

    var mode = "enc"
    var data = ""
    var key = 0
    var inFileName: String? = null
    var outFileName: String? = null
    var alg = "shift"

    for (i in args.indices) {
        when (args[i]) {
            "-mode" -> mode = args[i + 1]
            "-key" -> key = args[i + 1].toInt()
            "-data" -> data = args[i + 1]
            "-out" -> outFileName = args[i + 1]
            "-in" -> inFileName = args[i + 1]
            "-alg" -> alg = args[i + 1]
        }
    }

    if (data == "" && inFileName != null) data = File(inFileName).readText()

    val outText = when (mode) {
        "enc" -> encrypt(data, key, alg)
        "dec" -> decrypt(data, key, alg)
        else -> ""
    }

    if (outFileName == null) print(outText) else File(outFileName).writeText(outText)
}

fun encrypt(message: String, shift: Int, alg: String): String {
    var result = ""
    var encLetter: Char

    val lowerCase = 'a'..'z'
    val upperCase = 'A'..'Z'

    for (letter in message) {
        encLetter = letter
        if (alg == "shift") {
            if (letter in lowerCase) {
                repeat(shift) {
                    encLetter = if (encLetter == lowerCase.last) {
                        lowerCase.first
                    } else {
                        lowerCase.elementAt(lowerCase.indexOf(encLetter) + 1)
                    }
                }
            }
            if (letter in upperCase) {
                repeat(shift) {
                    encLetter = if (encLetter == upperCase.last) {
                        upperCase.first
                    } else {
                        upperCase.elementAt(upperCase.indexOf(encLetter) + 1)
                    }
                }
            }
        } else if (alg == "unicode") encLetter = (letter.code + shift).toChar()

        result += encLetter
    }

    return result
}

fun decrypt(message: String, shift: Int, alg: String): String {
    var result = ""
    var encLetter: Char

    val lowerCase = 'a'..'z'
    val upperCase = 'A'..'Z'

    for (letter in message) {
        encLetter = letter
        if (alg == "shift") {
            if (letter in lowerCase) {
                repeat(shift) {
                    encLetter = if (encLetter == lowerCase.first) {
                        lowerCase.last
                    } else {
                        lowerCase.elementAt(lowerCase.indexOf(encLetter) - 1)
                    }
                }
            }
            if (letter in upperCase) {
                repeat(shift) {
                    encLetter = if (encLetter == upperCase.first) {
                        upperCase.last
                    } else {
                        upperCase.elementAt(upperCase.indexOf(encLetter) - 1)
                    }
                }
            }
        } else if (alg == "unicode") encLetter = (letter.code - shift).toChar()

        result += encLetter
    }

    return result
}