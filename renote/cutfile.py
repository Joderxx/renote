import jieba
import re
import renote
import jieba.analyse

class CutFile:

    def __init__(self):
        if renote.dictfile is not None:
            jieba.load_userdict(renote.dictfile)
        f = open(renote.wordfile,'r',encoding='utf-8')
        self.words = f.read().lower().split()
        self.words = set(self.words)
        f.close()
        self.stop = []
        if renote.stopfile is not None:
            f = open(renote.stopfile, 'r', encoding='utf-8')
            self.stop = f.read().lower().split('\n')
            self.stop = set(self.stop)
            f.close()

    def cut(self,text,flag=True):
        '''
        :param text: 文本
        :param flag: 是否选择词库单词
        :return:
        '''
        pattern = re.compile(r'\d+')
        ws = jieba.cut(text)
        li = []
        for w in ws:
            if (w not in self.stop ) and (pattern.match(w) is None):
                li.append(w)
        if not flag:
            return li
        else:
            l = []
            for item in li:
                if item in self.words:
                    l.append(item)
            return l

    def tf_idf(self,text):
        jieba.analyse.set_stop_words(renote.stopfile)
        tf = jieba.analyse.extract_tags(text,topK=renote.topn)
        return tf

