import pymysql
sql = 'select * from my.article'
conn = pymysql.Connect(host='192.168.56.101',port=3306,user='root',password='root',charset='utf8')
cursor = conn.cursor(cursor=pymysql.cursors.DictCursor)
cursor1 = conn.cursor(cursor=pymysql.cursors.DictCursor)


cursor.execute(sql)
rows = cursor.fetchall()
texts = []
titles = []
tags = []

for item in rows:
    text = item['content']
    title = item['title']
    tags.append('金融')
    texts.append(text)
    titles.append(title)
    # print(title,'\t',text)


sql1 = "insert ignore into renote.note_content(content, tag, creater_id, shared, `type`) VALUES (%s,'金融',8,1,'text')"
sql2 = "insert ignore into renote.note(nbook_id, nc_id, `delete`, note_name, gmt_create, gmt_modified)" \
       " VALUES (13,%s,0,%s,now(),now())"


for text,title in zip(texts,titles):
    cursor.execute(sql1,(text))
    id = cursor.lastrowid
    cursor1.execute(sql2,(id,title))
    conn.commit()

conn.close()