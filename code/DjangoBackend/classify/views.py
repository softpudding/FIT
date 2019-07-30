from keras.applications import vgg16
from keras.models import Model
import keras
from keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout, InputLayer
from keras.models import Sequential
from keras import optimizers
import numpy as np
from PIL import Image
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
import io
import skimage
# *************** CONSTANT AREA ******************************************* #
multi_types = 22
single_types = 25
multi_lr = 1e-5
single_lr = 1e-5

single_label_converters=['炒河粉', '炒乌冬面', '蛋包饭', '咖喱猪排饭',
                         '汉堡', '黄焖鸡米饭', '馄饨', '酒酿丸子', '烤鸭饭',
                         '卤肉饭', '麻辣香锅', '毛血旺', '奶油蘑菇意面', '披萨',
                         '三杯鸡饭', '上海炒面', '烧饼', '生煎', '酸菜鱼',
                         '汤包', '汤圆', '西红柿鸡蛋面', '鸭血粉丝汤',
                         '扬州炒饭', '炸酱面']
multi_label_converters=['菠萝咕老肉', '炒冬瓜', '炒海带', '炒花菜',
                        '炒豇豆', '炒芹菜', '炒青菜', '炒玉米', '蛋饺',
                        '东坡肉', '番茄炒蛋', '红烧大排', '凉拌黄瓜', '卤鸡腿',
                        '米饭', '酸汤肥牛', '糖醋里脊', '土豆丝', '炸鸡块',
                        '炸小鸡腿', '蒸鸡蛋', '煮鸡蛋']

# ************* DEFINE 2 MODELS *****************************************

input_shape = (224,224,3)
vgg = vgg16.VGG16(include_top=False, weights='imagenet',
                  input_shape=input_shape)

output = vgg.layers[-1].output
output = keras.layers.Flatten()(output)
vgg_model = Model(vgg.input, output)

vgg_model._make_predict_function()

input_shape = vgg_model.output_shape[1]
'''
model_m = Sequential()
model_m.add(vgg_model)
model_m.add(InputLayer(input_shape=(input_shape,)))
model_m.add(Dense(512, activation='relu', input_dim=input_shape))
model_m.add(Dropout(0.3))
model_m.add(Dense(512, activation='relu'))
model_m.add(Dropout(0.3))
model_m.add(Dense(multi_types,activation='softmax'))

sgd = keras.optimizers.SGD(lr=multi_lr)
model_m.compile(loss='categorical_crossentropy',
              optimizer=sgd,
              metrics=['accuracy'])
model_m.load_weights("fit_multi.h5")
'''

model_s = Sequential()
model_s.add(InputLayer(input_shape=(input_shape,)))
model_s.add(Dense(512, activation='relu', input_dim=input_shape))
model_s.add(Dropout(0.3))
model_s.add(Dense(512, activation='relu'))
model_s.add(Dropout(0.3))
model_s.add(Dense(single_types,activation='softmax'))

sgd = keras.optimizers.SGD(lr=single_lr)
model_s.compile(loss='categorical_crossentropy',
              optimizer=sgd,
              metrics=['accuracy'])
model_s.load_weights("fit_single.h5")
model_s._make_predict_function()
# ******************* TWO PREDICT FUNCTIONS **************************

'''
def multi_model_predict(images):
    images = [transform.resize(image,(224,224,3)) for image in images]
    images = np.array(images)
    print(model_m.predict(images))
'''

# ******************** CLASSES *****************************************
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


class Predict:
    label: -1
    probability: 0.0

    def __init__(self,label,probability):
        self.label = label
        self.probability = probability


class Nutri:
    calory: 0.0
    protein: 0.0
    fat: 0.0
    carbohydrate: 0.0
    def __init__(self,cal,pro,fat,car):
        self.calory=cal
        self.protein=pro
        self.fat=fat
        self.carbohydrate=car


