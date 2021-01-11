package the_bit;

public class CPU_test3 {

    public void runTests() {

        // I have modified memory.toString() to print the last 20 bytes for stack memory

        /* test 1
         * testing simple push pop call return
         *
         * end result: r1 r2 = 5
         *             r3 = -1
         *             r15 = 6 -> address of "move r3 -1"
         *             stack 5
         */
        {
            System.out.println("-------------- test 1 --------------");
            Computer computer = new Computer();
            String[] test = new String[]{
                    "move r1 5",    // r1 = 5
                    "move r2 10",   // r2 = 10
                    "call 14",      // jump to "push r1" // stack RN
                    "move r3 -1",   // r3 = -1
                    "interrupt 0",
                    "interrupt 1",
                    "halt",
                    "pop r15",      // stack empty // r15 = RN
                    "push r1",      // stack 1
                    "pop r2",       // empty stack // r2 = 5
                    "push r15",     // stack RN
                    "return"        // jump to "move r3 -1"
            };
            computer.preload(Assembler.assemble(test));
            computer.run();
        }

        /* test 2
         * testing push pop call return
         *
         * simulate example code from the assignment10 instruction plus extra steps
         *
         * int c = add(add(3,4),4);
         * c = 11
         *
         * end result: r1 = 7
         *             r2 = 4
         *             r15 = 16 -> address of "interrupt 0"
         *             stack 11
         */
        {
            System.out.println("\n-------------- test 2 --------------");
            Computer computer = new Computer();
            String[] test = new String[]{
                    // main method
                    "move r1 3", // r1 = 3
                    "move r2 4", // r2 = 4
                    "push r1",   // stack 3
                    "push r2",   // stack 3 4
                    "call 20",   // stack 3 4 RN -> 1st add method call
                    "push r2",   // stack 7 4
                    "call 20",   // stack 7 4 RN -> 2nd add method call
                    "interrupt 0", // r3 = 11
                    "interrupt 1",
                    "halt",

                    // add method
                    "pop r15",   // stack 3 4 // r15 = RN -> 12
                         // 2nd run stack 7 4 // r15 = RN -> 16

                    "pop r2",    // stack 3 // r2 = 4
                         // 2nd run stack 7 // r2 = 4

                    "pop r1",    // stack   // r1 = 3
                         // 2nd run stack   // r1 = 7

                    "add r1 r2 r3", // r3 = r1 + r2

                    "push r3",   // stack 7
                         // 2nd run stack 11

                    "push r15",  // stack 7  RN -> 12
                         // 2nd run stack 11 RN -> 16

                    "return"     // stack 7
                         // 2nd run stack 11
            };

            computer.preload(Assembler.assemble(test));
            computer.run();
        }
    }

}
