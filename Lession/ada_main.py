from adafruit_mqtt import Adafruit_MQTT

def your_func(message):
    print("Receied in main", message)
    #TODO

obj = Adafruit_MQTT()

obj.setRecvCallBack(your_func)

while True:
    pass