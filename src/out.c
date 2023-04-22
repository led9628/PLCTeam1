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
void  testIf (double d ,int x ) {
if ( d > 5.1 ) { print_s( "Hi" ) ;
} else if ( d > 0.1 ) { print_i( x ) ;
} else { print_i( 3.2 * 4.0 ) ;
} }
int  testIfLoop (int x ) {
int output = 1 ;
bool _switch= true;
while ( x > 0 ) { if ( _switch) { output = output * 2 ;
_switch= false;
} else { _switch= true;
} x = x - 1 ;
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
print_s( testConcatLoop ( str ) ) ;
testIf ( 1.1 , 3 ) ;
int intI ;
intI = testIfLoop ( 3 ) ;
print_i( intI ) ;
testFuncCallLoop ( 5 ) ;
}

