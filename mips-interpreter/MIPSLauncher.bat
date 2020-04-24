@echo on

set in_file = %1
set s_file = %2
set debug = %3

java -cp build main.java.mips.MIPSInterpreter %debug%--in %in_file% %s_file%

@echo on