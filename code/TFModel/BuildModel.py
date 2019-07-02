import os
import numpy as np
import tensorflow as tf
import skimage
from skimage import transform
import matplotlib.pyplot as plt
import random
import piexif

SAMPLES_PER_TYPE = 100
NUM_OUTPUTS = 101
LEARNING_RATE = 0.001
MODEL_PATH = "Model/model.ckpt"
ALL = 100


def load_data(data_directory):
    directories = [d for d in os.listdir(data_directory)
                   if os.path.isdir(os.path.join(data_directory, d))]
    label_converters = []
    labels = []
    images = []
    i = 0
    for d in directories:
        ct = 0
        label_converters.append(d)
        label_directory = os.path.join(data_directory, d)
        file_names = [os.path.join(label_directory, f)
                      for f in os.listdir(label_directory)
                      if f.endswith(".jpg")]
        for f in file_names:
            piexif.remove(f)
            images.append(skimage.data.imread(f))
            labels.append(i)
            ct = ct + 1
            if ct > SAMPLES_PER_TYPE:
                break
        i = i+1
        print("Finished directory ",i)
    return images, labels, label_converters

ROOT_PATH = "C:\\Food-101\\data\\food-101\\images\\"
train_data_directory = os.path.join(ROOT_PATH, "SmallTraining")
test_data_directory = os.path.join(ROOT_PATH, "Testing")

images, labels, label_converters = load_data(train_data_directory)


print("Load Successfully.")

images128 = [transform.resize(image, (128,128)) for image in images]

images128 = np.array(images128)

print("Begin TF...")

x = tf.placeholder(dtype = tf.float32, shape = [None, 128, 128,3])
y = tf.placeholder(dtype = tf.int32, shape = [None])

# Flatten the input data
images_flat = tf.contrib.layers.flatten(x)

# Fully connected layer
logits = tf.contrib.layers.fully_connected(images_flat, NUM_OUTPUTS, tf.nn.relu)

# Define a loss function
loss = tf.reduce_mean(tf.nn.sparse_softmax_cross_entropy_with_logits(labels = y,
                                                                    logits = logits))
# Define an optimizer
train_op = tf.train.AdamOptimizer(learning_rate=LEARNING_RATE).minimize(loss)

# Convert logits to label indexes
correct_pred = tf.argmax(logits, 1)

# Define an accuracy metric
accuracy = tf.reduce_mean(tf.cast(correct_pred, tf.float32))


tf.set_random_seed(1234)
sess = tf.Session()

sess.run(tf.global_variables_initializer())

for i in range(201):
        print('EPOCH', i)
        _, accuracy_val = sess.run([train_op, accuracy], feed_dict={x: images128, y: labels})
        if i % 10 == 0:
            print("Loss: ", loss)
        print('DONE WITH EPOCH')

saver = tf.train.Saver()
saver.save(sess,MODEL_PATH)

# Pick 10 random images
i = 0
sample_indexes = []
while i < ALL:
    sample_indexes.append(random.randint(0,len(images128)))
    i = i+1

sample_images = [images128[i] for i in sample_indexes]
sample_labels = [labels[i] for i in sample_indexes]

# Run the "correct_pred" operation
predicted = sess.run([correct_pred], feed_dict={x: sample_images})[0]

# Print the real and predicted labels
print(sample_labels)
print(predicted)

i = 0
correct = 0
while i < len(sample_labels):
    if sample_labels[i] == predicted[i]:
        correct = correct+1
    i = i+1

print("Precision: ",correct/float(ALL))