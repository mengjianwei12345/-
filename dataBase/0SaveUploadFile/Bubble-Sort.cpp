/* ========================================================================== 
 *	PROGRAM Sorting Algorithms
 *
 *		AUTHOR: Steven Burgart
 *		FSU MAIL ID: skb08c@my.fsu.edu
 *		COP4531 - Fall 2011
 *		PROJECT NUMBER: 5
 *		DATE: 12/1/11
 *
 *	Description:	This program implements and benchmarks the following
 *              sorting algorithms:
 * 
 *              Bubble Sort, Bi-Directional Bubble Sort, Bitonic Sort
 *
 * ========================================================================== */
#include <iostream>
#include <stdlib.h>
#include <sys/time.h>
using namespace std;

// Sort function typedef
typedef void (*SortFunction)(int*, int);

// Method prototypes - documentation in declarations
void SingleTest(SortFunction, int, int);
void RunTestSuite();
void PrintArray(int*, int);
int *RandomArray(int, int);
int *CopyArray(int*, int);
void PrintTimeDiff(timeval, timeval);
void BubbleSort(int*, int);
void BiBubbleSort(int*, int);
void BitonicSort(int*, int);
void SortUp(int*, int, int);
void SortDown(int*, int, int);
void MergeUp(int*, int, int);
void MergeDown(int*, int, int);

// ========================================================================== //
// Method Name - Main
//      Simply run the benchmarks defined below
// ========================================================================== //
int main() {
    RunTestSuite();

    return 0;
}

// ========================================================================== //
// Method Name - RunTestSuite
//      Run a series of benchmarks based on the constant parameters declared
//      at the beginning of the method. 
// ========================================================================== //
void RunTestSuite() {
    const int NUM_SORT_FUNCS = 2;
    const int MAX_KEY_VALUE = 500;
    const int NUM_TESTS = 11;
    const int NUM_BIT_TESTS = 14;
    const int NUM_SAMPLES = 10;
    const int TEST_SIZES[] = {10, 50, 100, 500, 1000, 5000,
                              10000, 50000, 100000, 500000, 1000000};

    const int BIT_TEST_SIZES[] = {16, 32, 64, 128, 256, 512, 1024, 2048, 4096,
                                  8192, 16384, 32768, 65536, 131072};

    const string sortFuncNames[] = {"Bubble Sort", "Bi-Bubble Sort"};
    const SortFunction sortFuncs[] = {BubbleSort, BiBubbleSort};

    timeval start, end;
    int *tmpArray;

    cout << "Bitonic Sort Results:" << endl;
    for (int i = 0; i < NUM_BIT_TESTS; ++i) {
        int N = BIT_TEST_SIZES[i];
        cout << "N=" << N << " -> ";

        // Generate random samples
        for (int i = 0; i < NUM_SAMPLES; ++i) {
            tmpArray = RandomArray(N, MAX_KEY_VALUE);
            gettimeofday(&start, NULL);
            BitonicSort(tmpArray, N);
            gettimeofday(&end, NULL);
            PrintTimeDiff(start, end);
            delete[] tmpArray;
        }
        cout << endl;
    }
    cout << endl;

    for (int i = 0; i < NUM_TESTS; ++i) {
        int N = TEST_SIZES[i];
        cout << "Sorting with N=" << N << endl;

        // Generate random samples
        int **randArrays = new int*[NUM_SAMPLES];
        for (int i = 0; i < NUM_SAMPLES; ++i)
            randArrays[i] = RandomArray(N, MAX_KEY_VALUE);

        for (int j = 0; j < NUM_SORT_FUNCS; ++j) {
            cout << sortFuncNames[j] << '\t';
            for (int k = 0; k < NUM_SAMPLES; ++k) {
                tmpArray = CopyArray(randArrays[k], N);
                gettimeofday(&start, NULL);
                (*sortFuncs[j])(tmpArray, N);
                gettimeofday(&end, NULL);
                PrintTimeDiff(start, end);
                delete[] tmpArray;
            }
            cout << endl;
        }
        cout << endl;

        for (int j = 0; j < NUM_SAMPLES; ++j)
            delete[] randArrays[j];
        delete[] randArrays;
    }
}

// ========================================================================== //
// Method Name - SingleTest
//      For debugging purposes, quickly tests a single sort algorithm
// ========================================================================== //
void SingleTest(SortFunction func, int size, int maxKey) {
    int *test = RandomArray(size, maxKey);

    PrintArray(test, size);
    (*func)(test, size);
    PrintArray(test, size);

    delete[] test;
}

// ========================================================================== //
// Method Name - PrintArray
//      Simple method to print an array.
// ========================================================================== //
void PrintArray(int *ary, int size) {
    for (int i = 0; i < size; ++i) {
        cout << ary[i] << " ";
    }
    cout << endl;
}

