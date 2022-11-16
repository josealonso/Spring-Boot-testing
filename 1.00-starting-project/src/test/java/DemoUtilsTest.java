import com.josealonso.junitdemo.DemoUtils;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
    }

    @Test
    // @DisplayName("Equals and Not Equals")
    void test_Equals_And_Not_Equals() {

        assertEquals(6, demoUtils.add(2, 4), "2+4 must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1+9 must not be 6");
    }

    @Test
    // @DisplayName("Null and Not Null")
    void test_Null_And_Not_Null() {

        String nullStr = null;
        String myStr = "a string";

        assertNull(demoUtils.checkNull(nullStr), "Object should be null");
        assertNotNull(demoUtils.checkNull(myStr), "Object should not be null");
    }

    /*
    @AfterEach
    void tearDownAfterEach() {
        System.out.println("Running @AfterEach");
        System.out.println();
    }

    @BeforeAll
    static void setupBeforeEachClass() {
        System.out.println("Running @BeforeAll");
    }

    @AfterAll
    static void tearDownAfterAll() {
        System.out.println("Running @AfterAll");
    }
*/
}