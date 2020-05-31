import numpy as np
import math
import time
from colour import Color
from matplotlib import pyplot as plt
from Adafruit_AMG88xx import Adafruit_AMG88xx
from scipy.interpolate import griddata
import cv2
from time import sleep
import pyrebase
import datetime
from Adafruit_AMG88xx import Adafruit_AMG88xx
from pytz import timezone
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

# Start sensor
sensor = Adafruit_AMG88xx()
active = True

config = {
    "apiKey": "AIzaSyDDAqrOQ66tkSY4dfTfiMwzyDl3CplFP8U",
    "authDomain": "room-occupancy-5b0b1.firebaseapp.com",
    "databaseURL": "https://room-occupancy-5b0b1.firebaseio.com",
    "projectId": "room-occupancy-5b0b1",
    "storageBucket": "room-occupancy-5b0b1.appspot.com",
    "messagingSenderId": "188555956188",
    "appId": "1:188555956188:web:2158f8e8675c9cf1f00919",
    "measurementId": "G-48YEBHVVWK"
    }

firebase = pyrebase.initialize_app(config)
storage = firebase.storage()

path_on_cloud= "images/heat.png"
cred =credentials.Certificate('firebase.json')

firebase_admin.initialize_app(cred,{
'databaseURL': "https://room-occupancy-5b0b1.firebaseio.com/"
})

tem = 0
MINTEMP = 27
MAXTEMP = 32
COLORDEPTH = 1024

points = [(math.floor(ix / 8), (ix % 8)) for ix in range(0, 64)]
grid_x, grid_y = np.mgrid[0:7:512j, 0:7:512j]

def constrain(val, min_val, max_val):
    return min(max_val, max(min_val, val))

def map(x, in_min, in_max, out_min, out_max):
  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min

while 1:
    if active:
        # Read pixels, convert them to values between 0 and 1, map them to an 8x8 grid
        pixels = sensor.readPixels()
        # bicubic interpolation of 8x8 grid to make a 512x512 grid
        pixels = [map(p, MINTEMP, MAXTEMP, 0, COLORDEPTH - 1) for p in pixels]
        bicubic = griddata(points, pixels, (grid_x, grid_y), method='cubic')
        ret,thresh4 = cv2.threshold(bicubic,127,255,cv2.THRESH_TOZERO)
        cv2.imwrite("static/thershold4.png", thresh4)
        image = np.array(bicubic)
        image = np.reshape(image, (512, 512))
        
        plt.imsave('static/heatmap.png', image, cmap = 'rainbow')
        path_local = "static/heatmap.png"
        storage.child(path_on_cloud).put(path_local)
        
        ig = cv2.imread("static/thershold4.png",0)
        ret, thresh2 = cv2.threshold(ig,127,255,cv2.THRESH_TOZERO)
        
        now=datetime.datetime.now(timezone('Asia/Riyadh'))
        year=now.year
        month=now.month
        day=now.strftime("%A")
        hour=now.hour
        mit=now.minute
        result = sensor.readPixels()
        temp = 0
        for i in result:
            temp = temp+i
        temperature = temp / 64
        t=int(temperature)
        print("avrage temp : ",t)
        ret, labels = cv2.connectedComponents(thresh2)
        print('objects number is:', ret-1)
        
        if(ret-1 == 0):
            occupied=1
            print("status is : ",0)
        else:
            occupied=0
            print("status is : ",1)
        
        ref=db.reference('occupancy')
        ref.child('satuts').update({
     	"occupy":ret-1,
        "year":year,
        "month":month,
        "day":day,
        "hour":hour,
        "minute":mit,
        "temp":t
        
     })
        
        sleep(1)

    else:
        print("idle")
        time.sleep(1)