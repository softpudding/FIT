from keras.applications import vgg16
from keras.models import Model
import keras
from keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout, InputLayer
from keras.models import Sequential
from keras import optimizers
import pandas as pd
import os
import random
from skimage import transform
from PIL import Image
import skimage
import numpy as np
from sklearn.preprocessing import LabelEncoder
import matplotlib.pyplot as plt

batch_size =16
epochs = 100
SAVE_FOR_TEST = 100
SAMPLE_TYPES = 6 # 参与训练种类
EPOCHS = None
TRAIN_DATA_PATH = "C:/Users/11570/PycharmProjects/PaChong/image_single_object"
TEST_DATA_PATH = "C:/Users/11570/PycharmProjects/PaChong/image_single_object_test"

def load_data(data_directory):
    directories = [d for d in os.listdir(data_directory)
                   if os.path.isdir(os.path.join(data_directory, d))]
    labels = []
    images = []
    i = 0
    directories.sort()
    print(directories)
    while i < SAMPLE_TYPES:
        d = directories[i]
        ct = 0
        label_directory = os.path.join(data_directory, d)
        file_names = [os.path.join(label_directory, f)
                      for f in os.listdir(label_directory)
                      if f.endswith(".jpg")]
        LEN = len(file_names)
        random.shuffle(file_names)
        while ct < len(file_names):
            f = file_names[ct]
            try:
                img = Image.open(f)
                img.verify()
                img = skimage.data.imread(f)
                img = transform.resize(img,(224,224,3))
                images.append(img)
                prob_list = np.zeros(SAMPLE_TYPES)
                prob_list[i]=1
                labels.append(prob_list)
            except (IOError, SyntaxError) as e:
                print("Bad file ", f)
            ct = ct + 1
        i = i+1
        print("Finished directory ",i)
    return images, labels

def load_test_data(data_directory):
    directories = [d for d in os.listdir(data_directory)
                   if os.path.isdir(os.path.join(data_directory, d))]
    labels = []
    images = []
    i = 0
    directories.sort()
    print(directories)
    while i < SAMPLE_TYPES:
        d = directories[i]
        ct = 0
        label_directory = os.path.join(data_directory, d)
        file_names = [os.path.join(label_directory, f)
                      for f in os.listdir(label_directory)
                      if f.endswith(".jpg")]
        random.shuffle(file_names)
        while ct < len(file_names):
            f = file_names[ct]
            try:
                img = Image.open(f)
                img.verify()
                img = skimage.data.imread(f)
                img = transform.resize(img,(224,224,3))
                images.append(img)
                prob_list = np.zeros(SAMPLE_TYPES)
                prob_list[i] = 1
                labels.append(prob_list)
            except (IOError,SyntaxError) as e:
                print("Bad file ",f)
            ct = ct + 1
        i = i+1
        print("Finished directory ",i)
    return images, labels

train_imgs,train_labels = load_data(TRAIN_DATA_PATH)
validation_imgs,validation_labels = load_test_data(TEST_DATA_PATH)
train_imgs = [transform.resize(image, (224,224,3)) for image in train_imgs]
validation_imgs = [transform.resize(image, (224,224,3)) for image in validation_imgs]
train_imgs=np.array(train_imgs)
validation_imgs=np.array(validation_imgs)
train_imgs_scaled = train_imgs.astype('float32')
validation_imgs_scaled  = validation_imgs.astype('float32')

train_labels_enc = np.array(train_labels)
validation_labels_enc = np.array(validation_labels)

input_shape = (224,224,3)

vgg = vgg16.VGG16(include_top=False, weights='imagenet',
                  input_shape=input_shape)

output = vgg.layers[-1].output
output = keras.layers.Flatten()(output)
vgg_model = Model(vgg.input, output)

vgg_model.trainable = False
for layer in vgg_model.layers:
    layer.trainable = False


def get_bottleneck_features(model, input_imgs):
    features = model.predict(input_imgs, verbose=0)
    return features


train_features_vgg = get_bottleneck_features(vgg_model, train_imgs_scaled)
validation_features_vgg = get_bottleneck_features(vgg_model, validation_imgs_scaled)

print('Train Bottleneck Features:', train_features_vgg.shape,
      '\tValidation Bottleneck Features:', validation_features_vgg.shape)


input_shape = vgg_model.output_shape[1]

model = Sequential()
model.add(InputLayer(input_shape=(input_shape,)))
model.add(Dense(512, activation='relu', input_dim=input_shape))
model.add(Dropout(0.3))
model.add(Dense(512, activation='relu'))
model.add(Dropout(0.3))
model.add(Dense(SAMPLE_TYPES,activation='softmax'))

sgd = keras.optimizers.SGD(lr=0.001)

model.compile(loss='categorical_crossentropy',
              optimizer=sgd,
              metrics=['accuracy'])

model.summary()

# model.load_weights("test.h5")

print(train_labels_enc[0:10],train_labels_enc[500:510])
print(validation_labels_enc[0:10])


history = model.fit(x=train_features_vgg, y=train_labels_enc,
                    validation_data=(validation_features_vgg, validation_labels_enc),
                    batch_size=batch_size,
                    epochs=epochs,
                    verbose=1)

model.save_weights('test.h5')
