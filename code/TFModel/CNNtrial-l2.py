import os
import numpy as np
import tensorflow as tf
import skimage
from skimage import transform
import random
import piexif
tf.compat.v1.logging.set_verbosity(tf.compat.v1.logging.INFO)


SAMPLES_BEGIN = 300 # 每个种类参与训练的数据开始坐标
SAMPLES_END = 400 # 每个种类参与训练的数据结束坐标
SAMPLE_TYPES = 20 # 参与训练种类
LEARNING_RATE = 0.001
MODEL_PATH = "cnn/food101_convnet_model_4/" #保存模型的path
ALL = 10000 # 测试用的数据量
EPOCHS = None
UNITS = SAMPLE_TYPES # 此数据应该与参与训练的种类数量相同
STEPS = 500

def cnn_model_fn(features, labels, mode):
    # RGB pic since color does matter for a food
    input_layer = tf.reshape(features["x"], [-1, 128, 128, 3])
    print("Input layer defined...")
    conv1 = tf.layers.conv2d(
        inputs=input_layer,
        filters=4,
        kernel_size=[5, 5],
        padding="same",
        activation=tf.nn.relu)
    print("Conv1 layer defined...")
    # 128 128 4
    conv1 = tf.dtypes.cast(conv1,dtype=tf.float32)
    pool1 = tf.layers.max_pooling2d(inputs=conv1, pool_size=[2, 2], strides=2)
    # 64 64 4
    print("Poo1 layer defined...")

    conv2 = tf.layers.conv2d(
        inputs=pool1,
        filters=8,
        kernel_size=[5, 5],
        padding="same",
        activation=tf.nn.relu)
    # 64 64 8
    print("Conv2 layer defined...")
    pool2 = tf.layers.max_pooling2d(inputs=conv2, pool_size=[2, 2], strides=2)
    # 32 32 8
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
        while ct < len(file_names) and ct < SAMPLES_END:
            if ct < SAMPLES_BEGIN:
                ct = ct + 1
                continue
            f = file_names[ct]
            piexif.remove(f)
            images.append(skimage.data.imread(f))
            labels.append(i)
            ct = ct + 1
        i = i+1
        print("Finished directory ",i)
    return images, labels, label_converters

ROOT_PATH = "C:\\Food-101\\data\\food-101\\images\\"
train_data_directory = os.path.join(ROOT_PATH, "Training")

images, labels, label_converters = load_data(train_data_directory)

images128 = [transform.resize(image, (128,128,3)) for image in images]
images128 = np.array(images128)

labels = np.array(labels)
print("Data initialzation finished...")

food_classifier = tf.estimator.Estimator(
    model_fn=cnn_model_fn, model_dir=MODEL_PATH)
tensors_to_log = {"probabilities": "softmax_tensor"}
logging_hook = tf.train.LoggingTensorHook(tensors=tensors_to_log, every_n_iter=50)

"""
train_input_fn = tf.estimator.inputs.numpy_input_fn(
    x={"x": images128},
    y=labels,
    batch_size=4,
    num_epochs=None,
    shuffle=True)
food_classifier.train(
    input_fn=train_input_fn,
    steps=STEPS,
    hooks=[logging_hook])
"""

i = 0
sample_indexes = []
while i < ALL:
    sample_indexes.append(random.randint(0,len(images128)-1))
    i = i+1

sample_images = [images128[i] for i in sample_indexes]
sample_labels = [labels[i] for i in sample_indexes]
sample_images = np.array(sample_images)
sample_labels = np.array(sample_labels)

eval_input_fn = tf.estimator.inputs.numpy_input_fn(
    x={"x": sample_images},
    y=sample_labels,
    num_epochs=1,
    shuffle=False)
eval_results = food_classifier.evaluate(input_fn=eval_input_fn)
print(eval_results)
