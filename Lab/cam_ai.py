import requests
import time
from keras.models import load_model
from PIL import Image, ImageOps
import numpy as np
import cv2
import base64

#img_url = 'http://10.128.47.201:8080/photo.jpg'
img_url = 'http://192.168.100.13:8080/photo.jpg'
control_url = 'http://192.168.98.221/control?ai_camera='
counter = 0
model = load_model('keras_model.h5')

class_names = ["0 Không khẩu trang", "1 Đeo khẩu trang", "2 Không có người"]

def image_detector():
    # Create the array of the right shape to feed into the keras model
    # The 'length' or number of images you can put into the array is
    # determined by the first position in the shape tuple, in this case 1.
    data = np.ndarray(shape=(1, 224, 224, 3), dtype=np.float32)
    # Replace this with the path to your image
    image = Image.open('Pics//greenland_' + str(counter) +'.png')
    image_path = 'Pics//greenland_' + str(counter) +'.png'

    with open(image_path, 'rb') as f:
        image_data = f.read()

    encoded_image = base64.b64encode(image_data)
    if len(encoded_image) > 102400:
        print("Image is too big!")
        print(len(encoded_image))
    else:
        print("Publish image:")
        print(len(encoded_image))

    #resize the image to a 224x224 with the same strategy as in TM2:
    #resizing the image to be at least 224x224 and then cropping from the center
    size = (224, 224)
    image = ImageOps.fit(image, size, Image.LANCZOS)

    #turn the image into a numpy array
    image_array = np.asarray(image)
    # Normalize the image
    normalized_image_array = (image_array.astype(np.float32) / 127.0) - 1
    # Load the image into the array
    data[0] = normalized_image_array

    # run the inference
    prediction = model.predict(data)

    #get the 1D array
    output = prediction[0]
    #assign default value for max confidence
    max_index = 0
    max_confidence = output[0]
    #find the maximum confidence and its index
    for i in range(1, len(output)):
        if max_confidence < output[i]:
            max_confidence = output[i]
            max_index = i
    print(max_index, max_confidence)

    file = open("labels.txt",encoding="utf8")
    data = file.read().split("\n")
    print("AI Result: ", data[max_index])
    #client.publish("ai", data[max_index])
    return data[max_index], encoded_image

#image = cv2.imread('Pics/greenland_' + str(counter) +'.png')


# counter = 0
def image_capture():
    counter = 0
    print("Capturing...", counter)
    # counter = counter + 1
    response = requests.get(img_url)
    if response.status_code:
        fp = open('Pics//greenland_' + str(counter) +'.png', 'wb')
        fp.write(response.content)
        fp.close()
        #image = cv2.imread('Pics//greenland_' + str(counter - 1) +'.png')
        #cv2.imshow('AI Camera', response.content)
        result = image_detector()

        # requests.get(control_url + result)

    # time.sleep(1)