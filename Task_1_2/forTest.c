#include "stdio.h"
#include "stdlib.h"

int main(){
	long long chunkSize, chunks;
	printf("chunkSize: ");
	scanf("%lld", &chunkSize);
	printf("chunks: ");
	scanf("%lld", &chunks);
	char * chunk = (char*)malloc(chunkSize);
	chunk[0] = '\0';
	for(long long i = 0 ; i < chunkSize ; i++)
		sprintf(chunk,"%sl", chunk);
	FILE * f = fopen("input.txt", "w");
	for(long long i = 0 ; i < chunks ; i++)
		fprintf(f, chunk);
	fprintf(f, "loh");
	fflush(f);
	fclose(f);
	return 0;
}