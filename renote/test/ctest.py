import re
import pymysql
from renote.cutfile import CutFile
import numpy as np

sql = 'select * from articles'
conn = pymysql.Connect(host='192.168.56.101',port=3306,user='root',password='root',db='my',charset='utf8')
cursor = conn.cursor(cursor=pymysql.cursors.DictCursor)
cursor.execute(sql)
rows = cursor.fetchall()
texts = []
titles = []
tags = []
dr = re.compile(r'<[^>]+>', re.S)
cut = CutFile()
for item in rows:
    text = dr.sub('', item['url'])
    title = dr.sub('', item['title'])
    tag = dr.sub('', item['content'])
    # text = (title+' ')*30+text
    # text = text.lower()
    # text = cut.cut(text,True)
    texts.append(text)
    titles.append(title)
    tags.append(tag)

# for x,y,z in zip(texts,titles,tags):
#     if len(x)==0:continue
#     sql = 'insert into texts(title, content, tag) VALUES(%s,%s,%s)'
#     cursor.execute(sql,(y,' '.join(x), z))
#     conn.commit()

# sql = "select * from my.texts WHERE content !=''"
# conn = pymysql.Connect(host='192.168.56.101',port=3306,user='root',password='root',db='my',charset='utf8')
# cursor = conn.cursor(cursor=pymysql.cursors.DictCursor)
# cursor.execute(sql)
# rows = cursor.fetchall()
# texts = []
# tags = []
# for item in rows:
#     texts.append(item['content'])
#     tags.append(item['tag'])

from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.preprocessing import LabelEncoder

le = LabelEncoder()
tags = le.fit_transform(tags)
tags = np.asarray(tags)

cv = CountVectorizer()
texts = cv.fit_transform(texts)
texts = texts.toarray()

X_train,X_test,y_train,y_test = train_test_split(texts,tags,train_size=.7)

from sklearn.naive_bayes import MultinomialNB
mb = MultinomialNB()
mb.fit(X_train,y_train)
print(mb.score(X_test,y_test))

from sklearn.naive_bayes import GaussianNB
gb = GaussianNB()
gb.fit(X_train,y_train)
print(gb.score(X_test,y_test))

from sklearn.neighbors import KNeighborsClassifier

knn = KNeighborsClassifier()
knn.fit(X_train,y_train)
print(knn.score(X_test,y_test))

from sklearn.linear_model import SGDClassifier

sgd = SGDClassifier()
sgd.fit(X_train,y_train)
print(sgd.score(X_test,y_test))

X_test = mb.predict(X_test)
for x,y in zip(X_test,y_test):
    # li = np.argsort(x)[::-1]
    # li = li[:3]
    print(le.inverse_transform([y]),'  ',le.inverse_transform([x]))




