
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CortexM0SysTickTest.
 *
 * @author  (Kamil Jamros)
 * @version (a version number or a date)
 */
public class CortexM0SysTickTest
{
    
    /**
     * Default constructor for test class CortexM0SysTickTest
     */
    public CortexM0SysTickTest()
    {
        
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void disable_test()
    {
        CortexM0SysTick cortexM01 = new CortexM0SysTick();
        cortexM01.setEnableFlag(true);
        cortexM01.impuls();
        assertEquals(1, cortexM01.getCVR());
        cortexM01.setRVR(0);
        cortexM01.impuls();
        assertEquals(0, cortexM01.getCVR());
        assertEquals(0, cortexM01.getRVR());
        cortexM01.impuls();
        assertEquals(0, cortexM01.getCVR());
        assertEquals(0, cortexM01.getRVR());
        cortexM01.setRVR(2);
        cortexM01.impuls();
        assertEquals(2, cortexM01.getCVR());
        cortexM01.impuls();
        assertEquals(1, cortexM01.getCVR());
        cortexM01.impuls();
        assertEquals(0, cortexM01.getCVR());
        
    }
    
       @Test
    public void testReload()
    {
        CortexM0SysTick cortexM01 = new CortexM0SysTick();
        cortexM01.setRVR(4);
        cortexM01.setRVR(4);
        cortexM01.setEnableFlag(true);
        cortexM01.impuls();
        assertEquals(4, cortexM01.getRVR());
        for (int i=0; i<5; i++) cortexM01.impuls();
        assertEquals(4, cortexM01.getRVR());
        assertEquals(true, cortexM01.isCountFlag());
        assertEquals(false, cortexM01.isCountFlag());
    }
    
    @Test
    public void testFlags()
    {
        CortexM0SysTick cortexM01 = new CortexM0SysTick();
        assertEquals(false, cortexM01.isTickintFlag());
        assertEquals(false, cortexM01.isCountFlag());
        assertEquals(false, cortexM01.isEnableFlag());
        cortexM01.setEnableFlag(true);
        cortexM01.setTickintFlag(true);
        assertEquals(true, cortexM01.isTickintFlag());
        assertEquals(false, cortexM01.isCountFlag());
        assertEquals(true, cortexM01.isEnableFlag());
        cortexM01.impuls();
        cortexM01.impuls();
        assertEquals(0, cortexM01.getCVR()); //cvr do zera
        assertEquals(true, cortexM01.isCountFlag());
        assertEquals(false, cortexM01.isCountFlag());
    }
    
    
    @Test
    public void testRVR()
    {
        CortexM0SysTick cortexM01 = new CortexM0SysTick();
        cortexM01.setRVR(0);
        assertEquals(0, cortexM01.getRVR()); // blokuje licznik w nastepnym przejscu
        cortexM01.impuls();
        assertEquals(2, cortexM01.getCVR());
        cortexM01.setEnableFlag(true);
        cortexM01.impuls();
        assertEquals(0, cortexM01.getCVR());
        cortexM01.setRVR(10);
        assertEquals(10, cortexM01.getRVR());
        cortexM01.setRVR(-1);
        assertEquals(10, cortexM01.getRVR());
        cortexM01.setRVR((1<<24)+1);
        assertEquals(10, cortexM01.getRVR());
    }
    
    
    @Test
    public void testCVR()
    {
        CortexM0SysTick cortexM01 = new CortexM0SysTick();
        cortexM01.setCVR(0);
        assertEquals(0, cortexM01.getCVR());
        cortexM01.setEnableFlag(true);
        cortexM01.impuls();
        assertEquals(5, cortexM01.getCVR());
        assertEquals(false, cortexM01.isCountFlag());
        cortexM01.setCVR(0);
        assertEquals(0, cortexM01.getCVR());
    }
    
 
    @Test
    public void testInt()
    {
        CortexM0SysTick cortexM01 = new CortexM0SysTick();
        cortexM01.setEnableFlag(true);
        cortexM01.setTickintFlag(true);
        assertEquals(false, cortexM01.hasChanged());
        cortexM01.impuls();
        cortexM01.impuls();
        assertEquals(true, cortexM01.isCountFlag());
        assertEquals(0, cortexM01.getCVR());
        assertEquals(true, cortexM01.hasChanged());
        cortexM01.impuls();
        assertEquals(5, cortexM01.getCVR());
        assertEquals(false, cortexM01.hasChanged());

    }
    
}


