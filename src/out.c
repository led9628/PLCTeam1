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
void  main () {
int x = 5 ;
if ( x == 5 ) { print_s( "Yes" ) ;
} else if ( x == 4 ) { print_s( "Maybe" ) ;
} else { print_s( "No" ) ;
} }

