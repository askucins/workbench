package misc

import groovy.util.logging.Slf4j
import spock.lang.See
import spock.lang.Specification

import java.math.RoundingMode
import java.text.NumberFormat

@See('https://dev.to/luke_tong_d4f228249f32d86/beyond-double-essential-bigdecimal-practices-for-accurate-financial-calculations-52m1')
@Slf4j
class BigDecimalSpec extends Specification {

    def "should initialize with strings"() {
        given:
        def a = new BigDecimal("0.1")
        def b = new BigDecimal("0.2")
        when:
        def sum = a + b
        then:
        sum == new BigDecimal("0.3")
        cleanup:
        log.info("a: {}, b: {}, sum: {}", a, b, sum)
    }

    def "should import double value with BogDecimal.valueOf"() {
        given:
        Double priceDouble = 0.1
        when:
        BigDecimal price = BigDecimal.valueOf(priceDouble)
        then:
        price == new BigDecimal("0.1")
        cleanup:
        log.info("priceDouble: {}, price: {}", priceDouble, price)
    }

    def "should format value with setScale"() {
        given:
        BigDecimal preciseValue = new BigDecimal("123.456789")
        when:
        BigDecimal roundedValue = preciseValue.setScale(2, RoundingMode.HALF_UP) // Explicit rounding
        then:
        roundedValue == new BigDecimal("123.46")
    }

    def "should format currency value"() {
        given:
        BigDecimal preciseValue = new BigDecimal("123.456789")
        and:
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
        when:
        BigDecimal roundedValue = preciseValue.setScale(2, RoundingMode.HALF_UP) // Explicit rounding
        then:
        currencyFormatter.format(roundedValue) == '$123.46'
    }

    def "should compare carefully with a scale included and Groovy in mind"() {
        given:
        BigDecimal a = new BigDecimal("10.1") // scale == 1
        BigDecimal b = new BigDecimal("10.10") // scale == 2
        expect:
        !a.equals(b)
        and:
        a.hashCode() != b.hashCode() // likely to be different due to scale
        and:
        a == b
        and:
        a.compareTo(b) == 0 // compares values, ignoring scale
    }

    def "should be trapped into silent overflow (int)"() {
        given:
        int maxIntValue = Integer.MAX_VALUE // 2147483647
        and:
        int result = maxIntValue + 1
        expect:
        result == Integer.MIN_VALUE // Wraps around to the minimum value
    }

    def "should be trapped into silent overflow (long)"() {
        given:
        long largeNumber = Long.MAX_VALUE
        and:
        long overflowed = largeNumber + 100 // Wraps around to a negative number
        expect:
        overflowed < 0
    }

    def "should avoid silent overflow (int)"() {
        when:
        int sum = Math.addExact(Integer.MAX_VALUE, 1)
        then:
        thrown(ArithmeticException)
    }

    def "should avoid silent overflow (long)"() {
        when:
        long product = Math.multiplyExact(Long.MAX_VALUE, 2L)
        then:
        thrown(ArithmeticException)
    }
}
