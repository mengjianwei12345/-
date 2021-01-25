class LoadBalancer:
    def __init__(self):
        self.packet_list = []
        self.packets_sent= 0

    def sendPacketToServer(self, packet_id):
        self.packets_sent += 1
        self.packet_list[packet_id].pop()