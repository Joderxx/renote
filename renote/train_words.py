import sys
sys.path.append('/usr/develop/pypath')
import renote
from renote.readfile import ReadFile
from renote.cutfile import CutFile
import numpy as np

minRate = 0
maxRate = 1.

read = ReadFile()
cutfile = CutFile()
ids, filenames, texts, tags = read.load_data(sql='select note_id,note_name,content,tag from note_content AS nc,note as n WHERE nc.nc_id=n.nc_id ')

texts = np.asarray(texts)
minTime = minRate*texts.shape[0]
maxTime = maxRate*texts.shape[0]
di = {}
s = set()
for text in texts:
    text = text.strip()
    text = cutfile.cut(text,True)
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
    if minTime < di[key] < maxTime:
        n_di[key] = di[key]
li = sorted(n_di.items(), key=lambda x: x[1])
w = []
for key in li:
    # print(key)
    w.append(key[0])

print(len(w))
print(w)

f = open(renote.path+'words.txt', 'w', encoding='utf-8')
f.write(' '.join(w))
f.close()