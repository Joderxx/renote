import pymysql
import random

conn = pymysql.Connect(host='192.168.56.101', port=3306, user='root', password='root', charset='utf8')
cur = conn.cursor(cursor=pymysql.cursors.DictCursor)
cur1 = conn.cursor(cursor=pymysql.cursors.DictCursor)

# 随机分配create_id
cur.execute('select n.note_name,content,nc.tag from renote.note_content as nc ,renote.note as n WHERE n.nc_id=nc.nc_id')
data = cur.fetchall()
nc_ids = []

for d in data:
    i = random.randint(22, 30)
    sql1 = "insert into renote1.note_content(content, tag, creater_id, shared, type)" \
           " VALUES ('{0}','{1}',{2},1,'text')".format(pymysql.escape_string(d['content']), pymysql.escape_string(d['tag']), str(i))

    print(sql1)
    cur1.execute(sql1)
    id = cur1.lastrowid
    sql2 = "insert into renote1.note(nbook_id, nc_id, `delete`, note_name, gmt_create, gmt_modified) " \
           "VALUES (%s,%s,0,\"%s\",now(),now())"%(str(i+35),str(id),d['note_name'])
    cur1.execute(sql2)
conn.commit()









