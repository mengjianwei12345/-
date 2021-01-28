#include <iostream>
#include <fstream>
#include <queue>
#include "menu.h"
#include "barista.h"
#include "order.h"
#include "myTime.h"
#include "loadBalancing.h"

using namespace std;

// fstream functions
void loadMenuFile(string fileName);
void loadBaristaFile(string fileName);
void loadOrderFile(string fileName);


vector<menu> men;		// 모든 메뉴 정보를 저장해둘 vector형 자료구조
vector<barista> bari;	// 모든 바리스타 정보 저장해둘 vector형 자료구조
queue<order> ord;		// 모든 주문정보를 저장해둘 queue형 자료구조
//resultObserver *observer;
string drinkName;	// 메뉴 이름

// main 안에 깔끔하게 해서 구분하기 편하게 하기 위해 테스트 함수 따로 뺀거
void load() {
	string menu;
	string barista;
	string order;

	//menu = "menu.txt";

	order = "order.txt"; // 같은 시간대에 주문이 여러개 들어오는 경우
	barista = "barista.txt";

	menu = "menu2.txt"; // 등급에 상관없이 주문을 분배하는 경우
	
	//order = "order2.txt"; // 주문이 들어온 시간에 제조 가능한 바리스타가 없는 경우
	//barista = "barista2.txt"; // 위에 조건에서 제조 가능한 바리스타가 둘인 경우
	
	loadMenuFile(menu);
	loadBaristaFile(barista);
	loadOrderFile(order);
	cout << endl;
}
void test();

int main() {

	load();
	start();
	
	return 0;
}

/****************************
메뉴에 대한 데이터를 읽어온다
****************************/
void loadMenuFile(string fileName) {

	int i;
	int numOfMenu;	// 총 메뉴의 숫자
	int makeTime, drinkPrice;	// 메뉴를 만드는데 걸리는 시간, 메뉴의 가격
	int makableRank;	// 이 메뉴를 만드는데 필요한 바리스타 랭크

	cout << fileName << " loading" << endl;
	ifstream in(fileName);
	if (in.fail()) {
		cout << fileName << " open fail" << endl;
		return;
	}

	in >> numOfMenu;
	for (i = 0; i < numOfMenu; i++) {
		in >> drinkName >> makeTime >> drinkPrice >> makableRank;
		menu temp(drinkName, makeTime, drinkPrice, makableRank);
		men.push_back(temp);
	}
	cout << fileName << " load complete" << endl;
	in.close();
}

/********************************
바리스타에 대한 데이터를 읽어온다
********************************/
void loadBaristaFile(string fileName) {

	int i, j;
	int numOfBarista;	// 총 바리스타의 숫자
	int rank;	// 바리스타의 랭크 (0 ~ 5)
		
	cout << fileName << " loading" << endl;
	ifstream in(fileName);
	if (in.fail()) {
		cout << fileName << " open fail" << endl;
		return;
	}

	in >> numOfBarista;
	for (i = 0; i < numOfBarista; i++) {
		in >> rank;
		barista temp(rank);
		bari.push_back(temp);
	}
	cout << fileName << " load complete" << endl;
	in.close();
}

/*********************************
주문에 대한 데이터를 큐에 저장한다
**********************************/
void loadOrderFile(string fileName) {

	int orderNum;		// 주문번호
	int customerNum;	// 고객번호
	string orderTime;	// 주문시간
	int numOfDrink;		// 주문수량

	cout << fileName << " loading" << endl;
	ifstream in(fileName);
	if (in.fail()) {
		cout << fileName << " open fail" << endl;
		return;
	}

	while (!in.eof()) {
		in >> orderNum >> customerNum >> orderTime >> drinkName >> numOfDrink;
		order temp(orderNum, customerNum, orderTime, drinkName, numOfDrink);
		ord.push(temp);
	}
	cout << fileName << " load complete" << endl;
	in.close();
}