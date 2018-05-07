import sys
sys.path.append('/usr/develop/pypath')
from renote.readfile import ReadFile
from renote.algorithm import Algorithm
import renote
from renote.cutfile import CutFile
import numpy as np
cut = CutFile()
read = ReadFile()
sql = 'select n.note_id,n.note_name,nc.content,nc.tag from note as n,note_content as nc WHERE n.nc_id = nc.nc_id AND nc.shared = 1 '
ids, filenames, texts, tags = read.load_data(sql)
t = []
tt = texts
for text in texts:
    text = cut.cut(text,True)
    t.append(' '.join(text))
texts = t
algorithm = Algorithm(renote.path)
algorithm.fit_countVectorizer(texts)
algorithm.fit_neighbor(texts,ids)

sql = 'select n.note_id,n.note_name,nc.content,nc.tag from note as n,note_content as nc WHERE n.nc_id = nc.nc_id '
ids, filenames, texts, tags = read.load_data(sql)
t = []
for text in texts:
    text = cut.cut(text,True)
    t.append(' '.join(text))
texts = t
nid = np.load(renote.path+'id.npy')
texts = algorithm.countVectorizer.transform(texts).toarray()
sql1 = 'truncate table neighbor'
read.excuteSQL(sql1)

for id,text in zip(ids,texts):
    pred = algorithm.nearestNeighbors.kneighbors([text],n_neighbors=5)[1][::-1][0]
    n = ' '.join([str(i) for i in nid[pred.tolist()]])
    sql1 = "insert ignore into neighbor(`note_id`,`neighbor`) VALUES(%s,'%s') " % (id, n)
    read.excuteSQL(sql1)

ids, filenames, texts, tags = read.load_data(sql+" and tag !=''")
t = []
for text in texts:
    text = cut.cut(text,True)
    t.append(' '.join(text))
texts = t
algorithm.fit_classification(texts,tags)

############# train_keyword

texts = tt
t = []
for text in texts:
    text = cut.cut(text,True)
    t.append(' '.join(text))
texts = t
sql1 = 'truncate table keyword'
read.excuteSQL(sql1)
for id,text in zip(ids,texts):
    c = cut.tf_idf(text)
    if len(c)==0:continue
    sql1 = "insert ignore into keyword(`note_id`,`keyword`) VALUES(%s,'%s') "%(id,' '.join(c))
    read.excuteSQL(sql1)

