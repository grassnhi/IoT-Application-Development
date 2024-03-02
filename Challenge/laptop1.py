import cv2
import socket
import pickle
import base64
import numpy as np
from keras.models import load_model
import io

# Connect to the 2nd laptop
server_address = ('10.128.158.79', 12345)
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(server_address)

# Receive the pre-trained face recognition model from laptop1
model_data = b""
while True:
    data = client_socket.recv(4096)
    if not data:
        break
    model_data += data

model = load_model(io.BytesIO(model_data), compile=False)

# Label names corresponding to the output classes of the model
class_names = ["Person_A", "Person_B", "Nobody"]

# CAMERA can be 0 or 1 based on the default camera of your computer
camera = cv2.VideoCapture(0)

def send_labeled_face(image, label):
    # Serialize the image and label
    image_data = base64.b64encode(cv2.imencode('.jpg', image)[1]).decode('utf-8')
    data = {'label': label, 'image_data': image_data}

    # Send the data to Laptop 2
    serialized_data = pickle.dumps(data)
    client_socket.sendall(serialized_data)

while True:
    ret, image = camera.read()

    if ret:  # Check if the frame was read successfully

        # Preprocess the image for prediction (adjust as needed)
        processed_image = preprocess_image(image)

        # Predict using the pre-trained model
        prediction = model.predict(np.expand_dims(processed_image, axis=0))[0]
        predicted_label = class_names[np.argmax(prediction)]

        # Send label and image to Laptop 2
        send_labeled_face(image, predicted_label)

        # Show the image with the label
        cv2.imshow("Webcam Image", image)

    # Break the loop when 'ESC' key is pressed
    keyboard_input = cv2.waitKey(1)
    if keyboard_input == 27:
        break

# Close the connection and release the camera when done
client_socket.close()
camera.release()
cv2.destroyAllWindows()
