import numpy as np
import tensorflow as tf
from PIL import Image
tf.compat.v1.logging.set_verbosity(tf.compat.v1.logging.INFO)
import json
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
import requests
from skimage.color import rgb2gray
from skimage import transform
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from sklearn.cluster import KMeans
import base64
import cv2
import io

INPUT_DROPOUT = 0.8
CONV1_DROPOUT = 0.9
CONV2_DROPOUT = 0.9
BATCH_SIZE = 16
SAMPLE_TYPES = 25
LEARNING_RATE = 0.001
MODEL_PATH = "/home/centos/TF/BestM/multi" #保存模型的path
UNITS = SAMPLE_TYPES # 此数据应该与参与训练的种类数量相同

label_converters=['菠萝咕老肉', '炒冬瓜', '炒海带', '炒花菜', '炒豇豆',
                  '炒芹菜', '炒青菜', '炒玉米', '蛋饺', '东坡肉', '番茄炒蛋',
                  '贡丸汤', '红烧大排', '酒酿丸子', '凉拌黄瓜', '卤鸡腿', '米饭',
                  '酸汤肥牛', '糖醋里脊', '汤圆', '土豆丝', '炸鸡块',
                  '炸小鸡腿', '蒸鸡蛋', '煮鸡蛋','苑齐超']

