package misc

import groovy.util.logging.Slf4j

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@Slf4j
class HashCode {

    // See: https://stackoverflow.com/a/67939671/1108588
    private static String toHexString(byte[] bytes) {
        Formatter result = new Formatter()
        for (def b : bytes) {
            result.format("%02x", b & 0xff)
        }
        return result.toString()
    }

    // See: https://reflectoring.io/feature-flags-make-or-buy
    static double percentageHashCode(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256")
            byte[] encodedHash = digest.digest(
                text.getBytes(StandardCharsets.UTF_8))
            double INTEGER_RANGE = 1L << 32
            /*
                Check for specific case (only for illustration) based on https://en.wikipedia.org/wiki/SHA-2
                SHA256("")
                0x e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855
             */
            if (text == '') {
                log.debug "Checking reference case"
                assert toHexString(encodedHash) == 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'
            }
            log.debug "Hexed EncodedHash (length: {}) of text '{}': {}", encodedHash.size(), text, toHexString(encodedHash)
            log.debug "IntegerRange: ({}): {}", 1L << 32, INTEGER_RANGE
            log.debug "IntegerMin: {}", Integer.MIN_VALUE
            log.debug "HashCode: {}", (long) Arrays.hashCode(encodedHash)
            log.debug "HashCode - Integer.MIN_VALUE: {}", ((long) Arrays.hashCode(encodedHash) - Integer.MIN_VALUE)
            log.info "Ratio: {}", ((long) Arrays.hashCode(encodedHash) - Integer.MIN_VALUE) / INTEGER_RANGE
            return (((long) Arrays.hashCode(encodedHash) - Integer.MIN_VALUE) / INTEGER_RANGE) * 100
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e)
        }
    }
}
