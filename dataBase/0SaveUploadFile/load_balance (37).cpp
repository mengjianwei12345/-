/*SeyyedMohammad Hosseini
Seyyedmohammad.ir
Iasbs.ac.ir */
#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
using namespace std;

#include "mpi.h"
#pragma comment(lib, "msmpi.lib")

#define ROUND(a) ((int) (a + 0.5)) 

FILE * fp;
char *filename = "new1.bmp";    //bmp version
unsigned char *img = NULL;


typedef struct {
	float x, y;
} Complex;

Complex complexSquare(Complex c){
	Complex cSq;
	cSq.x = c.x * c.x - c.y * c.y;
	cSq.y = 2 * c.x * c.y;
	return cSq;
}

int iterate(Complex zInit, int maxIter){
	Complex z = zInit;
	int cnt = 0;
	while ((z.x * z.x + z.y * z.y <= 4) && (cnt < maxIter)){
		z = complexSquare(z);
		z.x += zInit.x;
		z.y += zInit.y;
		cnt++;
	}
	return cnt;
}

void madelbrot(int nx, int min_ny, int ny, int maxIter, float realMin, float realMax, float imagMin, float imagMax){
	static unsigned char color[3];
	float realInc = (realMax - realMin) / nx;
	float imagInc = (imagMax - imagMin) / ny;
	int max;
	Complex z;
	int x, y, w, h;
	int cnt;
	int counter = 0;
	int temp = min_ny;
	for (x = 0, z.x = realMin; x < nx; x++, z.x += realInc){
		for (y = 0, z.y = imagMin; y < ny; y++, z.y += imagInc){
			w = x;
			h = (ny - 1) - y;
			if (y == min_ny){
				cnt = iterate(z, maxIter);
				if (cnt == maxIter)
				{
					color[0] = 0;
					color[1] = 0;
					color[2] = 0;
				}
				else{
					double c = 3 * log((double)cnt) / log(maxIter - 1.0);
					if (c < 1)
					{
						color[0] = 255 * c;
						color[1] = 0;
						color[2] = 0;
					}
					else if (c < 2)
					{
						color[0] = 255;
						color[1] = 255 * (c - 1);
						color[2] = 0;
					}
					else
					{
						color[0] = 255;
						color[1] = 255;
						color[2] = 255 * (c - 2);
					}
				}
			}
			//putting colours in corresponding pixel (w,h)
			img[(w + h*nx) * 3 + 2] = color[0];
			img[(w + h*nx) * 3 + 1] = color[1];
			img[(w + h*nx) * 3 + 0] = color[2];
		}
	}
}

void main(int argc, char *argv[]){
	int myid, numprocs;
	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
	MPI_Comm_rank(MPI_COMM_WORLD, &myid);

	//initial setting
	const int MaxColorComponentValue = 255;
	const int iXmax = 900;
	const int iYmax = 600;
	const int iterations = 1000;
	const int realMin = -2.0;
	const int realMax = 1.0;
	const int imagMin = -1.0;
	const int imagMax = 1.0;

	int filesize = 54 + 3 * iXmax * iYmax;
	if (img) delete img;


	img = new unsigned char[3 * iXmax * iYmax];
	memset(img, 0, sizeof(img));

	//--- Parallel
	if (myid == 0){
		int row_counter = 0;
		int req_id;
		int all_proccess_done = 1;
		MPI_Status status;
		MPI_Status status2;
		for (int i = 1; i < numprocs; i++) {
			MPI_Send(&row_counter, 1, MPI_INT, i, 2, MPI_COMM_WORLD);
			row_counter++;
		}
		while (true)
		{
			MPI_Recv(&req_id, 1, MPI_INT, MPI_ANY_SOURCE, 1, MPI_COMM_WORLD, &status); // Recive Data Row Id
			MPI_Recv(&img[req_id * 900 * 3], 900 * 3, MPI_UNSIGNED_CHAR, MPI_ANY_SOURCE, 4, MPI_COMM_WORLD, &status2); //Recive Data
			if (row_counter < iYmax - 1){
				MPI_Send(&row_counter, 1, MPI_INT, status.MPI_SOURCE, 2, MPI_COMM_WORLD); // Sent Data Tag
				row_counter++;
			}
			else{
				MPI_Send(&row_counter, 1, MPI_INT, status.MPI_SOURCE, 3, MPI_COMM_WORLD); // Sent Finish Tag
				all_proccess_done++;
			}

			if (all_proccess_done == numprocs)
				break;
		}
		unsigned char bmpfileheader[14] = { 'B', 'M', 0, 0, 0, 0, 0, 0, 0, 0, 54, 0, 0, 0 };
		unsigned char bmpinfoheader[40] = { 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 24, 0 };
		unsigned char bmppad[3] = { 0, 0, 0 };

		bmpfileheader[2] = (unsigned char)(filesize);
		bmpfileheader[3] = (unsigned char)(filesize >> 8);
		bmpfileheader[4] = (unsigned char)(filesize >> 16);
		bmpfileheader[5] = (unsigned char)(filesize >> 24);

		bmpinfoheader[4] = (unsigned char)(iXmax);
		bmpinfoheader[5] = (unsigned char)(iXmax >> 8);
		bmpinfoheader[6] = (unsigned char)(iXmax >> 16);
		bmpinfoheader[7] = (unsigned char)(iXmax >> 24);
		bmpinfoheader[8] = (unsigned char)(iYmax);
		bmpinfoheader[9] = (unsigned char)(iYmax >> 8);
		bmpinfoheader[10] = (unsigned char)(iYmax >> 16);
		bmpinfoheader[11] = (unsigned char)(iYmax >> 24);
		fp = fopen(filename, "wb");
		fwrite(bmpfileheader, 1, 14, fp);
		fwrite(bmpinfoheader, 1, 40, fp);
		for (int i = 0; i < iYmax; i++){
			fwrite(img + (iXmax *(iYmax - i - 1) * 3), 3, iXmax, fp);
			fwrite(bmppad, 1, (4 - (iXmax * 3) % 4) % 4, fp);
		}
		fclose(fp);
	}
	else{
		int position;
		MPI_Status status;
		while (true)
		{
			MPI_Recv(&position, 1, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
			if (status.MPI_TAG == 3)
				break;
			madelbrot(iXmax, position, iYmax, iterations, realMin, realMax, imagMin, imagMax);
			MPI_Send(&position, 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
			MPI_Send(&img[position * 900 * 3], 900 * 3, MPI_UNSIGNED_CHAR, 0, 4, MPI_COMM_WORLD);
		}
		printf("I'm %d And Finish My Work", myid);
	}
	MPI_Finalize();
}
