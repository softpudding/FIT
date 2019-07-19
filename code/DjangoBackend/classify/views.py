import numpy as np
import tensorflow as tf
from skimage import transform
from PIL import Image
tf.compat.v1.logging.set_verbosity(tf.compat.v1.logging.INFO)
import json
from django.http import HttpResponse
from django.core.files.storage import FileSystemStorage
from django.views.decorators.csrf import csrf_exempt
import requests
from skimage.color import rgb2gray
from skimage import transform
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from scipy import ndimage
from sklearn.cluster import KMeans
import cv2

INPUT_DROPOUT = 0.8
CONV1_DROPOUT = 0.9
CONV2_DROPOUT = 0.9
BATCH_SIZE = 16
SAMPLE_TYPES = 18
LEARNING_RATE = 0.001
MODEL_PATH = "/home/centos/TF/BestM" #保存模型的path
UNITS = SAMPLE_TYPES # 此数据应该与参与训练的种类数量相同

label_converters=["菠萝咕老肉","蛋饺","番茄炒蛋","咖喱鸡块","贡丸汤",
                  "黄焖鸡米饭","烤鸭饭","麻辣香锅","米饭","面","青菜",
                  "生煎","酸汤肥牛","糖醋里脊","土豆丝","油条","炸酱面","煮鸡蛋"]


def cnn_model_fn(features, labels, mode):
    # RGB pic since color does matter for a food
    input_layer = tf.reshape(features["x"], [-1, 128, 128, 3])
    dropout1 = tf.nn.dropout(input_layer,keep_prob=INPUT_DROPOUT)
    print("Input layer defined...")
    conv1 = tf.layers.conv2d(
        inputs=dropout1,
        filters=4,
        kernel_size=[5, 5],
        padding="same",
        activation=tf.nn.relu)
    print("Conv1 layer defined...")
    # 128 128 4
    conv1 = tf.dtypes.cast(conv1,dtype=tf.float32)
    dropout2 = tf.nn.dropout(conv1,keep_prob=CONV1_DROPOUT)
    pool1 = tf.layers.max_pooling2d(inputs=dropout2, pool_size=[2, 2], strides=2)
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
    dropout3 = tf.nn.dropout(conv2,keep_prob=CONV2_DROPOUT)
    pool2 = tf.layers.max_pooling2d(inputs=dropout3, pool_size=[2, 2], strides=2)
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


food_classifier = tf.estimator.Estimator(
        model_fn=cnn_model_fn, model_dir=MODEL_PATH)


def model_predict(img):
    img = transform.resize(img, (128, 128, 3))
    predict_input_fn = tf.estimator.inputs.numpy_input_fn(
        x={"x": img},
        shuffle=False)
    res = food_classifier.predict(predict_input_fn)
    res = list(res)
    return Predict(int(res[0]['classes']),float(res[0]['probabilities'][int(res[0]['classes'])]))



def show_boxed_img(img,Bbox_list):
    plt.imshow(img)
    currentAxis = plt.gca()
    for bbox in Bbox_list:
        # print("PRE:",bbox.precision)
        rect = patches.Rectangle((bbox.x, bbox.y), bbox.w, bbox.h, linewidth=1, edgecolor='r', facecolor='none')
        currentAxis.add_patch(rect)
    plt.show()


# class for a bounding box (x,y) stands for the upper left corner
class Bbox:
    x = 0
    y = 0
    w = 0
    h = 0
    precision = 0.0

    def __init__(self,x,y,w,h,pre):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.precision = pre

    """Over lap suuggerst you to use larger box to call smaller box as bbox2"""
    def overlap(self,bbox2):
        C_O = 5
        w = max(self.w,bbox2.w)
        h = max(self.h,bbox2.h)
        if bbox2.x + w/C_O > self.x + self.w or\
           bbox2.x + bbox2.w - w/C_O < self.x or\
           bbox2.y + h/C_O > self.y + self.h or\
           bbox2.y + bbox2.h - h/C_O < self.y:
            return False
        else:
            return True


def Bbox2Json(bbox):
    return {
        "x":bbox.x,
        "y":bbox.y,
        "w":bbox.w,
        "h":bbox.h
    }


# class for a prediction
class Predict:
    label: -1
    probability: 0

    def __init__(self,label,probability):
        self.label = label
        self.probability = probability

    def __str__(self):
        return "{class:"+label_converters[self.label]+",probability:"+str(self.probability)+"}"

def Predict2Json(p):
    return {
        "class":label_converters[p.label],
        "probability":p.probability
    }


def is_object(img,b_width,b_height,x,y,Bbox_list):
    Del_list=list()
    EFFE_PTG = 0.4
    tested = img[y:y+b_height,x:x+b_width]
    tested = tested.reshape(tested.shape[0]*tested.shape[1])

    if np.std(tested) > 2.0:
        return False

    sum = b_width*b_height
    tmp_dict = dict()
    for i in tested:
        if i not in tmp_dict:
            tmp_dict.update({i:1})
        else:
            tmp_dict[i] = tmp_dict[i] + 1
    for key in tmp_dict:
        if tmp_dict[key] >= int(sum*EFFE_PTG):
            sign = True
            tmp_bbox = Bbox(x,y,b_width,b_height,np.std(tested))
            for bbox in Bbox_list:
                if bbox.overlap(tmp_bbox):
                    if (tmp_bbox.precision < bbox.precision and tmp_bbox.w == bbox.w
                            and tmp_bbox.h == bbox.h) or tmp_bbox.precision < bbox.precision-0.5:
                        Del_list.append(bbox)
                    else:
                        sign = False
                else:
                    continue
            if sign:
                Bbox_list.append(tmp_bbox)
                for del_obj in Del_list:
                    Bbox_list.remove(del_obj)
                return True
    return False


