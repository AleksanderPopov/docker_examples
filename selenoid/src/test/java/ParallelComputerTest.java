import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;

/**
 * Created by apop on 5/22/2017.
 */
public class ParallelComputerTest {

  @Test
  public void selenideTest() {
    Class[] cls = {GoogleSearchTest.class, GoogleSearchTest.class, GoogleSearchTest.class};
    JUnitCore.runClasses(new ParallelComputer(true, true), cls);
  }

//  @Test()
  public void test() {
    Class[] cls={ParallelTest1.class,ParallelTest1.class };

    //Parallel among classes
//    JUnitCore.runClasses(ParallelComputer.classes(), cls);

    //Parallel among methods in a class
//    JUnitCore.runClasses(ParallelComputer.methods(), cls);

    //Parallel all methods in all classes
    JUnitCore.runClasses(new ParallelComputer(true, true), cls);
  }

  public static class ParallelTest1 {
    @Test public void a(){System.out.println("a1 started" + Thread.currentThread());}
    @Test
    public void b(){System.out.println("b1 started" + Thread.currentThread());}
  }

  public static class ParallelTest2 {
    @Test public void a(){System.out.println("a2 started " + Thread.currentThread());}
    @Test public void b(){System.out.println("b2 started" + Thread.currentThread());}
  }
}