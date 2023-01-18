#!/usr/bin/env python3.10
import os
import pandas
import matplotlib.pyplot as plt
import numpy as np
from skimage.io import imread
from skimage.transform import resize


target = []
flat_data = []
images = []
DataDirectory = '/Users/radupopescu/Downloads/animalface/Image'

Categories = ["puppy", "kitten"]

for i in Categories:
    # print("Category is:", i, "\tLabel encoded as:", Categories.index(i))
    # Encode categories cute puppy as 0, icecream cone as 1 and red rose as 2
    target_class = Categories.index(i)
    # Create data path for all folders under MinorProject
    path = os.path.join(DataDirectory, i)
    # Image resizing, to ensure all images are of same dimensions
    for img in os.listdir(path):
        if "jpg" in img :
            img_array = imread(os.path.join(path, img))
            # Skimage normalizes the value of image
            img_resized = resize(img_array, (150, 150, 3))
            flat_data.append(img_resized.flatten())
            images.append(img_resized)
            target.append(target_class)

flat_data = np.array(flat_data)
images = np.array(images)
target = np.array(target)
df = pandas.DataFrame(flat_data)
df['Target'] = target
# Rows are all the input images (90 images, 30 of each category)


# Display 1 resized image
# print(plt.imshow(images[10]))

# Split data into input and output sets
from sklearn.model_selection import train_test_split

# x is all input values of images and their pixel values (90 images * 67500)
# y is output values or correct label of image (90 images * 1 column of labels)

x = df.iloc[:, :-1].values
y = target
# print("Input data dimensions:", x.shape)
# print("Output data dimensions:", y.shape)

# Stratify ensures every image is divided in equal proportions (no bias)
x_train, x_test, y_train, y_test = train_test_split(x, y, shuffle=True, test_size=0.3, random_state=109, stratify=y)
# print("Dimensions of input training data:", x_train.shape)
# print("Dimensions of input testing data:", x_test.shape)
# print("Dimensions of output training data:", y_train.shape)
# print("Dimensions of output testing data:", y_test.shape)
#
# # Check if testing and training data are divided in equal proportions
# print("Labels\t\t   Image index considered")
# print(np.unique(y_train, return_counts=True))
# print(np.unique(y_test, return_counts=True))

# Applying Support Vector Machine classifier
from sklearn.model_selection import GridSearchCV
from sklearn.svm import SVC

# Set the parameters by cross-validation
tuned_parameters = [{'kernel': ['rbf'], 'gamma': [1e-3, 1e-4],
                     'C': [1, 10, 100, 1000]}]

# Apply GridSearchCV to find best parameters for given dataset
# verbose is used to describe the steps taken to find best parameters
cv = GridSearchCV(SVC(), tuned_parameters, refit=True, verbose=3)
cv.fit(x_train, y_train)

# Display parameters selected by GridSearchCV for SVM 3 classes
# Parameters obtained: {'C': 10, 'gamma': 0.0001, 'kernel': 'rbf'}
# print("Best parameters to apply are:", cv.best_params_)
# Display model after hyperparameter tuning
svm = cv.best_estimator_
# print("Model after tuning is:\n", svm)

# Predict the output of model after above parameters are applied to it
y_prediction = svm.predict(x_test)

# Print expected and predicted output
# print("Expected results: ", y_test)
# print("Predicted results:", y_prediction)

# Evaluate the model using confusion matrix, classification report and accuracy

from sklearn.metrics import confusion_matrix, classification_report, accuracy_score

# print("Confusion matrix results:\n", confusion_matrix(y_prediction, y_test))
# print("\nClassification report of model:\n", classification_report(y_prediction, y_test))
# print("Accuracy score:", 100 * accuracy_score(y_prediction, y_test))

import pickle

# Save SVM model in pickle file
pickle.dump(svm, open("Classification_Model.p", "wb"))

# Read byte from pickle model
test_model = pickle.load(open("Classification_Model.p", "rb"))

# Testing for a new image
flat_data = []
img_array = imread('/Users/radupopescu/Documents/GitHub/ImageInterpretor/src/main/resources/pyImages/d.jpg')
# Resize image
img_resized = resize(img_array, (150, 150, 3))
flat_data.append(img_resized.flatten())
flat_data = np.array(flat_data)
#print("Dimensions of original image are:", img_array.shape)
plt.imshow(img_resized)
y_output = test_model.predict(flat_data)
y_output = Categories[y_output[0]]
print("PREDICTED OUTPUT IS:", y_output)
