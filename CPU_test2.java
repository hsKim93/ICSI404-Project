package the_bit;

public class CPU_test2 {

    public void runTests() {

        /* test 1
         * testing jump
         *
         * register 1 will be empty
         * will not print memory
         */
        {
            System.out.println("-------------- test 1  --------------");
            System.out.println("Testing jump");
            Computer computer = new Computer();
            String[] test = new String[]{
                    "jump 6",      // jump to "interrupt 1"
                    "move r1 -1",  // will NOT be executed
                    "interrupt 1", // will NOT be executed
                    "move r2 -1",  // jumped
                    "interrupt 0",
                    "halt"
            };
            test = Assembler.assemble(test);
            computer.preload(test);
            computer.run();
        }

        /* test 2
         * testing compare and branch
         *
         * register 2, 4, 8 will be empty
         */
        {
            System.out.println("-------------- test 2 --------------");
            System.out.println("Testing compare and branch");
            Computer computer = new Computer();
            String[] test = new String[]{
                    "move r0 -1",
                    "move r1 1",
                    "compare r0 r1",  // 0 - 1 result -> 10
                    "branchifne 4",   // will branch to "move r3 3"
                    "move r2 2",      // will NOT be executed
                    "move r3 3",
                    "compare r0 r0",  // 0 - 0 result -> 00
                    "branchife 4",    // will branch to "move r5 5"
                    "move r4 4",      // will NOT be executed
                    "move r5 5",
                    "branchifgt 4",   // will NOT branch to "move r7 7"
                    "move r6 6",
                    "move r7 7",
                    "compare r1 r0",  // 1 - 0 result -> 01
                    "branchifgtoe 4", // will branch to " move r9 9"
                    "move r8 8",      // will NOT be executed
                    "move r9 9",
                    "interrupt 0",
                    "halt"
            };
            test = Assembler.assemble(test);
            computer.preload(test);
            computer.run();
        }

        /* test 3
         * testing loop
         *
         * will be same as
         * for (int i=0; i<10; i++){
         *      r0++;
         * }
         * r0 will be 10
         *
         * also tests "branch negative address"
         */
        {
            System.out.println("-------------- test 3 --------------");
            System.out.println("Testing loop");
            Computer computer = new Computer();
            String[] test = new String[]{
                    "move r1 1",
                    "move r2 10",
                    "add r0 r1 r0", // r0++
                    "compare r2 r0", // r2 > r0 until r0 = 5
                    "branchifgt -4", //
                    "interrupt 0",
                    "halt"
            };
            test = Assembler.assemble(test);
            computer.preload(test);
            computer.run();
        }
    }
}
