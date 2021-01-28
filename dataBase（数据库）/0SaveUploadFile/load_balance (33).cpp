//
//  RandomLoadBalancer.cpp
//  csaw
//
//  Created by Steve on 25/12/2016.
//  Copyright © 2016 Stephen Tierney. All rights reserved.
//

#include "RandomLoadBalancer.hpp"
#include <random>

RandomLoadBalancer::RandomLoadBalancer() {
    shutdown_requested = false;
}

void RandomLoadBalancer::wait_finish() {
    std::cout << "Random Load Balancer Stopping!\n";
    
    shutdown_requested = true;
    
    task_q_cv.notify_all();
    
    cur_thread.join();
    
    std::cout << "Random Load Balancer Stopped!\n";
}

void RandomLoadBalancer::add_server(Server *new_server)
{
    std::lock_guard<std::mutex> lock(server_v_mutex);
    server_vec.push_back(new_server);
}

void RandomLoadBalancer::start()
{
    if (server_vec.size() > 0)
    {
        cur_thread = std::thread (&RandomLoadBalancer::worker_loop, this);
    }
}

void RandomLoadBalancer::DoRequest(int id) {
    
    std::lock_guard<std::mutex> lock(task_q_mutex);
    task_q.push(id);
    task_q_cv.notify_all();
    
    std::cout << "Task added to queue!\n";
}


void RandomLoadBalancer::worker_loop() {
    
    for (;;) {
        // Check if we should shutdown
        if (task_q.empty() && shutdown_requested)
        {
            break;
        }
        else
        {
            std::unique_lock<std::mutex> lock(task_q_mutex);
            
            // Shutdown clause used to break loop when woken due to shutdown message
            while (task_q.empty() && !shutdown_requested)
            {
                task_q_cv.wait(lock);
            }
            
            if (!task_q.empty())
            {
                int id = task_q.front();
                task_q.pop();
                lock.unlock();
                
                std::unique_lock<std::mutex> lock(server_v_mutex);
                
                // Send task to a random server
                std::random_device seed;
                std::mt19937 engine( seed( ) ) ;
                std::uniform_int_distribution<int> choose( 0 , server_vec.size( ) - 1 ) ;
                server_vec[choose(engine)]->DoRequest(id);
                
            }
        }
    }
}


void RandomLoadBalancer::process_task(int id) {
    // Do work
    struct timespec tim_in, tim_out;
    tim_in.tv_sec = 0L;
    tim_in.tv_nsec = 50000000;
    
    nanosleep(&tim_in, &tim_out);
    
    std::cout << "Task " << id << " complete!\n";
}

