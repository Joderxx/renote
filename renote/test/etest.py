import re
import pymysql
# from renote.cutfile import CutFile
import numpy as np

sql = 'select * from my.articles'
conn = pymysql.Connect(host='192.168.56.101',port=3306,user='root',password='root',charset='utf8')
cursor = conn.cursor(cursor=pymysql.cursors.DictCursor)
cursor1 = conn.cursor(cursor=pymysql.cursors.DictCursor)
cursor.execute(sql)

rows = cursor.fetchall()
texts = []
titles = []
tags = []
# dr = re.compile(r'<[^>]+>', re.S)
# cut = CutFile()
for item in rows:
    text = item['html']
    title = item['title']
    if(len(title)>190):title = title[:190]+'...'
    tag = item['category']
    # text = (title+' ')*30+text
    # text = text.lower()
    # text = cut.cut(text,True)
    texts.append(text)
    titles.append(title)
    tags.append(tag)

sql1 = "insert into renote.note_content(content, tag, creater_id, shared, `type`) VALUES (%s,%s,8,1,'text')"
sql2 = "insert into renote.note(nbook_id, nc_id, `delete`, note_name, gmt_create, gmt_modified)" \
       " VALUES (13,%s,0,%s,now(),now())"

for text,title,tag in zip(texts,titles,tags):
    cursor.execute(sql1,(text,tag))
    id = cursor.lastrowid
    cursor1.execute(sql2,(id,title))
    conn.commit()

conn.close()