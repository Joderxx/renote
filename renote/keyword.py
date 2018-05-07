import sys
sys.path.append('/usr/develop/pypath')
from renote.readfile import ReadFile
from renote.cutfile import CutFile
import argparse
import renote
parser = argparse.ArgumentParser(description='manual to this script')
parser.add_argument('--keyword', type=str)
args = parser.parse_args()
kw = args.keyword

read = ReadFile()
cut = CutFile()
sql = "select note_id from keyword WHERE 1=0 "

kw = cut.tf_idf(kw)
for k in kw:
    sql = sql + 'or keyword like \'%'+k+'%\''

read.cursor.execute(sql)
row = read.cursor.fetchall()
li = []
for r in row[:renote.searchSize]:
    li.append(str(r['note_id']))
print(' '.join(li))
