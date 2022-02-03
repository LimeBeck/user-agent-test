package someTest
import kotlin.test.Test
import ua_parser.Parser
import kotlin.test.assertEquals

class TestParser {
    val parser = Parser()

    fun parse(ua: String): String {
        val c = parser.parse(ua)
        val minor = c.os.minor?.let{ ".$it" } ?: ""
        val foo = c.device.family.takeIf { it != "Other" } ?: c.userAgent.family

        val result = "${c.os?.family ?: "unknown"} ${c.os.major ?: ""}$minor, ${foo ?: "unknown"}"
        return result
    }

    @Test
    fun `Parse user agent`(){
        assertEquals("iOS 5.1, iPhone", parse("Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3"))
        assertEquals("iOS 15.1, iPhone", parse("MobileBanking/1.0.0 (iPhone; CPU iPhone OS 15_1_1 like Mac OS X)"))
        assertEquals("iOS 15.1, iPhone", parse("MobileBanking/1.0.0 iPhone OS 15.1.1"))
        assertEquals("iOS , iPhone", parse("MobileBanking/1.0.0 iPhone10,6 iOS/15.1 CFNetwork/1325.0.1 Darwin/21.1.0"))
    }
}