import socket
import pickle
import numpy as np
from sklearn.svm import SVC
from sklearn.externals import joblib
import base64
import face_recognition

def load_labeled_data(dataset_dir):
    #  assuming your dataset structure is organized by person folders:
    X = []
    y = []
    for person_folder in os.listdir(dataset_dir):
        person_label = person_folder  # You may need to map person_folder to a numerical label
        person_folder_path = os.path.join(dataset_dir, person_folder)
        for image_file in os.listdir(person_folder_path):
            image_path = os.path.join(person_folder_path, image_file)
            face_encoding = load_and_process_face(image_path)  # Implement this function
            X.append(face_encoding)
            y.append(person_label)
    
    return X, y

def load_and_process_face(image_path):
    
    image = face_recognition.load_image_file(image_path)
    face_encoding = face_recognition.face_encodings(image)[0]
    
    return face_encoding

# Setup a server to listen for connections
server_address = ('<IP_ADDRESS_OF_LAPTOP2>', 12345)
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(server_address)
server_socket.listen(1)

print(f"Listening on {server_address}")

# Accept a connection from laptop1
client_socket, client_address = server_socket.accept()

# Load labeled data
dataset_dir = 'Dataset'  
X, y = load_labeled_data(dataset_dir)

while True:
    # Receive labeled face from laptop1
    data = client_socket.recv(4096)
    if not data:
        break  # Break the loop if no data received

    received_data = pickle.loads(data)
    label = received_data['label']
    image_data = base64.b64decode(received_data['image_data'])
    
    # Process image_data as needed
    image = process_received_image(image_data)  # Implement this function

    # Perform additional labeling if needed
    labeled_face = label_face(load_and_process_face(image))

    # Add the labeled face to the training data
    X.append(load_and_process_face(image))
    y.append(labeled_face)

# Train a face recognition model
model = SVC()
model.fit(X, y)

# Save the trained model
joblib.dump(model, 'face_recognition_model.pkl')

# Send the trained model back to laptop1
with open('face_recognition_model.pkl', 'rb') as model_file:
    model_data = model_file.read()
    client_socket.sendall(model_data)

client_socket.close()
server_socket.close()
