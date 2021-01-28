// BucketSort.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <list>
#include "Juego.h"

int main()
{
    std::cout << "Hello World!\n";
    Juego witcher("Witcher 3", 10);
    Juego mario("Mario Oddisey", 8);
    Juego starcraft("Starcraft", 1);
    Juego minecraft("Minecraft", 10);
    Juego zelda("Zelda", 1);

    Juego juegos[] = { witcher , mario, starcraft, minecraft, zelda};

    int cantidadJuegos = 5;
    const int maximaNota = 10;

    std::list<Juego>* arregloListas =  new std::list<Juego>[maximaNota+1];

    // insertamos en las listas que estan dentro de los arreglos
    for (int i = 0; i < cantidadJuegos; i++) {
        int nota= juegos[i].Nota;
        arregloListas[nota].push_front(juegos[i]);
    }
    //
    std::list<Juego> resultado;
    for (int i = 0; i < maximaNota+1; i++) {
        std::list<Juego> porNota = arregloListas[i];
        while (!porNota.empty()) {
            Juego cabeza = porNota.front();
            resultado.push_back(cabeza);
            porNota.pop_front();
        }
    }
    
    for (int i = 0; i < cantidadJuegos; i++) {
        std::cout << resultado.front().ToString() << "\n";
        resultado.pop_front();
    }
        



    
}