// ========================================================================== //
// Method Name - RandomArray
//      Returns an integer array based on the size and max key value.
// ========================================================================== //
int *RandomArray(int size, int maxRandNum) {
    int *randArray = new int[size];
    timeval timeseed;
    gettimeofday(&timeseed, NULL);

    srand(timeseed.tv_usec);
    for (int i = 0; i < size; ++i) {
        randArray[i] = (rand() % maxRandNum) + 1;
    }

    return randArray;
}

// ========================================================================== //
// Method Name - CopyArray
//      Returns a deep copy of an array.
// ========================================================================== //
int *CopyArray(int *ary, int size) {
    int *tmp = new int[size];

    for (int i = 0; i < size; ++i)
        tmp[i] = ary[i];

    return tmp;
}

// ========================================================================== //
// Method Name - PrintTimeDiff
//      Prints the time difference, in microseconds, of two time parameters.
// ========================================================================== //
void PrintTimeDiff(timeval start, timeval end) {
    long seconds = end.tv_sec - start.tv_sec;
    long useconds = end.tv_usec - start.tv_usec;
    long mtime = ((seconds * 1000000) + useconds);

    cout << "\t" << mtime;
}

// ========================================================================== //
// Method Name - BubbleSort
//      Implementation of the bubble sort algorithm for integers.
// ========================================================================== //
void BubbleSort(int *ary, int size) {
    bool swapped = true;
    int tmp;

    while (swapped == true) {
        swapped = false;

        for (int i = 0; i < size - 1; ++i)
            if (ary[i] > ary[i + 1]) {
                tmp = ary[i];
                ary[i] = ary[i + 1];
                ary[i + 1] = tmp;
                swapped = true;
            }
    }
}

// ========================================================================== //
// Method Name - BiBubbleSort
//      Implementation of the bi-directional bubble sort algorithm for 
//      integers.
// ========================================================================== //
void BiBubbleSort(int *ary, int size) {
    bool swapped = true;
    int tmp;

    while (swapped == true) {
        swapped = false;

        for (int i = 0; i < size - 1; ++i)
            if (ary[i] > ary[i + 1]) {
                tmp = ary[i];
                ary[i] = ary[i + 1];
                ary[i + 1] = tmp;
                swapped = true;
            }

        for (int i = size - 1; i > 0; --i) {
            if (ary[i] < ary[i - 1]) {
                tmp = ary[i];
                ary[i] = ary[i - 1];
                ary[i - 1] = tmp;
                swapped = true;
            }
        }
    }
}

// ========================================================================== //
// Method Name - BitonicSort
//      Implementation of the bitonic sort algorithm for integers.
// ========================================================================== //
void BitonicSort(int* ary, int size) {
    SortUp(ary, 0, size);
}

// ========================================================================== //
// Method Name - SortUp
//      Used in bitonic sort, sorts the list upwards recursively
// ========================================================================== //
void SortUp(int* ary, int start, int offset) {
    if (offset > 1) {
        SortUp(ary, start, offset / 2);
        SortDown(ary, start + offset / 2, offset / 2);
        MergeUp(ary, start, offset / 2);
    }
}

// ========================================================================== //
// Method Name - SortDown
//      Used in bitonic sort, sorts the list downwards recursively
// ========================================================================== //
void SortDown(int* ary, int start, int offset) {
    if (offset > 1) {
        SortUp(ary, start, offset / 2);
        SortDown(ary, start + offset / 2, offset / 2);
        MergeDown(ary, start, offset / 2);
    }
}

// ========================================================================== //
// Method Name - MergeUp
//      Used in bitonic sort, merges the list recursively upwards
// ========================================================================== //
void MergeUp(int* ary, int start, int offset) {
    if (offset > 0) {
        for (int i = 0; i < offset; ++i)
            if (ary[start + i] > ary[start + offset + i]) {
                int tmp = ary[start + i];
                ary[start + i] = ary[start + offset + i];
                ary[start + offset + i] = tmp;
            }

        MergeUp(ary, start, offset / 2);
        MergeUp(ary, start + offset, offset / 2);
    }
}

// ========================================================================== //
// Method Name - MergeDown
//      Used in bitonic sort, merges the list recursively downwards
// ========================================================================== //
void MergeDown(int* ary, int start, int offset) {
    if (offset > 0) {
        for (int i = 0; i < offset; ++i)
            if (ary[start + i] < ary[start + offset + i]) {
                int tmp = ary[start + i];
                ary[start + i] = ary[start + offset + i];
                ary[start + offset + i] = tmp;
            }

        MergeDown(ary, start, offset / 2);
        MergeDown(ary, start + offset, offset / 2);
    }
}
