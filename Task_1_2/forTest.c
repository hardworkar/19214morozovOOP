#include "stdio.h"

int main(){
	FILE * f = fopen("input.txt", "w");
	/*for(int i = 0 ; i < 90000000 ; i++){
		fprintf(f, "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
	}*/
	// 9000000000 + 2 bytes
	for(long long i = 0 ; i < 100000000ll ; i++){
		//fprintf(f, "l");
		fprintf(f, "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
	}
	fflush(f);
	fprintf(f, "loh");
	fflush(f);
	fclose(f);
	return 0;
}