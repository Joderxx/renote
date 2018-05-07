from renote.readfile import ReadFile
import renote.algorithm
from renote.cutfile import CutFile
import numpy as np
sql = 'select n.note_id,n.note_name,nc.content,nc.tag from note as n,note_content as nc WHERE n.nc_id = nc.nc_id AND nc.shared = 1 '
cut = CutFile()
read = ReadFile()
ids, filenames, texts, tags = read.load_data(sql+" and tag !=''")
tags = np.array(tags)
a = texts
# t = []
# for text in texts:
#     text = cut.cut(text,True)
#     t.append(' '.join(text))
# texts = t
# a = renote.algorithm.Algorithm()
# print(a.classify_score(texts,tags))

t = []
for text in texts:
    text = cut.tf_idf(text)
    t.append(' '.join(text))
texts = t

from sklearn.neighbors import NearestNeighbors
from sklearn.feature_extraction.text import HashingVectorizer
hash = HashingVectorizer(n_features=renote.topn)
texts = hash.transform(texts).toarray()
from sklearn.preprocessing import LabelEncoder
le = LabelEncoder()
tags = le.fit_transform(tags)
nn = NearestNeighbors()
nn.fit(texts)
# pred = nn.kneighbors([texts[0]],n_neighbors=5)[1][::-1]
# print(tags[0])
# print(pred[0])
# print(tags[pred[0].tolist()])

from sklearn.model_selection import train_test_split
X_train,X_test ,y_train,y_test = train_test_split(texts,tags,train_size=.8)

# from sklearn.naive_bayes import  MultinomialNB
# mb = MultinomialNB()
# mb.fit(X_train,y_train)
# print(mb.score(X_test,y_test))

from sklearn.neighbors import KNeighborsClassifier
knn = KNeighborsClassifier()
knn.fit(X_train,y_train)
print(knn.score(X_train,y_train))

from sklearn.ensemble import RandomForestClassifier
rfc = RandomForestClassifier()
rfc.fit(X_train,y_train)
print(rfc.score(X_test,y_test))

s = 'python 怎么学习'
c = cut.tf_idf(s)
t = hash.transform([' '.join(c)]).toarray()

pred = nn.kneighbors(t,n_neighbors=5)
pred = pred[1][::-1][0].tolist()
print(pred)
print(le.inverse_transform(tags[pred]))
print(le.inverse_transform(knn.predict(t)))
print(np.array(a)[pred])


