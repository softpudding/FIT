import os
import random

import numpy as np
import skimage
import tensorflow as tf
from PIL import Image
from skimage import transform

tf.compat.v1.logging.set_verbosity(tf.compat.v1.logging.INFO)

INPUT_DROPOUT = 0.8
CONV1_DROPOUT = 0.9
CONV2_DROPOUT = 0.9
BATCH_SIZE = 4
TEST_SAMPLES = 50
TRAIN_SAMPLES = 256
SAVE_FOR_TEST = 100
SAMPLE_TYPES = 10 # 参与训练种类
LEARNING_RATE = 0.001
MODEL_PATH = "cnn/啊♂/" #保存模型的path
EPOCHS = None
UNITS = SAMPLE_TYPES # 此数据应该与参与训练的种类数量相同
STEPS = (1600-SAVE_FOR_TEST)*SAMPLE_TYPES/BATCH_SIZE
TRAIN_DATA_PATH = "C:/Food-101/data/food-101/images/Training"

def cnn_model_fn(features, labels, mode):
    # RGB pic 128*128
    input_layer = tf.reshape(features["x"], [-1, 128, 128, 3])
    # Input layer dropout currently rate 20%
    dropout1 = tf.nn.dropout(input_layer,keep_prob=INPUT_DROPOUT)
    print("Input layer defined...")
    # Conv1 first convolutional layer
    conv1 = tf.layers.conv2d(
        inputs=dropout1,
        filters=4,
        kernel_size=[5, 5],
        padding="same",
        activation=tf.nn.relu)
    print("Conv1 layer defined...")
    # Output a tensor of size 128*128*4
    conv1 = tf.dtypes.cast(conv1,dtype=tf.float32)
    # Conv1 dropout currently 10%
    dropout2 = tf.nn.dropout(conv1,keep_prob=CONV1_DROPOUT)
    # First max pooling
    pool1 = tf.layers.max_pooling2d(inputs=dropout2, pool_size=[2, 2], strides=2)
    # result tensor size 64*64*4
    print("Poo1 layer defined...")
    # Conv2
    conv2 = tf.layers.conv2d(
        inputs=pool1,
        filters=8,
        kernel_size=[5, 5],
        padding="same",
        activation=tf.nn.relu)
    # output 64*64*8
    print("Conv2 layer defined...")
    # Dropout on conv2 10%
    dropout3 = tf.nn.dropout(conv2,keep_prob=CONV2_DROPOUT)
    # 2nd max pooling
    pool2 = tf.layers.max_pooling2d(inputs=dropout3, pool_size=[2, 2], strides=2)
    # output 32*32*8
    pool2_flat = tf.reshape(pool2, [-1, 32 * 32 * 8])
    print("Pool2 layer defined...")
    dense = tf.layers.dense(inputs=pool2_flat, units=32 * 32 *8, activation=tf.nn.relu)
    print("Dense layer defined...")
    dropout = tf.layers.dropout(
        inputs=dense, rate=0.3, training=mode == tf.estimator.ModeKeys.TRAIN)
    logits = tf.layers.dense(inputs=dropout, units=UNITS)
    predictions = {
        # Generate predictions
        "classes": tf.argmax(input=logits, axis=1),
        "probabilities": tf.nn.softmax(logits, name="softmax_tensor")}
    if mode == tf.estimator.ModeKeys.PREDICT:
        return tf.estimator.EstimatorSpec(mode=mode, predictions=predictions)
    loss = tf.losses.sparse_softmax_cross_entropy(labels=labels, logits=logits)
    if mode == tf.estimator.ModeKeys.TRAIN:
        optimizer = tf.train.GradientDescentOptimizer(learning_rate=LEARNING_RATE)
        train_op = optimizer.minimize(
            loss=loss,
            global_step=tf.train.get_global_step())
        return tf.estimator.EstimatorSpec(mode=mode, loss=loss, train_op=train_op)
    eval_metric_ops = {
        "accuracy": tf.metrics.accuracy(labels=labels, predictions=predictions["classes"])}
    return tf.estimator.EstimatorSpec(mode=mode, loss=loss, eval_metric_ops=eval_metric_ops)

def load_data(data_directory):
    directories = [d for d in os.listdir(data_directory)
                   if os.path.isdir(os.path.join(data_directory, d))]
    label_converters = []
    labels = []
    images = []
    i = 0
    while i < SAMPLE_TYPES:
        d = directories[i]
        ct = 0
        label_converters.append(d)
        label_directory = os.path.join(data_directory, d)
        file_names = [os.path.join(label_directory, f)
                      for f in os.listdir(label_directory)
                      if f.endswith(".jpg")]
        LEN = len(file_names)
        file_names = file_names[0:LEN-SAVE_FOR_TEST-1]
        random.shuffle(file_names)
        while ct < len(file_names) and ct < TRAIN_SAMPLES:
            f = file_names[ct]
            try:
                img = Image.open(f)
                img.verify()
                img = skimage.data.imread(f)
                img = transform.resize(img,(128,128,3))
                images.append(img)
                labels.append(i)
            except (IOError, SyntaxError) as e:
                print("Bad file ", f)
            ct = ct + 1
        i = i+1
        print("Finished directory ",i)
    return images, labels, label_converters

def load_test_data(data_directory):
    directories = [d for d in os.listdir(data_directory)
                   if os.path.isdir(os.path.join(data_directory, d))]
    labels = []
    images = []
    i = 0
    while i < SAMPLE_TYPES:
        d = directories[i]
        ct = 0
        label_directory = os.path.join(data_directory, d)
        file_names = [os.path.join(label_directory, f)
                      for f in os.listdir(label_directory)
                      if f.endswith(".jpg")]
        LEN = len(file_names)
        file_names = file_names[LEN-SAVE_FOR_TEST:]
        random.shuffle(file_names)
        file_names = file_names[0:TEST_SAMPLES]
        while ct < len(file_names):
            f = file_names[ct]
            try:
                img = Image.open(f)
                img.verify()
                img = skimage.data.imread(f)
                img = transform.resize(img,(128,128,3))
                images.append(img)
                labels.append(i)
            except (IOError,SyntaxError) as e:
                print("Bad file ",f)
            ct = ct + 1
        i = i+1
        print("Finished directory ",i)
    return images, labels


images, labels, label_converters = load_data(TRAIN_DATA_PATH)

images128 = [transform.resize(image, (128,128,3)) for image in images]
images128 = np.array(images128 )

labels = np.array(labels)
print("Data initialzation finished...")

food_classifier = tf.estimator.Estimator(
    model_fn=cnn_model_fn, model_dir=MODEL_PATH)
tensors_to_log = {"probabilities": "softmax_tensor"}
logging_hook = tf.train.LoggingTensorHook(tensors=tensors_to_log, every_n_iter=50)


train_input_fn = tf.estimator.inputs.numpy_input_fn(
    x={"x": images128},
    y=labels,
    batch_size=BATCH_SIZE,
    num_epochs=None,
    shuffle=True)
food_classifier.train(
    input_fn=train_input_fn,
    steps=STEPS,
    hooks=[logging_hook])

sample_images,sample_labels = load_test_data(TRAIN_DATA_PATH)
sample_images = [transform.resize(image, (128,128,3)) for image in sample_images]
sample_images = np.array(sample_images)
sample_labels = np.array(sample_labels)

eval_input_fn = tf.estimator.inputs.numpy_input_fn(
    x={"x": sample_images},
    y=sample_labels,
    num_epochs=1,
    shuffle=False)
eval_results = food_classifier.evaluate(input_fn=eval_input_fn)
print(eval_results)
