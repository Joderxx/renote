import sys
sys.path.append('/usr/develop/pypath')
from renote.readfile import ReadFile
from renote.algorithm import Algorithm
import renote
from renote.cutfile import  CutFile
import re
import argparse
parser = argparse.ArgumentParser(description='manual to this script')
parser.add_argument('--clazz', type=str, default = renote.classification)
parser.add_argument('--noteId', type=int)
parser.add_argument('--size',type=int,default=5)
args = parser.parse_args()
clazz = args.clazz
noteId = args.noteId
size = args.size

c = CutFile()
read = ReadFile()
algorithm = Algorithm(renote.path)
sql = 'select * from note_content AS nc,note as n WHERE nc.nc_id=n.nc_id AND note_id = '+str(noteId)
text = read.read_data(sql,title='note_name')
dr = re.compile(r'<[^>]+>', re.S)
text = dr.sub('', text)
if clazz == renote.classification:
    if text is None or len(text) == 0:
        print('其它')
    else:
        text = text.lower()
        text = c.cut(text)
        if len(text) == 0:print('其它')
        else:
            # print(text)
            tag = algorithm.classification(text)
            print(tag)
else:
    if text is None or len(text)==0:
        print('')
    else:
        text = text.lower()
        text = c.cut(text)
        if len(text) == 0:
            print('')
        else:
            print(algorithm.neighbor(text, size))
