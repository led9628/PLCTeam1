#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
int length(char* s) {
    return strlen(s);
}
char* concat(char* s1, char* s2) {
    char* s = malloc(strlen(s1) + strlen(s2));
    strcpy(s, s1);
    return strcat(s, s2);
}
void print_s(char* s) {
    printf("%s\n", s);
}
void print_i(int i) {
    printf("%d\n", i);
}
void print_f(float d) {
    printf("%f\n", d);
}
void  testPrintLoop (int x ) {
while ( x > 0 ) { print_i( x ) ;
x = x - 1 ;
} }
char*  testConcatLoop (char* y ) {
int x = length ( y ) x = 1 ;
char* output = y ;
while ( x > 0 ) { output = concat ( output , y ) ;
x = x - 1 ;
} return output ;
}
void  foo () {
print_s( "ran foo" ) ;
}
void  testFuncCallLoop (int x ) {
while ( x > 0 ) { foo ( ) ;
x = x - 2 ;
} }
void  main () {
testPrintLoop ( 5 ) ;
char* str = "a1" ;
print_i( testConcatLoop ( str ) ) ;
testIf ( 1.1 , 3 ) ;
Integer  intI ;
intI = testIfLoop ( 3 ) ;
print_i( intI ) ;
testFuncCallLoop ( 5 ) ;
}

