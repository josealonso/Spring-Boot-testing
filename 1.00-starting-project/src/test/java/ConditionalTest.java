import com.josealonso.junitdemo.DemoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalTest {

    @Test
    @Disabled("Don't run until JIRA #123 is resolved")
    @DisplayName("Basic Test")
    void basicTest() {
        assertEquals(1, 1, "1 equals 1");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    @DisplayName("Basic Test only For Windows")
    void testForWindowsOnly() {
        assertEquals(1, 1, "1 equals 1");
    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX, OS.SOLARIS})
    @DisplayName("Basic Test only For Unix Systems")
    void testForUnixSystemsOnly() {
        assertEquals(1, 1, "1 equals 1");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    @DisplayName("Basic Test only For Java 17")
    void testOnlyForJava17() {
        assertEquals(1, 1, "1 equals 1");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_18)  // min and max are both mandatory
    void testOnlyForJavaRange() {
        assertEquals(1, 1, "1 equals 1");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "WORKING_ENVIRONMENT", matches = "DEV")
    void testOnlyForDevEnvironment() {
        assertEquals(1, 1, "1 equals 1");
    }

    @Test
    @EnabledIfSystemProperty(named = "SYS_PROP", matches = "CI")
    void testOnlyForSystemProperty() {
        assertEquals(1, 1, "1 equals 1");
    }

}