def vgg16(features, labels, mode):
    # RGB pic 224*224
    input_layer = tf.reshape(features["x"], [-1, 224, 224, 3])
    # Conv1 first convolutional layer
    conv1 = tf.layers.conv2d(
        inputs=input_layer,
        filters=64,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    # Output a tensor of size 224*224*64
    conv1 = tf.dtypes.cast(conv1,dtype=tf.float32)
    conv2 = tf.layers.conv2d(
        inputs=conv1,
        filters=64,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    # First max pooling
    pool1 = tf.layers.max_pooling2d(inputs=conv2, pool_size=[2, 2], strides=2)
    # result tensor size 112*112*64
    conv3 = tf.layers.conv2d(
        inputs=pool1,
        filters=128,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    conv4 = tf.layers.conv2d(
        inputs=conv3,
        filters=128,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    pool2 = tf.layers.max_pooling2d(inputs=conv4, pool_size=[2, 2], strides=2)
    # output 56*56*128
    conv5 = tf.layers.conv2d(
        inputs=pool2,
        filters=256,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    conv6 = tf.layers.conv2d(
        inputs=conv5,
        filters=256,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    conv7 = tf.layers.conv2d(
        inputs=conv6,
        filters=256,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    # 56*56*256
    pool3 = tf.layers.max_pooling2d(inputs=conv7, pool_size=[2, 2], strides=2)
    # 28*28*256
    conv8 = tf.layers.conv2d(
        inputs=pool3,
        filters=512,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    conv9 = tf.layers.conv2d(
        inputs=conv8,
        filters=512,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    conv10 = tf.layers.conv2d(
        inputs=conv9,
        filters=512,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    # 28*28*512
    pool4 = tf.layers.max_pooling2d(inputs=conv10, pool_size=[2, 2], strides=2)
    # 14*14*512
    conv11 = tf.layers.conv2d(
        inputs=pool4,
        filters=512,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    conv12 = tf.layers.conv2d(
        inputs=conv11,
        filters=512,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    conv13 = tf.layers.conv2d(
        inputs=conv12,
        filters=512,
        kernel_size=[3, 3],
        padding="same",
        activation=tf.nn.relu)
    pool5 = tf.layers.max_pooling2d(inputs=conv13, pool_size=[2, 2], strides=2)
    # 7*7*512

    pool_flat = tf.reshape(pool5, [-1, 7 * 7 * 512])
    dense = tf.layers.dense(inputs=pool_flat, units=4096, activation=tf.nn.relu)
    dropout = tf.layers.dropout(
        inputs=dense, rate=0.5, training=mode == tf.estimator.ModeKeys.TRAIN)
    dense2 = tf.layers.dense(inputs=dropout, units=4096, activation=tf.nn.relu)
    dropout2 = tf.layers.dropout(
        inputs=dense2, rate=0.5, training=mode == tf.estimator.ModeKeys.TRAIN)
    logits = tf.layers.dense(inputs=dropout2, units=UNITS)

    predictions = {
        # Generate predictions
        "classes": tf.argmax(input=logits, axis=1),
        "probabilities": tf.nn.softmax(logits, name="softmax_tensor")}
    if mode == tf.estimator.ModeKeys.PREDICT:
        return tf.estimator.EstimatorSpec(mode=mode, predictions=predictions)


food_classifier = tf.estimator.Estimator(
        model_fn=vgg16, model_dir=MODEL_PATH)


def model_predict(images):
    images = [transform.resize(image,(224,224,3)) for image in images]
    images = np.array(images)
    predict_input_fn = tf.estimator.inputs.numpy_input_fn(
        x={"x": images},
        shuffle=False)
    res = food_classifier.predict(predict_input_fn)
    res = list(res)
    predictions = list()
    for prediction in res:
        predictions.append(Predict(int(prediction['classes']),float(prediction['probabilities'][int(prediction['classes'])])))
    return predictions



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

    def overlap(self, bbox2, wide_space=False):
        C_O = 0
        if wide_space:
            C_O = -10
        else:
            C_O = -10
        w = min(self.w, bbox2.w)
        h = min(self.h, bbox2.h)
        if bbox2.x + w / C_O > self.x + self.w or \
                bbox2.x + bbox2.w - w / C_O < self.x or \
                bbox2.y + h / C_O > self.y + self.h or \
                bbox2.y + bbox2.h - h / C_O < self.y:
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


def Predict2Json(p):
    return {
        "class":label_converters[p.label],
        "probability":p.probability
    }

def All2Json(p):
    if hasattr(p,"label"):
        return {
            "class": label_converters[p.label],
            "probability": p.probability
        }
    else:
        return {
            "x":p.x,
            "y":p.y,
            "w":p.w,
            "h":p.h
        }

def is_object(img,b_width,b_height,x,y,W,H,Bbox_list,do_repel=True,wide_space=False):
    Del_list = list()
    tested = img[y:y+b_height,x:x+b_width]
    tested = tested.reshape(tested.shape[0]*tested.shape[1])

    if np.std(tested) > 1.2:
        return False
    else:
        sign = True
        tmp_bbox = Bbox(x, y, b_width, b_height, np.std(tested))
        for bbox in Bbox_list:
            if bbox.overlap(tmp_bbox,wide_space):
                if (tmp_bbox.precision < bbox.precision and tmp_bbox.w == bbox.w
                and tmp_bbox.h == bbox.h) or (do_repel and tmp_bbox.precision < bbox.precision - 0.5):
                    Del_list.append(bbox)
                else:
                    sign = False
                    break
        if sign:
            Bbox_list.append(tmp_bbox)
            for del_obj in Del_list:
                Bbox_list.remove(del_obj)
            return True
    return False


def apply_bbox(bbox_w,bbox_h,img,W,H,Bbox_list,do_repel=True,wide_space=False):
    BOUND = 50
    bbox_x = int(W / BOUND)
    bbox_y = int(H / BOUND)
    step_x = int(W / 25)
    step_y = int(H / 25)
    while bbox_y + bbox_h < H - H/BOUND:
        while bbox_x + bbox_w < W - W/BOUND:
            is_object(img, bbox_w, bbox_h, bbox_x, bbox_y, W, H, Bbox_list, do_repel = do_repel,wide_space=wide_space)
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
        apply_bbox(int(W * 0.5), int(H * 0.5), g_cluster_pic, W, H, Bbox_list)
        apply_bbox(int(W * 0.25), int(H * 0.5), g_cluster_pic, W, H, Bbox_list)
        apply_bbox(int(W * 0.3), int(H * 0.32), g_cluster_pic, W, H, Bbox_list, do_repel=False, wide_space=True)
        apply_bbox(int(W * 0.26), int(H * 0.26), g_cluster_pic, W, H, Bbox_list, do_repel=False, wide_space=True)
        apply_bbox(int(W * 0.22), int(H * 0.22), g_cluster_pic, W, H, Bbox_list, do_repel=False, wide_space=True)
    elif plate_type == 2:
        apply_bbox(int(W * 0.75), int(H * 0.45), g_cluster_pic,W,H,Bbox_list)
        apply_bbox(int(W * 0.33), int(H * 0.33), g_cluster_pic,W,H,Bbox_list)
        apply_bbox(int(W * 0.25), int(H * 0.25), g_cluster_pic,W,H,Bbox_list)
    show_boxed_img(pic,Bbox_list)
    sub_images = list()
    for bbox in Bbox_list:
        sub_images.append(pic[bbox.y:bbox.y+bbox.h,bbox.x:bbox.x+bbox.w])
    Pred_list = model_predict(sub_images)
    # Pred_list = json.dumps(Pred_list,default=Predict2Json,ensure_ascii=False)
    # Bbox_list = json.dumps(Bbox_list,default=Bbox2Json)
    res =  {
        "predictions":Pred_list,
        "boxes":Bbox_list
    }
    return res


@csrf_exempt
def index(request):
    if request.method == 'POST' and request.POST.get('img') is not None and request.POST.get('tel') is not None:
        # object type confirmation
        obj_type = 0
        url = 'http://202.120.40.8:30231/user/recoTest/'
        url_2 = "http://202.120.40.8:30231/Reco/saveReco"
        tel = request.POST.get("tel")
        if request.POST.get('obj_type') is not None:
            obj_type = int(request.POST.get('obj_type'))
        if obj_type == 0:
            obj_type = 1   # single object
        if obj_type != 1 and obj_type != 2:
            return HttpResponse("obj_type must be 1 (single object) or 2 (multiple object)")
        # security authorization
        token = str(request.META['HTTP_TOKEN'])
        headers = {'token':str(token)}
        response = requests.post(url,headers,headers=headers)
        response = str(response.content)
        if response!="b'get!'":
            print(response)
            return HttpResponse("FAILED IN TOKEN AUTHORIZATIAN")
        #deal with pic
        base64_string = request.POST.get('img')
        imgdata = base64.b64decode(str(base64_string))
        img = Image.open(io.BytesIO(imgdata))
        img = np.array(img)
        if obj_type == 1:
            # obj_type 1
            images = list()
            images.append(img)
            res = model_predict(images)
            if res[0].probability <= 0.5:
                res[0] = Predict(25,0.0)
            Pred_list = json.dumps(res,default=Predict2Json,ensure_ascii=False)
            requests.post(url_2, json.dumps({'tel':tel,'predictions':Pred_list}),
                          headers={'Content-Type': 'application/json'})
            return HttpResponse(json.dumps(res, default=All2Json))
        elif obj_type == 2:
            # multiple object
            plate_type = 0
            if request.POST.get("plate_type") is not None:
                plate_type = int(request.POST.get('plate_type'))
            else:
                plate_type = 1
            if plate_type !=1 and plate_type != 2:
                return HttpResponse("plate_type must be 1 (square plate) or 2 (round plate)")
            res=region_proposal(plate_type,img)
            # also send result to YQC
            requests.post(url_2, json.dumps({'tel':tel,'predictions':res['predictions']},default=All2Json),headers={'Content-Type':'application/json'})
            return HttpResponse(json.dumps({'predictions':res['predictions'],'boxes':res['boxes']},default=All2Json))
    return HttpResponse("INVALID REQUEST.")





