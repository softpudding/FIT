import tensorflow as tf
import os 

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

message = tf.constant('Welcome to the exciting world')
with tf.compat.v1.Session() as sess:
		print (sess.run(message).decode())