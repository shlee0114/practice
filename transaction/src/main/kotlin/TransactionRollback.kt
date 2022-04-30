import java.math.BigInteger
import kotlin.math.pow

fun main() {
    "111.11"
        .run {
            var value = (2.0.pow(indexOf('.') - 1))

            replace(".", "")
                .fold(0.0) { decimal, binary ->
                    decimal + binary.digitToInt() * value
                        .also {
                            value /= 2
                        }
                }
        }
        .apply(::println)
}

fun binaryToDecimal(type: Int, binary: String): Double {
    return when (type) {
        0 -> {
            binary.toCharArray().reversed().foldIndexed(0.0) { index: Int, acc: Double, c: Char ->
                acc + c.digitToInt() * 2.0.pow(index.toDouble())
            }
        }
        else -> {
            binary.toCharArray().foldIndexed(0.0) { index: Int, acc: Double, c: Char ->
                acc + c.digitToInt() * 0.5.pow(index.toDouble() + 1)
            }
        }
    }
}

fun fibo(acc: List<BigInteger>, acc1: BigInteger, acc2: BigInteger, x: BigInteger): List<BigInteger> =
    when (x) {
        BigInteger.ZERO -> acc
        BigInteger.ONE -> acc + (acc1 + acc2)
        else -> fibo(acc + (acc1 + acc2), acc2, acc1 + acc2, x - BigInteger.ONE)
    }
