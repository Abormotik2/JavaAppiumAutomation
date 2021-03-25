import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

  @Test
  public void testGetLocalNumber() {
    MainClass main = new MainClass();
    int actualValue = main.getLocalNumber();
    int expectedValue = 14;
    Assert.assertEquals("Method: getLocalNumber(), does not return " + expectedValue, expectedValue, actualValue);
  }

  @Test
  public void testGetClassNumber() {
    MainClass main = new MainClass();
    int actualValue = main.getClassNumber();
    int expectedValue = 45;
    Assert.assertTrue("Method: getClassNumber(), return number less than: " + expectedValue, expectedValue < actualValue);
  }

  @Test
  public void testGetClassString() {
    MainClass main = new MainClass();
    String actualSubStringValue = main.getClassString();
    String expectedSubStringFirstValue = "hello";
    String expectedSubStringSecondValue = "Hello";
    Assert.assertTrue("Method: getClassString(), does not return subString: " +
                    expectedSubStringFirstValue + " or subString: " + expectedSubStringSecondValue,
            actualSubStringValue.contains(expectedSubStringFirstValue) ||
                    actualSubStringValue.contains(expectedSubStringSecondValue));
  }
}