nutris_s = [Nutri(175,5.05,6.37,23.76),Nutri(131,4.02,0.35,27.18),Nutri(95,3.59,3.69,11.7),
            Nutri(131,7,6,12),Nutri(290,15.71,11.8,28.83),Nutri(167,14.07,7.81,9.52),
            Nutri(259,10.43,5.9,37.59),Nutri(120,4.02,3.35,27.18),Nutri(265,18.66,20.61,11.7),
            Nutri(187 ,5.92,9.55,18.85),Nutri(99,5.23,4.62,9.73),Nutri(75,6.72,2.55,6.32),
            Nutri(101,3.56,1.58,18.48),Nutri(270,12.07,10.55,31.65),Nutri(186,15.78,11.55,12.79),
            Nutri(527,8.38,30.76,57.54),Nutri(240,8.96,3.04,49.7),Nutri(282,9.97,12.75,34.32),
            Nutri(95,13.43,2.77,3.24),Nutri(196,7.93,6.16,29.1),Nutri(327,6.79,6.75,59.12),
            Nutri(92,4.45,3.73,12.15),Nutri(95,6.5,2.17,12.06),Nutri(164,5.08,7.88,17.76),
            Nutri(168,6.47,7.12,19.32),Nutri(0,0,0,0)]

# ********************* USEFUL FUNCTIONS ********************************

def single_model_predict(images):
    images = [transform.resize(image,(224,224,3)) for image in images]
    images = np.array(images)
    print(images.shape)
    features = get_bottleneck_features(vgg_model,images)
    res = model_s.predict(features)
    i = 0
    type = -1
    max = 0
    while i < single_types:
        if res[0][i] > max:
            type = i
            max = res[0][i]
        i = i+1
    print(single_label_converters[type],max)
    return Predict(type,max)



def show_boxed_img(img,Bbox_list):
    plt.imshow(img)
    currentAxis = plt.gca()
    for bbox in Bbox_list:
        # print("PRE:",bbox.precision)
        rect = patches.Rectangle((bbox.x, bbox.y), bbox.w, bbox.h, linewidth=1, edgecolor='r', facecolor='none')
        currentAxis.add_patch(rect)
    plt.show()


def All2Json_m(p):
    if hasattr(p,"label"):
        return {
            "class": multi_label_converters[p.label],
            "probability": float(p.probability)
        }
    elif hasattr(p,"x"):
        return {
            "x":p.x,
            "y":p.y,
            "w":p.w,
            "h":p.h
        }
    elif hasattr(p,"calory"):
        return {
            "calory": p.calory,
            "protein": p.protein,
            "fat": p.fat,
            "carbohydrate":p.carbohydrate
        }
    else:
        return p


def All2Json_s(p):
    if hasattr(p,"label"):
        return {
            "class": single_label_converters[p.label],
            "probability": float(p.probability)
        }
    elif hasattr(p, "x"):
        return {
            "x": p.x,
            "y": p.y,
            "w": p.w,
            "h": p.h
        }
    elif hasattr(p, "calory"):
        return {
            "calory": p.calory,
            "protein": p.protein,
            "fat": p.fat,
            "carbohydrate": p.carbohydrate
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


def get_bottleneck_features(model, input_imgs):
    features = model.predict(input_imgs, verbose=0)
    return features


'''
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
'''

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
        # img = skimage.data.imread("./1.jpg")
        if obj_type == 1:
            # obj_type 1
            images = [img]
            pred = single_model_predict(images)
            if pred.probability <= 0.5:
                pred = Predict(25,0.0)
            '''
            Pred_list = json.dumps(pred,default=All2Json_s,ensure_ascii=False)
            requests.post(url_2, json.dumps({'tel':tel,'predictions':Pred_list}),
                          headers={'Content-Type': 'application/json'})
            '''
            return HttpResponse(json.dumps({'prediction':pred,'nutri':nutris_s[pred.label]}, default=All2Json_s))
        elif obj_type == 2:
            '''
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
            print(json.dumps({'tel':tel,'predictions':res['predictions']},default=All2Json))
            requests.post(url_2, json.dumps({'tel':tel,'predictions':res['predictions']},default=All2Json),headers={'Content-Type':'application/json'})
            return HttpResponse(json.dumps({'predictions':res['predictions'],'boxes':res['boxes']},default=All2Json))
            '''
            return HttpResponse("Unsupported Request")
    return HttpResponse("INVALID REQUEST.")





