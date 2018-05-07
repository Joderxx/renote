from sklearn.preprocessing import LabelEncoder
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.neighbors import NearestNeighbors
from sklearn.externals import joblib
import numpy as np
import renote

class Algorithm:
    def __init__(self,path=renote.path):
        self.labelEncoder =LabelEncoder()
        self.multinomialNB = MultinomialNB()
        self.nearestNeighbors = NearestNeighbors()
        self.countVectorizer = CountVectorizer()
        self.path = path

    def fit_countVectorizer(self,texts):
        self.countVectorizer.fit(texts)
        joblib.dump(self.countVectorizer,self.path + 'countVectorizer.model')

    def fit_classification(self,texts,tag):
        self.labelEncoder.fit(tag)
        tag = self.labelEncoder.transform(tag)
        texts = self.countVectorizer.transform(texts).toarray()
        self.multinomialNB.fit(texts, tag)
        joblib.dump(self.labelEncoder,self.path+'tag.model')
        joblib.dump(self.multinomialNB,self.path + 'multinomialNB.model')


    def fit_neighbor(self,texts,ids):
        texts = self.countVectorizer.transform(texts).toarray()
        self.nearestNeighbors.fit(texts)
        joblib.dump(self.nearestNeighbors,self.path+'neighbor.model')
        np.save(self.path+'id.npy',np.asarray(ids))

    def classification(self,text):
        self.multinomialNB = joblib.load(self.path+'multinomialNB.model')
        self.countVectorizer = joblib.load(self.path + 'countVectorizer.model')
        self.labelEncoder = joblib.load(self.path + 'tag.model')
        text = self.countVectorizer.transform(text).toarray()
        y = self.multinomialNB.predict(text)
        tag = self.labelEncoder.inverse_transform(y)[0]
        return tag

    def neighbor(self,text,size = 5):
        ids = np.load(self.path+'id.npy')
        self.countVectorizer = joblib.load(self.path + 'countVectorizer.model')
        self.nearestNeighbors = joblib.load(self.path+'neighbor.model')
        text = self.countVectorizer.transform(text).toarray()
        ls = self.nearestNeighbors.kneighbors(text,size)[1]
        id = ids[ls]
        id = id.tolist()[0]
        return ' '.join(str(v) for v in id)

    def classify_score(self,texts,tags):
        self.labelEncoder = joblib.load(self.path + 'tag.model')
        self.multinomialNB = joblib.load(self.path + 'multinomialNB.model')
        self.countVectorizer = joblib.load(self.path + 'countVectorizer.model')
        texts = self.countVectorizer.transform(texts).toarray()
        tags = self.labelEncoder.transform(tags)
        return self.multinomialNB.score(texts,tags)



