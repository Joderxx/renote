import pymysql
import random
import renote
import re

class ReadFile:
    def __init__(self,host=renote.host,port=renote.port,user=renote.username,password=renote.password,db=renote.db,charset=renote.charset):
        self.conn = pymysql.Connect(host=host, port=port, user=user, password=password, db=db, charset=charset)
        self.cursor = self.conn.cursor(cursor=pymysql.cursors.DictCursor)

    def __del__(self):
        self.conn.close()

    def load_data(self,sql = 'select * from text',id='note_id',filename='note_name',content='content',tag='tag'):
        dr = re.compile(r'<[^>]+>', re.S)
        self.cursor.execute(sql)
        ls = self.cursor.fetchall()
        ids = []
        filenames = []
        texts = []
        tags = []
        for item in ls:
            ids.append(item[id])
            filenames.append(item[filename])
            text = item[content]
            text = dr.sub('', text)
            texts.append((item[filename]+' ')*renote.extend+text)
            tags.append(item[tag])
        c = list(zip(ids, filenames, texts, tags))
        random.shuffle(c)
        ## ids, filenames, texts, tags = zip(*c)
        return zip(*c)

    def read_data(self,sql,content='content',title = 'filename'):
        self.cursor.execute(sql)
        item = self.cursor.fetchone()
        if item is None or len(item)==0:return None
        return (item[title]+' ')*renote.extend+item[content]

    def excuteSQL(self,sql):
        self.cursor.execute(sql)
        self.conn.commit()


