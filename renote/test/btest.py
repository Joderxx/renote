import re
import pymysql
from renote.cutfile import CutFile
import numpy as np
import renote

sql = 'select * from articles'
conn = pymysql.Connect(host='192.168.56.101',port=3306,user='root',password='root',db='my',charset='utf8')
cursor = conn.cursor(cursor=pymysql.cursors.DictCursor)
cursor.execute(sql)
rows = cursor.fetchall()
texts = []
dr = re.compile(r'<[^>]+>', re.S)
cut = CutFile()
for item in rows:
    text = dr.sub('', item['html'])
    title = dr.sub('', item['title'])
    text = title+' '+text
    text = text.lower()
    text = cut.cut(text,False)
    texts.append(text.split())

minRate = 0.003
maxRate = 0.90
texts = np.array(texts)
minTime = minRate * texts.shape[0]
maxTime = maxRate * texts.shape[0]
di = {}
s = set()
for text in texts:
    s.clear()
    for t in text:
        if t not in s:
            if t in di.keys():
                di[t] = di[t] + 1
            else:
                di[t] = 1
        s.add(t)
n_di = {}
for key in di.keys():
    if minTime < di[key]<maxTime :
        n_di[key] = di[key]
li = sorted(n_di.items(), key=lambda x: x[1])
w = []
l = []
ls = [0]
i=1
for key in li:
    w.append(key[0])
    l.append(key[1])
    if(3*i>key[1]):
        ls[-1]+=1
    else:
        ls.append(1)
        i+=1
print(texts.shape)
# print(' '.join(w))
print(len(w))
print(ls)
print(len(ls))
f = open(renote.path+'words.txt','w',encoding='utf-8')
f.write(' '.join(w))