def apply_bbox(bbox_w,bbox_h,img,W,H,Bbox_list):
    BOUND = 50
    bbox_x = int(W / BOUND)
    bbox_y = int(H / BOUND)
    step_x = int(W / 10)
    step_y = int(H / 10)
    while bbox_y + bbox_h < H - H/BOUND:
        while bbox_x + bbox_w < W - W/BOUND:
            is_object(img, bbox_w, bbox_h, bbox_x, bbox_y,Bbox_list)
            bbox_x = bbox_x + step_x
        bbox_y = bbox_y + step_y
        bbox_x = int(W / BOUND)

def region_proposal(plate_type,pic):
    H = int(pic.shape[0] / pic.shape[1] * 400)
    W = 400
    Bbox_list = list()
    pic = transform.resize(pic, (H, W, 3))
    # cluster
    pic_n = pic.reshape(pic.shape[0] * pic.shape[1], pic.shape[2])
    kmeans = KMeans(n_clusters=4, random_state=1).fit(pic_n)
    pic2show = kmeans.cluster_centers_[kmeans.labels_]
    cluster_pic = pic2show.reshape(pic.shape[0], pic.shape[1], pic.shape[2])
    # to gray scale
    g_cluster_pic = rgb2gray(cluster_pic)
    # color tuning
    tmp = g_cluster_pic.reshape(H * W)
    k_colors = list()
    for v in tmp:
        if v not in k_colors:
            k_colors.append(v)
    i = 0
    while i < len(tmp):
        j = 0
        while j < len(k_colors):
            if tmp[i] == k_colors[j]:
                tmp[i] = j
                break
            else:
                j = j + 1
        i = i + 1
    g_cluster_pic = tmp.reshape(H, W)
    if plate_type == 1:
        apply_bbox(int(W * 0.6), int(H * 0.5), g_cluster_pic,W,H,Bbox_list)
        apply_bbox(int(W * 0.3), int(H * 0.6), g_cluster_pic,W,H,Bbox_list)
        apply_bbox(int(W * 0.3), int(H * 0.32), g_cluster_pic,W,H,Bbox_list)
    elif plate_type == 2:
        apply_bbox(int(W * 0.75), int(H * 0.45), g_cluster_pic,W,H,Bbox_list)
        apply_bbox(int(W * 0.33), int(H * 0.33), g_cluster_pic,W,H,Bbox_list)
        apply_bbox(int(W * 0.25), int(H * 0.25), g_cluster_pic,W,H,Bbox_list)
    show_boxed_img(pic,Bbox_list)
    Pred_list = list()
    for bbox in Bbox_list:
        Pred_list.append(model_predict(pic[bbox.y:bbox.y+bbox.h,bbox.x:bbox.x+bbox.w]))
    Pred_list = json.dumps(Pred_list,default=Predict2Json,ensure_ascii=False)
    Bbox_list = json.dumps(Bbox_list,default=Bbox2Json)
    res =  {
        "predictions":Pred_list,
        "boxes":Bbox_list
    }
    res = json.dumps(res)
    return res




@csrf_exempt
def index(request):
    if request.method == 'POST' and request.FILES.get('img') is not None and request.POST.get('token') is not None:
        # object type confirmation
        obj_type = 0
        if request.POST.get('obj_type') is not None:
            obj_type = int(request.POST.get('obj_type'))
        if obj_type == 0:
            obj_type = 1   # single object
        if obj_type != 1 and obj_type != 2:
            return HttpResponse("obj_type must be 1 (single object) or 2 (multiple object)")
        # security authorization
        token = str(request.POST.get('token'))
        url = 'http://202.120.40.8:30231/user/recoTest/'
        headers = {'token':str(token)}
        response = requests.post(url,headers,headers=headers)
        response = str(response.content)
        if response!="b'get!'":
            print(response)
            return HttpResponse("FAILED IN TOKEN AUTHORIZATIAN")
        #deal with pic
        if obj_type == 1:
            # obj_type 1
            img = request.FILES['img']
            img = Image.open(img)
            img = np.array(img)
            res = model_predict(img)
            if res.probability <= 0.5:
                return HttpResponse("苑琪超没有见过这种吃的...")
            else:
                return HttpResponse(json.dumps(res,default=Predict2Json))
        elif obj_type == 2:
            # get img
            img = request.FILES['img']
            img = Image.open(img)
            img = np.array(img)
            # multiple object
            plate_type = 0
            if request.POST.get("plate_type") is not None:
                plate_type = int(request.POST.get('plate_type'))
            else:
                plate_type = 1
            if plate_type !=1 and plate_type != 2:
                return HttpResponse("plate_type must be 1 (square plate) or 2 (round plate)")
            return HttpResponse(region_proposal(plate_type,img))
    return HttpResponse("INVALID REQUEST.")




