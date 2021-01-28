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


vector<menu> men;		// ��� �޴� ������ �����ص� vector�� �ڷᱸ��
vector<barista> bari;	// ��� �ٸ���Ÿ ���� �����ص� vector�� �ڷᱸ��
queue<order> ord;		// ��� �ֹ������� �����ص� queue�� �ڷᱸ��
//resultObserver *observer;
string drinkName;	// �޴� �̸�

// main �ȿ� ����ϰ� �ؼ� �����ϱ� ���ϰ� �ϱ� ���� �׽�Ʈ �Լ� ���� ����
void load() {
	string menu;
	string barista;
	string order;

	//menu = "menu.txt";

	order = "order.txt"; // ���� �ð��뿡 �ֹ��� ������ ������ ���
	barista = "barista.txt";

	menu = "menu2.txt"; // ��޿� ������� �ֹ��� �й��ϴ� ���
	
	//order = "order2.txt"; // �ֹ��� ���� �ð��� ���� ������ �ٸ���Ÿ�� ���� ���
	//barista = "barista2.txt"; // ���� ���ǿ��� ���� ������ �ٸ���Ÿ�� ���� ���
	
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
�޴��� ���� �����͸� �о�´�
****************************/
void loadMenuFile(string fileName) {

	int i;
	int numOfMenu;	// �� �޴��� ����
	int makeTime, drinkPrice;	// �޴��� ����µ� �ɸ��� �ð�, �޴��� ����
	int makableRank;	// �� �޴��� ����µ� �ʿ��� �ٸ���Ÿ ��ũ

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
�ٸ���Ÿ�� ���� �����͸� �о�´�
********************************/
void loadBaristaFile(string fileName) {

	int i, j;
	int numOfBarista;	// �� �ٸ���Ÿ�� ����
	int rank;	// �ٸ���Ÿ�� ��ũ (0 ~ 5)
		
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
�ֹ��� ���� �����͸� ť�� �����Ѵ�
**********************************/
void loadOrderFile(string fileName) {

	int orderNum;		// �ֹ���ȣ
	int customerNum;	// ����ȣ
	string orderTime;	// �ֹ��ð�
	int numOfDrink;		// �ֹ�����

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