import org.example.milestone3mlgui.MLGuiController;
import org.example.milestone3mlgui.Machine;
import org.example.milestone3mlgui.Memory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MachineUnitTests {
    private Machine machine;
    private Memory memory;

    @BeforeEach
    void setUp() {
        machine = new Machine();
        memory = machine.memory;
    }

    @Test
    void testReadPos() {
        machine.index = 1;
        machine.read(+1234);
        assertEquals(1234, memory.getWordSingle(machine.index-1));
    }

    @Test
    void testReadNeg() {
        machine.index = 1;
        machine.read(-5678);
        assertEquals(-5678, memory.getWordSingle(machine.index - 1));
    }

    @Test
    void testStorePos() {
        machine.accumulator = 3456;
        machine.store(10);
        assertEquals(3456, memory.getWordSingle(10));
    }

    @Test
    void testStoreNeg() {
        machine.accumulator = -7890;
        machine.store(20);
        assertEquals(-7890, memory.getWordSingle(20));
    }

    @Test
    void testWritePos() {
        memory.setWordSingle(5, 4321);
        assertEquals("location: 5 in memory: 4321", machine.write(5));
    }

    @Test
    void testWriteNeg() {
        memory.setWordSingle(7, -8765);
        assertEquals("location: 7 in memory: -8765", machine.write(7));
    }

    @Test
    void testAddPos() {
        memory.setWordSingle(3, 1000);
        machine.accumulator = 2000;
        assertEquals("2000 and 1000 added. Added result: 3000", machine.add(3));
    }

    @Test
    void testAddNeg() {
        memory.setWordSingle(4, -500);
        machine.accumulator = 1000;
        assertEquals("1000 and -500 added. Added result: 500", machine.add(4));
    }

    @Test
    void testSubtractPos() {
        memory.setWordSingle(6, 800);
        machine.accumulator = 1500;
        assertEquals("1500 and 800subtracted. Subtracted result:700", machine.subtract(6));
    }

    @Test
    void testSubtractNeg() {
        memory.setWordSingle(8, -200);
        machine.accumulator = 500;
        assertEquals("500 and -200subtracted. Subtracted result:700", machine.subtract(8));
    }

    @Test
    void testDividePos() {
        memory.setWordSingle(9, 5);
        machine.accumulator = 100;
        assertEquals("100 and 5 divided. Divided result:20", machine.divide(9));
    }

    @Test
    void testDivideNeg() {
        memory.setWordSingle(11, -4);
        machine.accumulator = 40;
        assertEquals("40 and -4 divided. Divided result:-10", machine.divide(11));
    }

    @Test
    void testMultiplyPos() {
        memory.setWordSingle(12, 3);
        machine.accumulator = 10;
        assertEquals("10 and 3 multiplied. Multiplied result: 30", machine.multiply(12));
    }

    @Test
    void testMultiplyNeg() {
        memory.setWordSingle(14, -2);
        machine.accumulator = 15;
        assertEquals("15 and -2 multiplied. Multiplied result: -30", machine.multiply(14));
    }

    @Test
    void testBranchBasic() {
        assertEquals(4, machine.branch(5));
    }

    @Test
    void testBranchExtensive() {
        for (int i = 0; i < 100; i++) {
            assertEquals(i - 1, machine.branch(i));
        }
    }

    @Test
    void testBranchNegBasic() {
        machine.accumulator = -1;
        assertEquals(4, machine.branchneg(5));
    }

    @Test
    void testBranchNegExtensive() {
        machine.accumulator = -10;
        for (int i = 1; i <= 50; i++) {
            assertEquals(i - 1, machine.branchneg(i));
        }
    }

    @Test
    void testBranchZeroBasic() {
        machine.accumulator = 0;
        assertEquals(4, machine.branchzero(5));
    }

    @Test
    void testBranchZeroExtensive() {
        machine.accumulator = 0;
        for (int i = 1; i <= 50; i++) {
            assertEquals(i - 1, machine.branchzero(i));
        }
    }

    @Test
    void testLoadBasic() {
        memory.setWordSingle(2, 1234);
        assertEquals("1234 loaded to accumulator.", machine.load(2));
    }

    @Test
    void testLoadExtensive() {
        for (int i = 0; i < 100; i++) {
            memory.setWordSingle(i, i * 10);
            machine.load(i);
            assertEquals(i * 10, machine.accumulator);
        }
    }
}
